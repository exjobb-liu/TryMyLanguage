package com.liu.trymylanguage.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorRegex {
	private String regex;
	public String getRegex() {
		return regex;
	}

	public int getGroup() {
		return group;
	}

	private int group;
	
	public ErrorRegex(String regex){
		
		if(regex ==null || regex.equals("")){
			this.regex = null;
		} else {
			
			//Determining the group number of @ by counting the number of none-escaped '(' 
			// characters before it.
			String substr = regex.substring(0,
					regex.indexOf('@'));

			Pattern p = Pattern.compile("(?:^\\(|[(]++)");
			Matcher m = p.matcher(substr);
			
			

			while(m.find()) {
				group++;
			}
			
			this.regex = regex.replace("@", "(\\d+?)");
			this.regex = TmlUtil.htmlEscape(this.regex);
		}
		
	}
	
	
}
