package com.liu.trymylanguage.server;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import org.junit.* ;
import static org.junit.Assert.* ;
import com.liu.trymylanguage.client.TMLService;

import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

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
     "public static void main(String argsv[]){\n"+
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
	codeDTO.setFileName("Hello");
	codeDTO.setCode(code);
	ConsoleDTO p = null;
	try {
		p = cmps.compile(codeDTO);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		System.out.println(p.getLineFeedback().get(1));
	assertTrue("Hello\n".equals(p.getContent()));
	
	
    }
    
    @Test
    public void test_saveLang(){
    	TMLService tml = new TMLServiceImpl();
    	LangParamDTO dto = new LangParamDTO();
    	dto.setCommentMEnd("*/");
    	dto.setCommentMStart("/*");
    	dto.setCommentSingle("//");
    	dto.setCompileCmd("javac <filename>.<suffix>");
    	dto.setEscapeChar("\\");
    	dto.setFeedbackRegex("<filename>.<suffix>:@:");
    	dto.setKeywords("test class");
    	dto.setName("java");
    	dto.setOperators("+-");
    	dto.setRunCmd("java <filename>");
    	dto.setStringChar("\" \'");
    	dto.setTimeout(6000);
    	dto.setSuffix("java");
    	LangParamDTO dto1 = null;
    	
    	try {
    		tml.saveLang(dto);
			ObjectInput obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream("langparam.bin")));
			dto1 = (LangParamDTO) obj.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	assertTrue(dto.getName().equals(dto1.getName()));
    }
    @Test
    public void test_getLangParam(){
    	
    	TMLService tml = new TMLServiceImpl();
    	LangParamDTO dto = new LangParamDTO();
    	dto.setCommentMEnd("*/");
    	dto.setCommentMStart("/*");
    	dto.setCommentSingle("//");
    	dto.setCompileCmd("javac <filename>.<suffix>");
    	dto.setEscapeChar("\\");
    	dto.setFeedbackRegex("<filename>.<suffix>:@:");
    	dto.setKeywords("test class");
    	dto.setName("java");
    	dto.setOperators("+-");
    	dto.setRunCmd("java <filename>");
    	dto.setStringChar("\" \'");
    	dto.setTimeout(6000);
    	dto.setSuffix("java");
    	LangParamDTO dto1 = null;
    	
    	try {
    		ObjectOutput output = new ObjectOutputStream(new FileOutputStream("langparam.bin"));
			output.writeObject(dto);
			
			dto1 = tml.getLangParam();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	
    	assertTrue(dto.getName().equals(dto1.getName()));
    	
    	
    	
    }
}
