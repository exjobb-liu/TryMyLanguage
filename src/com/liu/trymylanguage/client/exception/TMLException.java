/**
 * 
 */
package com.liu.trymylanguage.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * General exception designed to be thrown by call to <code>TMLService</code>
 * methods 
 * @author Amirhossein Fouladi
 * 
 *
 */
public class TMLException extends Exception implements
		IsSerializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String stackTraceString;
	public TMLException(String message){
		
		super(message);
		
	}
	
	public TMLException(String messsage, String stackTraceString) {
		super(messsage);
		this.stackTraceString = stackTraceString;
	}
	public TMLException(){
		
		super();
	}
	
	public String getStackTraceString() {
		return stackTraceString;
	}
	
}
