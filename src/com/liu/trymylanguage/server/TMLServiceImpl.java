package com.liu.trymylanguage.server;


import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.liu.trymylanguage.client.TMLService;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.File;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;



import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.ProcBuilder;

import com.liu.trymylanguage.shared.FileTypeDTO;

public class TMLServiceImpl extends RemoteServiceServlet implements TMLService {
	public ConsoleDTO compile(CodeDTO code) throws Exception {

		LangParamDTO dto = this.getLangParam();
		String result = "";
		BufferedWriter bfw = null;
		String dirPath = "output-"+Thread.currentThread().getId()+"/";
		File dir=null;
		if(new File(dirPath).mkdir()){
		try {
			
			File file = new File(dirPath+code.getFileName()+"."+dto.getSuffix());
			dir = new File(dirPath);
			bfw = new BufferedWriter(new FileWriter(file));
			bfw.write(code.getCode());
			bfw.flush();
			
			if (bfw !=null) {
				bfw.close();		
			}
			
			if(dto.getCompileCmd()!=null && !dto.getCompileCmd().equals("")){
				String ccmd = dto.getCompileCmd().replaceAll("<filename>",code.getFileName());
				ccmd = ccmd.replaceAll("<suffix>", dto.getSuffix());	
				result+=runCmd(ccmd,dto.getTimeout(),dir);
				
				
				
			}
			String rcmd = dto.getRunCmd().replaceAll("<filename>",code.getFileName());
			rcmd = rcmd.replaceAll("<suffix>", dto.getSuffix());
			
			result +=runCmd(rcmd,dto.getTimeout(),dir);
			
		} catch (IOException ex) {
			
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally{
			this.deleteDir(dir);
			
		}
		

		return new ConsoleDTO(result,true);
		}else
			throw new Exception("A user directory to place the source and executable file can not be created");

	} 
	
	
	/*public ConsoleDTO run(CodeDTO code){
		return this.compile(code);
	}


	/**
	 * {@inheritDoc}
	 * @see TMLService#getSupportedTypes()
	 */
	public ArrayList<FileTypeDTO> getSupportedTypes()
	{
		ArrayList<FileTypeDTO> mock = new ArrayList<FileTypeDTO>();
		mock.add(new FileTypeDTO("1","java"));
		return mock;

	}
	public String fileName(String patternstr, String code) {
		Pattern pattern = Pattern.compile(patternstr);
		Matcher matcher = pattern.matcher(code);
		if(matcher.find())
			return  code.substring(matcher.start()+6,matcher.end()).trim();
		else 
			return "default";
	}
	@Override
	public void saveLang(LangParamDTO dto) {
		try {
			ObjectOutput output = new ObjectOutputStream(new FileOutputStream("langparam.bin"));
			output.writeObject(dto);
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public LangParamDTO getLangParam() {

		LangParamDTO dto = null;
		try {
			ObjectInput obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream("langparam.bin")));
			dto = (LangParamDTO)obj.readObject();
			obj.close();
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}

	private String runCmd(String cmd,long timeout, File dir) throws IOException{
		
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		String[] s = cmd.split("\\s");
		String[] args = new String[s.length-1];
		for (int i = 1; i < s.length; i++) {
			args[0] = s[i];
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
			
			out += e.getStderr();
			
		}
		        
		out += output.toString();
		return out;

	}
	public boolean deleteDir(File dir) {
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
	
}
