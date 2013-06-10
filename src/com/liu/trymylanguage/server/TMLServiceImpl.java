package com.liu.trymylanguage.server;



import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.liu.trymylanguage.client.TMLService;
import com.liu.trymylanguage.client.exception.LangNotFoundException;
import com.liu.trymylanguage.client.exception.TMLException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpSession;

import net.zschech.gwt.comet.server.CometServlet;
import net.zschech.gwt.comet.server.CometSession;

import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;





public class TMLServiceImpl extends RemoteServiceServlet 
				implements TMLService {
	/**
	 * 
		 */
    private static final long serialVersionUID = 1L;
	
	private CompileServerImpl server;
	private final File tmldir;
	private CometSession cometSession;
	
	public TMLServiceImpl(){
		
		this(new File(System.getProperty("catalina.base")+"/webapps/tml/"));
		
		
	}
	
	public TMLServiceImpl(File tmldir){
	
		
		this.server = new CompileServerImpl();
		this.tmldir= tmldir;
		
		
		
	}
	
	
	public void compile(CodeDTO code) throws TMLException {
		 
		
		
		HttpSession httpSession = getThreadLocalRequest().getSession();
		cometSession = CometServlet.getCometSession(httpSession, false);
	         if (cometSession == null) {
	                 cometSession = CometServlet.getCometSession(httpSession);
	      
	         }
		
		//String regex = dto.getFeedbackRegex();
		//regex = regex.replaceAll("<filename>",code.getFileName());
		//regex = regex.replaceAll("<suffix>",dto.getSuffix());
		
		
		server.compile(code, cometSession);
//		String runResult = "";
//		
//		BufferedWriter bfw = null;
//		
//		String dirPath = "output-"+Thread.currentThread().getId()+"/";
//		File dir=null;
//		
//		if((dir = new File(tmldir.getAbsolutePath()+"/output/"+dirPath)).mkdir()){
//			try {
//
//				File file = new File(tmldir.getAbsolutePath()+"/output/"+dirPath+code.getFileName()+"."+code.getSuffix());
//				dir = new File(tmldir.getAbsolutePath()+"/output/"+dirPath);
//				bfw = new BufferedWriter(new FileWriter(file));
//				bfw.write(code.getCode());
//				bfw.flush();
//
//				if (bfw !=null)
//					bfw.close();		
//				
//
//				if(code.getCompileCmd()!=null && 
//						!code.getCompileCmd().trim().equals("")){
//					
//					
//					runResult+=tmlUtil.runCmd(code.getCompileCmd(),dir,code.getTimeout());
//					//System.out.println(runResult);
//					
//				}
//				String rcmd = code.getRunCmd().replaceAll("<filename>",
//						code.getFileName());
//				rcmd = rcmd.replaceAll("<suffix>", code.getSuffix());
//				
//				//Here we assume that if the compiler process runs 
//				//successfully, it gives no output and then if it 
//				//has output, the run command will not be executed
//				if(runResult.trim().equals(""))
//					runResult+=tmlUtil.runCmd(rcmd,dir,code.getTimeout());
//				//System.out.println(rcmd);
//				//System.out.println(runResult);
//				
//				String first = null;
//				
//				if(runResult.indexOf("\n")!=-1){
//					first = runResult
//						.substring(0, runResult.indexOf("\n"));
//					
//				}
//				if(code.getPlot()!=null &&
//						first!=null &&
//						code.getPlot().equals(first)){ 
//					try {
//						runResult= convertor.convert(runResult
//								.substring(runResult.indexOf("\n")+1));
//					} catch (ClassNotFoundException e) {
//						
//						e.printStackTrace();
//						throw new TMLException("No plot data convertor has been found");
//					} catch (ServiceConfigurationError e) {
//						
//						
//						e.printStackTrace();
//						throw new TMLException("There has been a problem with plot data conversion");
//					}	
//					//System.out.println(runResult);
//					return new ConsoleDTO(runResult,true);
//				}
//				else{
//					String result;
//					try{
//						result = TmlUtil.formatResult(runResult, code.getRegex());
//					}catch(NumberFormatException ex){
//						throw new TMLException("The @ in the Regular expression doesn't match a number");
//						
//					}
//					
//					ConsoleDTO c = new ConsoleDTO(result,false);
//					
//					//c.setContent(runResult);
//					//c.setLineFeedback(TmlUtil.getErrorMap(runResult, regex));
//					
//					return c;
//				
//				}
//
//			} catch (IOException ex) {
//
//				ex.printStackTrace();
//				throw new TMLException(ex.getMessage());
//			
//			}catch (InterruptedException e) {
//				e.printStackTrace();
//				
//				throw new TMLException(e.getMessage());
//				
//			
//			} catch (TimeoutException e) {
//				
//				e.printStackTrace();
//				throw new TMLException(e.getMessage());
//			}finally{
//				
//				TmlUtil.deleteDir(new File(tmldir.getAbsolutePath()+"/output/"+dirPath));
//
//			}
//			
//			
//			
//		}else
//			throw new TMLException("A user directory to place the source and executable file can not be created");

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
			
			Pattern.compile(dto.getFeedbackRegex().replace("@", "(\\d+?)"));
			ObjectOutput output = new ObjectOutputStream(
					new FileOutputStream(
							tmldir.getAbsolutePath()+"/langparam.bin",false));
			
			
			output.writeObject(dto);
			output.flush();
			output.close();
			
				
			
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new TMLException("Can not save the language configuration: \n"
					+e.getMessage());
		} catch (PatternSyntaxException e ){
			throw new TMLException("Syntax error in regular expression for line feedback");
		}
		
	}
	@Override
	public LangParamDTO getLangParam() throws TMLException{

		
		
			ObjectInput obj = null;
			LangParamDTO dto;
			try {
				obj = new ObjectInputStream(new BufferedInputStream(
						new FileInputStream(tmldir.getAbsolutePath()+"/langparam.bin")));
				
				dto = (LangParamDTO)obj.readObject();
				 if(dto==null)
					 throw new LangNotFoundException();
				 obj.close();
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
	public LangParamDTO getLangParamAddLang() throws TMLException{
			
			if(getThreadLocalRequest().getRemoteAddr().
					trim().equals("127.0.0.1") ||
					getThreadLocalRequest().getRemoteAddr().
					trim().equals("0:0:0:0:0:0:0:1"))
				
				return getLangParam();
			else
				throw new TMLException("Access Denied");
		
		
	}

	@Override
	public void InvalidateCometSession() {
		if (cometSession != null && cometSession.isValid()) {
			
			cometSession.invalidate();
		}
			
	}
	

	

//	@Override
//	public String getPlotData(String data) throws TMLException {
//		// TODO Auto-generated method stub
//		String out= null;
//		try {
//			out = convertor.convert(data);
//		} catch (ClassNotFoundException e) {
//			throw new TMLException("No plot data convertor has been found: \n"+e.getMessage());
//		}
//		return out;
//	}
//

	
	
}
