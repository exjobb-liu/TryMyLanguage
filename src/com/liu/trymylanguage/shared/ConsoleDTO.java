package com.liu.trymylanguage.shared;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: amir
 * Date: 11/25/11
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleDTO implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Describe content here.
     */
    private String content;

    /**
     * Describe isTerminated here.
     */
    
    private Map<Integer,Integer> lineFeedback;
    
    private boolean isPlot;

  
	public ConsoleDTO(){}
    public ConsoleDTO(String content, boolean isPlot){
    	this.content=content;
    	
    	this.isPlot = isPlot;
    }


    /**
     * Get the <code>Content</code> value.
     *
     * @return a <code>String</code> value
     */
    public  String getContent() {
    	return content;
    }

    public Map<Integer, Integer> getLineFeedback() {
		return lineFeedback;
	}
	public void setLineFeedback(Map<Integer, Integer> lineFeedback) {
		this.lineFeedback = lineFeedback;
	}
	/**
     * Set the <code>Content</code> value.
     *
     * @param newContent The new Content value.
     */
    public void setContent(String newContent) {
	this.content = newContent;
    }
    public boolean isPlot() {
  		return isPlot;
  	}
  	public void setPlot(boolean isPlot) {
  		this.isPlot = isPlot;
  	}
   
}
