package com.liu.trymylanguage.server;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;

public class MKLPlotDataConverterTest {

	@Test
	public void testConvert() {
		MKLPlotDataConverter c = new MKLPlotDataConverter();
		String sampleData= "time	ground	y\n	" +
				"0.	-2.99999999998	-1.41477653833\n	" +
				"0.01	-3.	-1.41502154559\n	" +
				"0.02	-3.	-1.41575608363\n	" +
				"0.03	-3.	-1.41697905793";
		try {
			System.out.println(c.convert(new BufferedReader(new StringReader(sampleData))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
