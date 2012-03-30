package com.liu.trymylanguage.server;

import org.junit.Test;

public class MKLPlotDataConvertorTest {

	@Test
	public void testConvert() {
		MKLPlotDataConvertor c = new MKLPlotDataConvertor();
		String sampleData= "time	ground	y\n	" +
				"0.	-2.99999999998	-1.41477653833\n	" +
				"0.01	-3.	-1.41502154559\n	" +
				"0.02	-3.	-1.41575608363\n	" +
				"0.03	-3.	-1.41697905793";
		System.out.println(c.convert(sampleData));
	}

}
