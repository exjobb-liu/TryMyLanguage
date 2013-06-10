package com.liu.trymylanguage.server;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.liu.trymylanguage.shared.OutputDTO;



public class TmlUtil {




	
//	public Process runCmd(String cmd, File dir, long timeout) 
//			throws IOException, TimeoutException, InterruptedException{
//		boolean hasOutput = false;
//		if(cmd==null || "".equals(cmd.trim()))
//			throw new IllegalArgumentException("Command can not be null or empty");
//		List<String> command = new ArrayList<String>();
//		String[] cmdArray = cmd.split("\\s");
//		for(int i=0;i<cmdArray.length;i++){
//			command.add(cmdArray[i]);
//
//		}
//
//		ProcessBuilder pb = new ProcessBuilder(command);
//		
//		
//		pb.redirectErrorStream(true);
//		pb.directory(dir);
//
//
//		Process p = pb.start();
//		Timer timer = null;
//		InterruptTimerTask interrupter = null;
//		
////		BufferedInputStream inReader = null;
////		BufferedReader bufReader =  null;
////		BufferedWriter bufWriter = null;
//		try {
//			if(timeout>0){
//				timer = new Timer(true);
//				interrupter = 
//					new InterruptTimerTask(Thread.currentThread());
//				timer.schedule(interrupter, timeout);
//			
//				
//			}
////			
////			inReader = new BufferedInputStream(
////					new StreamGobbler(p.getInputStream()));
////			byte buf[] = new byte[50];
////			while((inReader.read(buf))>=0){
////				hasOutput = true;
////				out.write(buf);
////				out.flush();
////			}
////			
////			bufReader = new BufferedReader(
////					new InputStreamReader(in));
////			String line;
////			if((line=bufReader.readLine())!=null){
////				bufWriter = new BufferedWriter(
////						new OutputStreamWriter(p.getOutputStream()));
////				bufWriter.write(line);
////				bufWriter.flush();
////			}
////				
//			
//			
//
//			p.waitFor();
//		} catch (InterruptedException e) {
//			p.destroy();
//			if(interrupter.isTimedout)
//				throw new TimeoutException("Timeout Exceeded");
//			else{
//				throw e;
//			}
//			
//				
//		}finally{
//			if(timer!=null)
//				timer.cancel();
////			if(inReader!=null)
////				inReader.close();
////			if(bufReader!=null)
////				bufReader.close();
////			if(bufWriter!=null)
////				bufWriter.close();
//			Thread.interrupted();
//			
//		}
//			
//		return hasOutput;
//		
//		
//		
//
//
//	}

	public Process runCmd(String cmd,File dir) throws IOException{
		
		if(cmd==null || "".equals(cmd.trim()))
			throw new IllegalArgumentException("Command can not be null or empty");
		List<String> command = new ArrayList<String>();
		String[] cmdArray = cmd.split("\\s");
		for(int i=0;i<cmdArray.length;i++){
			command.add(cmdArray[i]);

		}

		ProcessBuilder pb = new ProcessBuilder(command);
		
		
		pb.redirectErrorStream(true);
		pb.directory(dir);


		return pb.start();
		
	} 
	
	public static List<OutputDTO> getErrorMap(String lines,String regex) 
			throws NumberFormatException{
		List<OutputDTO> out = new ArrayList<OutputDTO>();
		if(lines == null){

			return out;
		}
		if(regex ==null || regex.equals("")){
			out.add(new OutputDTO(0,lines));
			return out;
		}

		//Determining the group number of @ by counting the number of none-escaped '(' 
		// characters before it.
		String substr = regex.substring(0,
				regex.indexOf('@'));

		Pattern p = Pattern.compile("(?:^\\(|[(]++)");
		Matcher m = p.matcher(substr);
		int atgrpn=0;

		while(m.find())
			atgrpn++;





		regex = regex.replace("@", "(\\d+?)");
		Pattern pattern = Pattern.compile(regex);



		//fora (int i = 0; i < lines.length; i++) {

		Matcher matcher = pattern.matcher(lines);

		int start = 0;
		while (matcher.find()) {
			String line = lines.substring(start, matcher.start());
			if(!line.equals(""))
				out.add(new OutputDTO(0,line));
			line = matcher.group();
			if(!line.equals(""))
				out.add(new OutputDTO(Integer.parseInt(matcher.group(atgrpn+1)),
						line));

			start = matcher.end();
		}
		if(start<lines.length() && !lines.substring(start).equals(""))
			out.add(new OutputDTO(0,lines.substring(start)));






		return out;
	}

	public static String formatResult(String lines,ErrorRegex regex) 
			throws NumberFormatException{
		
		if(lines == null){

			return "";
		}
		if(regex ==null || regex.getRegex() == null ||regex.getRegex().equals("")){
			
			return lines;
		}
		StringBuilder out = new StringBuilder();
	
		Pattern pattern = Pattern.compile(regex.getRegex());



		

		Matcher matcher = pattern.matcher(lines);

		int start = 0;
		while (matcher.find()) {
			String line = lines.substring(start, matcher.start());
			if(!line.equals(""))
				out.append(line);
			line = matcher.group();
			if(!line.equals("")){
				out.append("<span class='lineFeedback' onclick='selectLine(event)' id='");
				out.append(matcher.group(regex.getGroup()+1));
				out.append("'>");
				out.append(line);
				out.append("</span>");
			}
			start = matcher.end();
		}
		if(start<lines.length() && !lines.substring(start).equals(""))
			out.append(lines.substring(start));

		return out.toString();
	}

	
	
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}
	public static boolean isAlive( Process p ) {
		try
		{
			p.exitValue();
			return false;
		} catch (IllegalThreadStateException e) {
			return true;
		}
	}

	public static String htmlEscape(String s) {

		StringBuilder builder = new StringBuilder();
		boolean previousWasASpace = false;
		for( char c : s.toCharArray() ) {
			if( c == ' ' ) {
				if( previousWasASpace ) {
					builder.append("&nbsp;");
					previousWasASpace = false;
					continue;
				}
				previousWasASpace = true;
			} else {
				previousWasASpace = false;
			}
			switch(c) {
			case '<': builder.append("&lt;"); break;
			case '>': builder.append("&gt;"); break;
			case '&': builder.append("&amp;"); break;
			case '"': builder.append("&quot;"); break;
			case '\n': builder.append("<br />"); break;
			// We need Tab support here, because we print StackTraces as HTML
			case '\t': builder.append("&nbsp; &nbsp; &nbsp;"); break;  
			default:
				if( c < 128 ) {
					builder.append(c);
				} else {
					builder.append("&#").append((int)c).append(";");
				}    
			}
		}
		return builder.toString();
	}

	class InterruptTimerTask
	extends TimerTask
	{
		private boolean isTimedout; 
		private Thread thread;

		public InterruptTimerTask(Thread t)
		{
			isTimedout = false;
			this.thread = t;
		}

		public void run()
		{
			thread.interrupt();
			isTimedout = true;
		}
		public boolean isTimedout(){
			return isTimedout;
		}
		

	}
	
	
	

}
