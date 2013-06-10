package com.liu.trymylanguage.server;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;

import com.liu.trymylanguage.shared.OutputDTO;

public class TmlUtilTest {
	
	
	private TmlUtil tmlUtil;
	private final String regex = "Hello.java\"\\s:@:";
	private final String simpleString = "two spaces:  tab:	less than:< "+ 
		"greater than:> Hello.java\" :1:apm:& quot:\" unicode:ä new line:\n";
	private final String htmlString = "two spaces: &nbsp;tab:&nbsp; &nbsp; &nbsp;less than:&lt; "+ 
		"greater than:&gt; <span class='lineFeedback' onclick='selectLine(event)' id='1'>Hello.java:1:</span>apm:&amp; quot:&quot; unicode:&#228; new line:<br />";
	
	@Before
	public void setUp(){
		tmlUtil = new TmlUtil();
		
	}
	
	
	
	@Test
	public void test_runCmdNullEmptyCommand() throws IOException, InterruptedException, TimeoutException{
		
		assertTrue("".equals(tmlUtil.runCmd(null,null)));
		assertTrue("".equals(tmlUtil.runCmd("",null)));
		
	}
	
	@Test(expected=IOException.class)
	public void test_runCmdInvalidCommand() throws IOException, InterruptedException, TimeoutException{
		
		tmlUtil.runCmd("sfasdf",null);
		
		
	}
	@Test
	public void test_runCmdPathNullStdout() throws IOException,
	InterruptedException, TimeoutException{
		
		assertTrue("Test\n".equals(
				tmlUtil.runCmd("echo Test",null)));
		
		
	}
	
	@Test
	public void test_runCmdPathNullStderr() throws IOException, InterruptedException, TimeoutException{
		//System.out.println(tmlUtil.runCmd("bash alias echoerr=\'echo >&2\'",
		//null , 0));
		assertTrue("ls: cannot access safdsf: No such file or directory\n".equals(
				tmlUtil.runCmd("ls safdsf",null)));
		
		
	}
	@Test(expected=IOException.class)
	public void test_runCmdPathInvalid() throws 
	IOException, InterruptedException, TimeoutException{
		tmlUtil.runCmd("echo test", 
				new File("asfsafd"));
		
		
	}

//	@Test(expected = TimeoutException.class)
//	public void test_runCmdTimeoutPositiveExceeded() throws IOException
//		, InterruptedException, TimeoutException{
//		tmlUtil.runCmd("sleep 1", 
//				null,990);
//		
//		
//	}
	
//	@Test
//	public void test_runCmdTimeoutPositiveNotExceeded() throws IOException, 
//	InterruptedException, TimeoutException{
//		tmlUtil.runCmd("sleep 1", 
//				null,2000);
//		
//		
//	}
//	
//	@Test
//	public void test_runCmdTimeoutNonPositive() throws IOException, InterruptedException, TimeoutException{
//		long begin = System.currentTimeMillis();
//		tmlUtil.runCmd("sleep 1", 
//				null,0);
//		long end = System.currentTimeMillis();
//		assertTrue((1000 <end-begin) && (end-begin<1010));
//		
//		 begin = System.currentTimeMillis();
//		tmlUtil.runCmd("sleep 1", 
//				null,-12312312);
//		 end = System.currentTimeMillis();
//		 assertTrue((1000 <end-begin) && (end-begin<1010));
//	}
//	
	@Test
	public void test_formatResultLinesNull() {
		
		String out = TmlUtil.formatResult(null, new ErrorRegex(regex));
		
		
		assertTrue("".equals(out));
	}
	
	
	@Test
	public void test_formatResultRegexNull() {
		String out = TmlUtil.formatResult(simpleString, null);
		assertTrue(TmlUtil.htmlEscape(simpleString).equals(out));
	}
	
	
	@Test
	public void test_formatResultRegexEmpty() {
		String out = TmlUtil.formatResult(simpleString, new ErrorRegex(regex));
		assertTrue(TmlUtil.htmlEscape(simpleString).equals(out));
	}
	
@Test
	public void test_formatResult() {
		String out = TmlUtil.formatResult(simpleString,new ErrorRegex(regex));
		assertTrue(htmlString.equals(out));	
	}
	
	@Test
	public void test_formatResultMultipleMatcherGroup() {
		
		assertTrue(htmlString.equals(TmlUtil.formatResult(simpleString,new ErrorRegex("(\\w+?)[.](\\w+?):@:"))));	
	}
	
	@Test
	public void test_formatResultNoMatch() {
		assertTrue("blahblahblah &nbsp;".equals(TmlUtil.formatResult("blahblahblah  ",new ErrorRegex(regex))));	
	}
	
