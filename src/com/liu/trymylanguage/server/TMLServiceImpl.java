package com.liu.trymylanguage.server;

import java.io.File;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.liu.trymylanguage.client.TMLService;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

import java.io.InputStreamReader;

import com.liu.trymylanguage.shared.FileTypeDTO;

public class TMLServiceImpl extends RemoteServiceServlet implements TMLService {
    public ConsoleDTO compile(CodeDTO code) {

	code.setFileName(this.fileName("class ([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*",code.getCode()));
	BufferedWriter bfw = null;
	try {
	    bfw = new BufferedWriter(new FileWriter(code.getFileName()+".java"));
	    bfw.write(code.getFileName(),0,code.getFileName().length());
	    bfw.flush();
	    //	} catch (IOException ex) {
	    // ex.printStackTrace();
	    //	}finally{
	    if (bfw !=null) {
		bfw.close();		
	    }
	    
	    
	    
	    //}
	    
	    
	    //ProcessBuilder bp = new ProcessBuilder("javac "+code.getFileName()+".java");
	    ProcessBuilder bp = new ProcessBuilder("/bin/ls");
	    bp.redirectErrorStream(true);
	    bp.directory(new File("/home/amir/TryMyLanguage"));
	    Process p = bp.start();
	    //try {
	    //p = Runtime.getRuntime().exec("java -cp . "+code.getFileName());
	    BufferedReader bfr  = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    String line;
	    while((line=bfr.readLine())!=null){
		    line=line+bfr.readLine();
	    }
	    bfr.close();
	    return new ConsoleDTO(line,true);
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
	
	return null;
	
    } 
    public ConsoleDTO run(CodeDTO code){
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
	public void saveLang(LangParamDTO dto) throws FileNotFoundException{
		try {
			ObjectOutput output = new ObjectOutputStream(new FileOutputStream("langparam.bin"));
			output.writeObject(dto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public LangParamDTO getLangParam() throws FileNotFoundException{
		
		LangParamDTO dto = null;
		try {
			ObjectInput obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream("langparam.bin")));
			dto = (LangParamDTO)obj.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}
}
