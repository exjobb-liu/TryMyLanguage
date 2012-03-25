package com.liu.trymylanguage.server;


import java.util.ServiceConfigurationError;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.liu.trymylanguage.client.TMLService;
import com.liu.trymylanguage.client.exception.LangNotFoundException;
import com.liu.trymylanguage.client.exception.TMLException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;



import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.StartupException;
import org.buildobjects.process.TimeoutException;

public class TMLServiceImpl extends RemoteServiceServlet implements TMLService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlotDataConvertorService convertor;
	private TmlUtil tmlUtil;
	public TMLServiceImpl(){
		convertor = PlotDataConvertorService.getInstance();
		tmlUtil = new TmlUtil();
		
	}
	
	public ConsoleDTO compile(CodeDTO code) throws TMLException {

		LangParamDTO dto = getLangParam();
	
		String regex = dto.getFeedbackRegex().replaceAll("@", "(\\\\d+?)");
		regex = regex.replaceAll("<filename>",code.getFileName());
		regex = regex.replaceAll("<suffix>",dto.getSuffix());
		
		
		
		String runResult = "";
		
		BufferedWriter bfw = null;
		
		String dirPath = "output-"+Thread.currentThread().getId()+"/";
		File dir=null;
		if((dir = new File("output/"+dirPath)).mkdir()){
			try {

				File file = new File("output/"+dirPath+code.getFileName()+"."+dto.getSuffix());
				dir = new File("output/"+dirPath);
				bfw = new BufferedWriter(new FileWriter(file));
				bfw.write(code.getCode());
				bfw.flush();

				if (bfw !=null) {
					bfw.close();		
				}

				if(dto.getCompileCmd()!=null && !dto.getCompileCmd().equals("")){
					
					String ccmd = dto.getCompileCmd().replaceAll("<filename>",code.getFileName());
					ccmd = ccmd.replaceAll("<suffix>", dto.getSuffix());	
					
					tmlUtil.runCmd(ccmd,dto.getTimeout(),dir);
					//System.out.println(compileResult);


				}
				String rcmd = dto.getRunCmd().replaceAll("<filename>",code.getFileName());
				rcmd = rcmd.replaceAll("<suffix>", dto.getSuffix());

				runResult =tmlUtil.runCmd(rcmd,dto.getTimeout(),dir);

			} catch (IOException ex) {

				ex.printStackTrace();
				throw new TMLException(ex.getMessage());
			
			}catch (TimeoutException e) {
				e.printStackTrace();
				throw new TMLException("Execution of the program has been timed out");
			}catch (StartupException e) {
				e.printStackTrace();
				throw new TMLException(e.getMessage());
			}catch (ExternalProcessFailureException e) {
				ConsoleDTO c = new ConsoleDTO();
				c.setContent(e.getStderr());
				c.setLineFeedback(TmlUtil.getErrorMap(e.getStderr().split("\\n"), regex));
				return c;
			}finally{
				
				TmlUtil.deleteDir(new File("output/"+dirPath));

			}
			

			String first = runResult
					.substring(0, runResult.indexOf("\n"));
			if(dto.getPlot()!=null &&
					first!=null &&
					dto.getPlot().equals(first)){ 
				try {
					runResult= convertor.convert(runResult);
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
					throw new TMLException("No plot data convertor has been found");
				} catch (ServiceConfigurationError e) {
					
					
					e.printStackTrace();
					throw new TMLException("There has been a problem with plot data conversion");
				}	
				return new ConsoleDTO(runResult
						.substring(runResult.indexOf("\n")+1),true);
			}
			else
			{
				ConsoleDTO c = new ConsoleDTO();
				c.setContent(runResult);
				c.setLineFeedback(TmlUtil.getErrorMap(runResult.split("\\n"), regex));
				c.setPlot(false);
			return c;
			
			}
		}else
			throw new TMLException("A user directory to place the source and executable file can not be created");

	} 
	
	
	/*public ConsoleDTO run(CodeDTO code){
		return this.compile(code);
	}


	/**
	 * {@inheritDoc}
	 * @see TMLService#getSupportedTypes()
	 */
	
	@Override
	public void saveLang(LangParamDTO dto) throws TMLException {
		
		try {
			
			
			ObjectOutput output = new ObjectOutputStream(
					new FileOutputStream("langparam.bin"));
			
			output.writeObject(dto);
			output.flush();
			output.close();
			
				
			
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new TMLException("Can not save the language configuration: \n"
					+e.getMessage());
		}
		
	}
	@Override
	public LangParamDTO getLangParam() throws TMLException{


		
		
			ObjectInput obj = null;
			LangParamDTO dto;
			try {
				obj = new ObjectInputStream(new BufferedInputStream(
						new FileInputStream("langparam.bin")));
				 dto = (LangParamDTO)obj.readObject();
				 if(dto==null)
					 throw new LangNotFoundException();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
				throw new LangNotFoundException();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
				throw new TMLException("There has been an error loading language configuration: \n"
						+e.getMessage());
			
			}catch (IOException e ) {
				e.printStackTrace();
				throw new TMLException("There has been an error loading language configuration: \n"
						+e.getMessage());
			}finally{
				
				if(obj!=null){
						try {
							obj.close();
						} catch (IOException e) {
							
							e.printStackTrace();
						}
					}
				
			}
			
			
		
		/*	throw new Exception("No Language configuration is found");
		
			e.printStackTrace();
			throw new Exception("Language configuration can not be loaded");
			// TODO Auto-generated catch block
			
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		*/
		return dto;
	}
	
	
	

	@Override
	public boolean isConfigured() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getPlotData(String data) throws TMLException {
		// TODO Auto-generated method stub
		String out= null;
		try {
			out = convertor.convert(data);
		} catch (ClassNotFoundException e) {
			throw new TMLException("No plot data convertor has been found: \n"+e.getMessage());
		}
		return out;
	}


	
	
}
