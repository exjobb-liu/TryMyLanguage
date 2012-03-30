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
	public TMLException(String message){
		
		super(message);
		
	}
	public TMLException(){
		
		super();
	}
}
