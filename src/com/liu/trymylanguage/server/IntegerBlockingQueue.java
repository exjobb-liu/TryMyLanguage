package com.liu.trymylanguage.server;

import java.util.concurrent.ArrayBlockingQueue;

public class IntegerBlockingQueue extends ArrayBlockingQueue<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2715677591283671616L;
	 

	public IntegerBlockingQueue(int capacity) {
		super(capacity);
		
	}
	
	public void put(String string) throws InterruptedException {
		char[] array = string.toCharArray();
		for (int i = 0; i < array.length; i++) {
			this.put((int)array[i]);
		}
	}
	
	
	
	

}
