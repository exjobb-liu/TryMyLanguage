package com.liu.trymylanguage.server;


import com.liu.trymylanguage.client.exception.TMLException;

public interface ProcessOutputCallback {
	void receivedOutput(String output) throws TMLException;
}