	@Test
	public void test_formatResultMoreThanOneMatch() {
		String in = simpleString + "Hello.java:41:";	
		String out = htmlString  + "<span class='lineFeedback' onclick='selectLine(event)' id='41'>Hello.java:41:</span>";
		assertTrue(out.equals(TmlUtil.formatResult(in, new ErrorRegex(regex))));
		
	}
	@Test
	public void test_getErrorMapLinesNull() {
		
		List<OutputDTO> map = TmlUtil.getErrorMap(null, "Hello.java:@:");
		
		
		assertEquals(0,map.size());
	}
	
	
	@Test
	public void test_getErrorMapRegexNull() {
		String lines = "Hello.java:30: error: ';' expected"+
				"import java.io.Console;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}"+
				""+
				"1 error";
		List<OutputDTO> map = TmlUtil.getErrorMap(lines, null);
		
		
		assertEquals(1,map.size());
		assertEquals(map.get(0).getLineNum(), 0);
		assertTrue(map.get(0).getContent().equals(lines));
	}
	
	
	@Test
	public void test_getErrorMapRegexEmpty() {
		String lines = "Hello.java:30: error: ';' expected"+
				"import java.io.Console;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}"+
				""+
				"1 error";
		List<OutputDTO> map = TmlUtil.getErrorMap(lines, "");
		
		
		assertEquals(1,map.size());
		assertEquals(map.get(0).getLineNum(), 0);
		assertTrue(map.get(0).getContent().equals(lines));
	}
	
@Test
	public void test_getErrorMap() {
	String lines = "Hello.java:30: error: ';' expected"+
			"import java.io.Console;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}"+
			""+
			"1 error";
	List<OutputDTO> map = TmlUtil.getErrorMap(lines,
				"Hello.java:@:");
		
		
		assertEquals(2,map.size());
		
		OutputDTO dto = map.get(0);
		assertEquals(30, dto.getLineNum());
		assertTrue("Hello.java:30:".equals(dto.getContent()));
		
		dto = map.get(1);
		assertEquals(0, dto.getLineNum());
		String s= " error: ';' expected"+
				"import java.io.Console;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}"+
				""+
				"1 error";
		assertTrue(s.equals(dto.getContent()));
		
	}
	
	@Test
	public void test_getErrorMapMultipleMatcherGroup() {
		String lines = "Hello.java:30: error: ';' expected"+
				"import java.io.Console;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}"+
				""+
				"1 error";
		List<OutputDTO> map = TmlUtil.getErrorMap(lines,
				"(\\w+?)[.](\\w+?):@:");
		assertEquals(2,map.size());
		
		OutputDTO dto = map.get(0);
		assertEquals(30, dto.getLineNum());
		assertTrue("Hello.java:30:".equals(dto.getContent()));
		
		dto = map.get(1);
		assertEquals(0, dto.getLineNum());
		String s= " error: ';' expected"+
				"import java.io.Console;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}"+
				""+
				"1 error";
		assertTrue(s.equals(dto.getContent()));
	}
	
	@Test
	public void test_getErrorNoMatch() {
		String lines = "Hello.java:30: error: ';' expected"+
				"import java.io.Console;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}"+
				""+
				"1 error";
		List<OutputDTO> map = TmlUtil.getErrorMap(lines,
				"");
		assertEquals(1,map.size());
		assertEquals(map.get(0).getLineNum(), 0);
		assertTrue(map.get(0).getContent().equals(lines));
	}
	
	@Test
	public void test_getErrorMoreThanOneMatch() {
		String lines = " error: ';' expected Hello.java:30:"+
				"import java.io.Console;import Hello.java:40: \njava.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}"+
				""+
				"1 error";
		List<OutputDTO> map = TmlUtil.getErrorMap(lines,
				"(\\w+?)[.](\\w+?):@:");
		assertEquals(5,map.size());
		
		OutputDTO dto = map.get(0);
		assertEquals(0, dto.getLineNum());
		assertTrue(" error: ';' expected ".equals(dto.getContent()));
		
		dto = map.get(1);
		assertEquals(30, dto.getLineNum());
		assertTrue("Hello.java:30:".equals(dto.getContent()));
		
		dto = map.get(2);
		assertEquals(0, dto.getLineNum());
		String s= "import java.io.Console;import ";
		assertTrue(s.equals(dto.getContent()));
		
		dto = map.get(3);
		assertEquals(40, dto.getLineNum());
		s= "Hello.java:40:";
		assertTrue(s.equals(dto.getContent()));
		
		dto = map.get(4);
		assertEquals(0, dto.getLineNum());
		s= " \njava.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;public class Hello{public static void main(String argsv[]),{System.out.println(\"Hello\");/*String out = null;try {BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in)); out = bfr.readLine();bfr.close();} catch (IOException ex) {ex.printStackTrace();}System.out.println(\"Your name is:\");System.out.println(out);*/}}}"+
				""+
				"1 error";
		assertTrue(s.equals(dto.getContent()));
		
	}
	@Test
	public void test_MklFeedback() {
		String out = TmlUtil.formatResult("FILE \"default.mkl\" 1:0-1:7 ERROR:",
				new ErrorRegex("\"default.mkl\"\\s@:"));
		System.out.println(out);
	}
}
