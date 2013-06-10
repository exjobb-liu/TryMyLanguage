package com.liu.trymylanguage.server;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

public class HtmlEscapedInputStreamTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		
	}

	@Test
	public void test() {
		String expected = "test &nbsp; &nbsp; &nbsp;test &nbsp;";
		InputStream is = new ByteArrayInputStream("test      test  ".getBytes());
		InputStream his = new HtmlEscapedInputStream(is);
		byte[] b = new byte[1024];
		int o = -1;
		try {
			o = his.read(b, 0, 1024);
			his.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		String actual = new String(b, 0 , o);
		assertTrue(expected.equals(actual));
		
	}

}
