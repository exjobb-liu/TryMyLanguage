package com.liu.trymylanguage.client.exception;

public class LangNotFoundException extends TMLException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LangNotFoundException() {
		super("No Configuration has been found");
	}
}
