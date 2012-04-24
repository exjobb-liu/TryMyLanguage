package com.liu.trymylanguage.server;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class TmlUtilTest {
	
	
	public void test_runCmdRoot(){
		try {
			TmlUtil tmlUtil = new TmlUtil();
			( new File("output")).mkdir();
			( new File("output/outputtest")).mkdir();
			File dir = new File("output/outputtest");
			System.out.println(dir.getCanonicalFile().getAbsolutePath());
			String s = tmlUtil.
					runCmd("gcc default.cpp -o default", 7000, new File("output"));
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	public void test_getErrorMap() {
		String[] lines = {"Hello.java:30: error: ';' expected",
				"import java.io.Console;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}",
				"",
				"1 error"};
		Map<Integer, Integer> map = TmlUtil.getErrorMap(lines, "Hello.java:@:");
		
		
		assertEquals((Integer)30, map.get(1));
	}

	public void test_runCmd(){
		
		TmlUtil tmlUtil = new TmlUtil();
		assertFalse("".equals(tmlUtil.
				runCmd("/home/amir/Downloads/mkl test.mkl",
						70000, new File("."))));
		
	}
	@Test
	public void test_run(){
		
		TmlUtil tmlUtil = new TmlUtil();
		List<String> l = null;
		try {
			l = tmlUtil.run("javac", 
					new File("/home/amir/Downloads/"),1000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
		
		assertEquals(2, l.size());
	}
}
