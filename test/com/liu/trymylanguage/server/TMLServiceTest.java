package com.liu.trymylanguage.server;

import org.junit.* ;
import static org.junit.Assert.* ;
import com.liu.trymylanguage.client.TMLService;

import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.ConsoleDTO;

public class TMLServiceTest  {
    
    /*@Test
    public void test_returnFilename(){
	TMLService cmps = new TMLServiceImpl();
	String name = cmps.fileName("class ([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*","package test.test; import test.test; class Test{} ");
	System.out.println(name);
	assertTrue("Test".equals(name));
    }*/
    @Test
    public void test_compileHello(){
	TMLService cmps = new TMLServiceImpl();
	String code =
"import java.io.Console;"+
"import java.io.BufferedReader;"+
"import java.io.IOException;"+
"import java.io.InputStreamReader;"+

"public class Hello{"+
     "public static void main(String argsv[]){"+
	"System.out.println(\"Hello\");"+
	"/*String out = null;"+
	"try {"+
	    "BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));"+	    
	   " out = bfr.readLine();"+
	    "bfr.close();"+
	"} catch (IOException ex) {"+
	    "ex.printStackTrace();"+
	    "}"+


	"System.out.println(\"Your name is:\");"+
	"System.out.println(out);*/"+ 
    "}"+
"}";
	CodeDTO codeDTO = new CodeDTO();
	codeDTO.setCode(code);
	ConsoleDTO p = cmps.compile(codeDTO);
		System.out.println(p.getContent());
	assertTrue("Hello\n".equals(p.getContent()));
	
	
    }

}
