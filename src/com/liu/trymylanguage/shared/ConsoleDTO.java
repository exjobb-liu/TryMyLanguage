package com.liu.trymylanguage.shared;

/**
 * Created by IntelliJ IDEA.
 * User: amir
 * Date: 11/25/11
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleDTO implements java.io.Serializable {

    /**
     * Describe content here.
     */
    private String content;

    /**
     * Describe isTerminated here.
     */
    private boolean isTerminated;


    public ConsoleDTO(){}
    public ConsoleDTO(String content, boolean isTerminated){
	this.content=content;
	this.isTerminated = isTerminated;
    }


    /**
     * Get the <code>Content</code> value.
     *
     * @return a <code>String</code> value
     */
    public  String getContent() {
	return content;
    }

    /**
     * Set the <code>Content</code> value.
     *
     * @param newContent The new Content value.
     */
    public void setContent(String newContent) {
	this.content = newContent;
    }

    /**
     * Get the <code>IsTerminated</code> value.
     *
     * @return a <code>boolean</code> value
     */
    public boolean isIsTerminated() {
	return isTerminated;
    }

    /**
     * Set the <code>IsTerminated</code> value.
     *
     * @param newIsTerminated The new IsTerminated value.
     */
    public void setIsTerminated(boolean newIsTerminated) {
	this.isTerminated = newIsTerminated;
    }
}
