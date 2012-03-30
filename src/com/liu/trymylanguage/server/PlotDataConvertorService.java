package com.liu.trymylanguage.server;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class PlotDataConvertorService {
	private static PlotDataConvertorService service;
	private ServiceLoader<PlotDataConvertor> loader;

	
	private PlotDataConvertorService(){
		loader= ServiceLoader.load(PlotDataConvertor.class);
		
	}
	
	  public static synchronized PlotDataConvertorService getInstance() {
	        if (service == null) {
	            service = new PlotDataConvertorService();
	        }
	        return service;
	    }

	    /**
	     * Retrieve definitions from the first provider
	     * that contains the word.
	     */
	    public String convert(String data) throws ClassNotFoundException
	    				,ServiceConfigurationError{
	        String result = null;

	       
	            Iterator<PlotDataConvertor> convertors = loader.iterator();
	            if (convertors.hasNext()) {
	                PlotDataConvertor d = convertors.next();
	                result = d.convert(data);
	            }else
	            	throw new ClassNotFoundException();
	       
	        return result;
	    }
}
