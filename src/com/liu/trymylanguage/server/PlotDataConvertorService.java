package com.liu.trymylanguage.server;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class PlotDataConvertorService {
	private static PlotDataConvertorService service;
	private ServiceLoader<PlotDataConverter> loader;

	
	private PlotDataConvertorService(){
		loader= ServiceLoader.load(PlotDataConverter.class);
		
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
	    public String convert(BufferedReader data) throws ClassNotFoundException,
	    	ServiceConfigurationError,
	    	Exception{
	        String result = null;

	       
	            Iterator<PlotDataConverter> converters = loader.iterator();
	            if (converters.hasNext()) {
	                PlotDataConverter d = converters.next();
	                result = d.convert(data);
	            }else
	            	throw new ClassNotFoundException();
	       
	        return result;
	    }
}
