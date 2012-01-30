package com.liu.trymylanguage.server;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.liu.trymylanguage.client.TMLService;

public class TmlUtilTest {

	@Test
	public void test_getErrorMap() {
		String[] lines = {"Hello.java:30: error: ';' expected",
				"import java.io.Console;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}",
				"",
				"1 error"};
		Map<Integer, Integer> map = TmlUtil.getErrorMap(lines, "Hello.java:@:");
		
		
		assertEquals((Integer)30, map.get(1));
	}

}
