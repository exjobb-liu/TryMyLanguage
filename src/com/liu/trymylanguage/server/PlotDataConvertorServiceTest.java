package com.liu.trymylanguage.server;

import static org.junit.Assert.*;

import java.util.ServiceConfigurationError;

import org.junit.Test;

public class PlotDataConvertorServiceTest {

	@Test
	public void testConvert() {
		PlotDataConvertorService service = PlotDataConvertorService.getInstance();
		assertNotNull(service);
		String sampleData= "time	ground	y\n	" +
				"0.	-2.99999999998	-1.41477653833\n	" +
				"0.01	-3.	-1.41502154559\n	" +
				"0.02	-3.	-1.41575608363\n	" +
				"0.03	-3.	-1.41697905793";
		
			
				try {
					System.out.println(service.convert(sampleData));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceConfigurationError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
	}

}
