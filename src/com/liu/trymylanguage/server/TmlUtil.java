package com.liu.trymylanguage.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liu.trymylanguage.shared.LangParamDTO;

public class TmlUtil {

	
	public static synchronized void saveLangToFile(LangParamDTO dto) throws IOException {
		

	}

	
	public static synchronized LangParamDTO getLangParamFromFile() throws IOException,
			ClassNotFoundException {
		
		return null;
	}

	
	public static String runCmd(String cmd, long timeout) throws IOException {
		
		return null;
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

}
