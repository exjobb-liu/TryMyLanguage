package com.liu.trymylanguage.shared;

import java.io.Serializable;

public class OutputDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int lineNum;
	
	private String content;
	public OutputDTO(){
		lineNum=0;
		content="";
		
	}
	public OutputDTO(int lineNum, String content){
		this.lineNum = lineNum;
		this.content = content;
		
		
		
	}
	public int getLineNum() {
		return lineNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	}
