package com.liu.trymylanguage.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.util.ServiceConfigurationError;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import net.zschech.gwt.comet.server.CometSession;

import com.liu.trymylanguage.client.exception.TMLException;

import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.Plot;


public class CompileServerImpl {

	private TmlUtil tmlUtil;
	private File tmldir;
	private PlotDataConvertorService converter;


	private BufferedReader inReader;


	public CompileServerImpl(){

		this(PlotDataConvertorService.getInstance(),
				new TmlUtil(),
				new File(System.getProperty("catalina.base")+"/webapps/tml/"));



	}

	public CompileServerImpl(PlotDataConvertorService converter,
			TmlUtil tmlUtil, File tmldir){

		this.tmlUtil = tmlUtil;
		this.tmldir= tmldir;
		this.converter = converter;


	}

	public void compile(CodeDTO dto, CometSession cometSession) throws TMLException {




		File srcDir = createSourceDir();
		String path = srcDir.getAbsolutePath();
		try {
			createSourceFile(dto.getCode(), path+"/"+dto.getFileName()+"."+dto.getSuffix());





			boolean compileHasOutput = false;
			if(dto.getCompileCmd()!=null && 
					!dto.getCompileCmd().trim().equals("")){


				Process p;

				p  = tmlUtil.runCmd(dto.getCompileCmd(),srcDir);
				compileHasOutput = sendOutput(p,
						cometSession,
						dto.getTimeout(),
						new ErrorRegex(dto.getRegex()),
						false);



			}

			//Here we assume that if the compiler process runs 
			//successfully, it gives no output and then if it 
			//has output, the run command will not be executed
			if(!compileHasOutput){
				sendOutput(tmlUtil.runCmd(dto.getRunCmd(), srcDir),
						cometSession,
						dto.getTimeout(),
						new ErrorRegex(dto.getRegex()),
						dto.isPlot());



			}
		} catch (IOException e) {
			if (e.getCause() instanceof InterruptedException) {
				cometSession.enqueue(new TMLException("Operation has been timed out"));
			} else {

				throw new TMLException(e.getMessage());
			}

		} catch (TimeoutException e) {
			cometSession.enqueue(new TMLException("Operation has been timed out"));


		} finally {
			TmlUtil.deleteDir(srcDir);
		}



		//System.out.println(rcmd);
		//System.out.println(runResult);

		//		String first = null;
		//
		//		if(runResult.indexOf("\n")!=-1){
		//			first = runResult
		//					.substring(0, runResult.indexOf("\n"));
		//
		//		}
		//		if(dto.getPlot()!=null &&
		//				first!=null &&
		//				dto.getPlot().equals(first)){ 
		//			try {
		//				runResult= convertor.convert(runResult
		//						.substring(runResult.indexOf("\n")+1));
		//			} catch (ClassNotFoundException e) {
		//
		//				e.printStackTrace();
		//				throw new TMLException("No plot data convertor has been found");
		//			} catch (ServiceConfigurationError e) {
		//
		//
		//				e.printStackTrace();
		//				throw new TMLException("There has been a problem with plot data conversion");
		//			}	
		//			//System.out.println(runResult);
		//			return new ConsoleDTO(runResult,true);
		//		}
		//		else{
		//			String result;
		//			try{
		//				result = TmlUtil.formatResult(runResult, regex);
		//			}catch(NumberFormatException ex){
		//				throw new TMLException("The @ in the Regular expression doesn't match a number");
		//
		//			}
		//
		//			ConsoleDTO c = new ConsoleDTO(result,false);
		//
		//			//c.setContent(runResult);
		//			//c.setLineFeedback(TmlUtil.getErrorMap(runResult, regex));
		//
		//			return c;
		//
		//		}
		//
		//	} catch (IOException ex) {
		//
		//		ex.printStackTrace();
		//		throw new TMLException(ex.getMessage());
		//
		//	}catch (InterruptedException e) {
		//		e.printStackTrace();
		//
		//		throw new TMLException(e.getMessage());
		//
		//
		//	} catch (TimeoutException e) {
		//
		//		e.printStackTrace();
		//		throw new TMLException(e.getMessage());
		//	}finally{
		//
		//		TmlUtil.deleteDir(new File(tmldir.getAbsolutePath()+"/output/"+dirPath));
		//
		//	}





	}



	private boolean sendOutput(Process p,
			CometSession cometSession,
			long timeout,
			ErrorRegex regex,
			boolean plot) throws TimeoutException, TMLException, IOException{
		boolean hasOutput = false;
		Timer timer = null;
		InterruptTimerTask interrupter = null; 

		try {
			if(timeout>0){
				timer = new Timer(true);
				interrupter = 
						new InterruptTimerTask(Thread.currentThread());
				timer.schedule(interrupter, timeout);
			}
			InputStreamReader ir;
			if (plot) {
				ir = new InputStreamReader(
						new StreamGobbler(p.getInputStream()));
				
				try {
					Plot chartObj = new Plot();
					chartObj.setChartData(converter.convert(new BufferedReader(ir)));
					cometSession.enqueue(chartObj);
				
				} catch (ClassNotFoundException e) {

					e.printStackTrace();
					cometSession.enqueue(new TMLException("No plot data convertor has been found", getStackTraceString(e)));
				} catch (ServiceConfigurationError e) {


					e.printStackTrace();
					cometSession.enqueue(new TMLException("There has been a problem with plot data conversion", getStackTraceString(e) ));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					cometSession.enqueue(new TMLException("There has been a problem with plot data conversion." +
							"<br /> Uncheck 'draw plot' and run the program again to get the output ", getStackTraceString(e)));
				}	
			} else {
				ir = new InputStreamReader(new HtmlEscapedInputStream(
						new StreamGobbler(p.getInputStream())));

				inReader = new BufferedReader(ir);

			
				int o = inReader.read();

				while (o != -1) {
					hasOutput = true;
					StringBuilder builder = new StringBuilder();

					int i = 0;

					while (i < 1025 && o != '\n' && o != -1) {
						builder.append((char)o);
						o = inReader.read();
					}
					cometSession.enqueue(TmlUtil.formatResult(builder.toString(),regex));

					if (o != -1) {
						o = inReader.read();
					}

				}
				inReader.close();
			}
	
			p.waitFor();
			
			cometSession.enqueue(new Boolean(true));


		} catch (InterruptedException e) {
			p.destroy();
			if(interrupter.isTimedout())
				throw new TimeoutException("Timeout Exceeded");
			else{
				throw new TMLException(e.getMessage());
			}


		}finally{
			if(timer!=null)
				timer.cancel();

			Thread.interrupted();

		}


		return hasOutput;
	}

	private void createSourceFile(String content, String absolutePath) throws IOException{

		File file = new File(absolutePath);

		BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
		bfw.write(content);
		bfw.flush();
		bfw.close();	
	}

	private File createSourceDir() throws TMLException{
		File dir;
		String dirPath = tmldir.getAbsolutePath()+"/output/"
				+"output-"+UUID.randomUUID().toString()+"/";
		if((dir = new File(dirPath)).mkdir())
			return dir;
		else
			throw new TMLException("A user directory to place the source and executable file can not be created");

	}


	private String getStackTraceString(Throwable e) {
		Writer sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

}
