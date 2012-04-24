package com.liu.trymylanguage.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;
import org.buildobjects.process.StartupException;
import org.buildobjects.process.TimeoutException;


import com.liu.trymylanguage.shared.LangParamDTO;

public class TmlUtil {

	
	public static synchronized void saveLangToFile(LangParamDTO dto) throws IOException {
		

	}

	
	public static synchronized LangParamDTO getLangParamFromFile() throws IOException,
			ClassNotFoundException {
		
		return null;
	}

	
	public static String runCmdroot(String cmd, long timeout, File dir) throws IOException {
	ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		String[] s = cmd.split("\\s");
		
		String[] args = new String[s.length-1];
		for (int i = 1; i < s.length; i++) {
			args[i-1] = s[i];
		}
		
		
		ProcBuilder builder = new ProcBuilder(s[0])
				.withWorkingDirectory(dir)
				.withArgs(args)
		        .withOutputStream(output)
		        .withTimeoutMillis(timeout);
		
		
		
		String out = new String();
		try {
			
			builder.run();
		} catch (ExternalProcessFailureException e) {
			
			out = e.getStderr();
			
		}
		        
		out += output.toString();
		return out;

	}
	public  String runCmd(String cmd, 
			long timeout, File dir) 
					throws StartupException,TimeoutException,
					ExternalProcessFailureException{
		//ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		
		
			
			String[] s = cmd.split("\\s");
			String[] args = new String[s.length-1];
			
			for (int i = 1; i < s.length; i++) {
				args[i-1] = s[i];
			}
			
			
			ProcBuilder builder = new ProcBuilder(s[0])
					.withWorkingDirectory(dir)
					.withArgs(args)
			       // .withOutputStream(output)
			        .withTimeoutMillis(timeout);
			
			
			
			ProcResult result = builder.run();
		
			System.out.println(result.getExitValue());
			//System.out.println(result.);
			
			
			return result.getOutputString();

		}
	
	public List<String> run(String cmd, File dir, long timeout) 
			throws IOException, InterruptedException{
		List<String> output= new ArrayList<String>();
		
		if(cmd==null || "".equals(cmd.trim()))
			return output;
		List<String> command = new ArrayList<String>();
		String[] cmdArray = cmd.split("\\s");
		for(int i=0;i<cmdArray.length;i++){
			command.add(cmdArray[i]);
			
		}
		
		ProcessBuilder pb = new ProcessBuilder(command);
		pb.redirectErrorStream(true);
		pb.directory(dir);
		
		
			Process p = pb.start();
			if(timeout<=0)
				p.waitFor();
			else {
				

			    long now = System.currentTimeMillis();
			   
			    long finish = now + timeout;
			    while ( isAlive( p ) && ( System.currentTimeMillis() < finish ) )
			    {
			        Thread.sleep( 10 );
			    }
			    if ( isAlive( p ) )
			    {
			    	p.destroy();
			        throw new InterruptedException( 
			        		"Process timeout out after " + timeout + " milliseconds" );
			    }
			   
			   
				
			}
		
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = br.readLine();
			
			while(line!=null){
				output.add(line);
				line = br.readLine();
			}
			
	
		return output;
		
		
	}
	
	public static Map<Integer,Integer> getErrorMap(String[] lines,String regex){
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		
		
		Pattern pattern = Pattern.compile(regex);
		for (int i = 0; i < lines.length; i++) {
			Matcher matcher = pattern.matcher(lines[i]);
			if(matcher.find()){
				map.put(i+1,Integer.parseInt(matcher.group(1)));
			}
			
			
		}
		
		return map;
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


}
