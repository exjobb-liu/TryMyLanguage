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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.* ;
import com.liu.trymylanguage.client.TMLService;
import com.liu.trymylanguage.client.exception.TMLException;
import com.liu.trymylanguage.shared.LangParamDTO;

public class TMLServiceTest  {





	private TMLService tml;
	private final String tmldir = "./";
	@Mock private TmlUtil tmlUtil;
	@Mock private PlotDataConvertorService convertorService;

	
	@Before 
	public void setUp(){

		//tmlUtil = mock(TmlUtil.class);
		MockitoAnnotations.initMocks(this);

//		tml = new TMLServiceImpl(convertorService,tmlUtil
//				,new File(tmldir));

	} 

	@Test
	public void test_saveLang(){

		LangParamDTO dto = new LangParamDTO();
		dto.setCommentMEnd("*/");
		dto.setCommentMStart("/*");
		dto.setCommentSingle("//");
		dto.setCompileCmd("");
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
			ObjectInput obj = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(tmldir+"langparam.bin")));
			dto1 = (LangParamDTO) obj.readObject();
			obj.close();
			dto1.setTimeout(7000);
			tml.saveLang(dto1);
			ObjectInput obj1 = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(tmldir+"langparam.bin")));
			LangParamDTO dto2 = (LangParamDTO)obj1.readObject();
			obj1.close();
			assertEquals(7000, dto2.getTimeout());
		} catch (TMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue(dto.getName().equals(dto1.getName()));
	}

	@Test(expected=TMLException.class)
	public void test_saveLangRegexError() throws TMLException{
		LangParamDTO dto = new LangParamDTO();
		dto.setFeedbackRegex("line[@");
		tml.saveLang(dto);


	}

	@Test
	public void test_getLangParam(){

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
			ObjectOutput output = new ObjectOutputStream(
					new FileOutputStream(tmldir+"langparam.bin"));
			output.writeObject(dto);

			dto1 = tml.getLangParam();
			output.close();
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
		assertTrue(dto.getFeedbackRegex()
				.equals(dto1.getFeedbackRegex()));


	}

//	@Test
//	public void test_compileCpp(){
//		LangParamDTO dto = new LangParamDTO();
//		dto.setCommentMEnd("*/");
//		dto.setCommentMStart("/*");
//		dto.setCommentSingle("//");
//		dto.setCompileCmd("g++ <filename>.<suffix> -o <filename>");
//		dto.setEscapeChar("\\");
//		dto.setFeedbackRegex("<filename>.<suffix>:@:");
//		dto.setKeywords("test class");
//		dto.setName("java");
//		dto.setOperators("+-");
//		dto.setRunCmd("./<filename>");
//		dto.setStringChar("\" \'");
//		dto.setTimeout(6000);
//		dto.setSuffix("cpp");
//		String code ="#include <iostream>\n" +
//				"using namespace std;\n" +
//				"int main(){\n" +
//				"cout<<\"hello\";\n" +
//				"}";
//		CodeDTO codeDTO = new CodeDTO();
//		codeDTO.setFileName("Hello");
//		codeDTO.setCode(code);
//		ConsoleDTO p = null;
//
//		//			try {
//		//				when(tmlUtil.runCmd(anyString(), (File)anyObject(), anyLong()))
//		//				.thenReturn("").thenReturn("hello");
//		//			} catch (IOException e1) {
//		//				// TODO Auto-generated catch block
//		//				//e1.printStackTrace();
//		//			} catch (InterruptedException e1) {
//		//				// TODO Auto-generated catch block
//		//				//e1.printStackTrace();
//		//			}
//		TMLServiceImpl tml1  = new TMLServiceImpl();
//		try {
//
//			p = tml1.compile(codeDTO,dto);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		assertTrue("hello".equals(p.getConsoleContent().get(0).getContent()));
//
//
//	}
//	@Test
//	public void test_compileHelloWithCompilCmd(){
//		LangParamDTO dto = new LangParamDTO();
//		dto.setCommentMEnd("*/");
//		dto.setCommentMStart("/*");
//		dto.setCommentSingle("//");
//		dto.setCompileCmd("javac <filename>.<suffix>");
//		dto.setEscapeChar("\\");
//		dto.setFeedbackRegex("<filename>.<suffix>:@:");
//		dto.setKeywords("test class");
//		dto.setName("java");
//		dto.setOperators("+-");
//		dto.setRunCmd("java <filename>");
//		dto.setStringChar("\" \'");
//		dto.setTimeout(6000);
//		dto.setSuffix("java");
//		String code ="";
//		CodeDTO codeDTO = new CodeDTO();
//		codeDTO.setFileName("Hello");
//		codeDTO.setCode(code);
//		ConsoleDTO p = null;
//
//		try {
//			when(tmlUtil.runCmd(anyString(), (File)anyObject(), anyLong()))
//			.thenReturn("compile\ncompile2").thenReturn("run");
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//
//			p = tml.compile(codeDTO,dto);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(p.getConsoleContent().get(0).getContent());
//		assertEquals(1, p.getConsoleContent().size());
//		assertTrue("compile<br />compile2".equals(p.getConsoleContent().get(0).getContent()));
//		//assertTrue("run".equals(p.getConsoleContent().get(1).getContent()));
//
//	}
//	  @Test
//	public void test_compileHelloWithOutCompilCmd(){
//		LangParamDTO dto = new LangParamDTO();
//		dto.setCommentMEnd("*/");
//		dto.setCommentMStart("/*");
//		dto.setCommentSingle("//");
//
//		dto.setEscapeChar("\\");
//		dto.setFeedbackRegex("<filename>.<suffix>:@:");
//		dto.setKeywords("test class");
//		dto.setName("java");
//		dto.setOperators("+-");
//		dto.setRunCmd("perl <filename>.<suffix>");
//		dto.setStringChar("\" \'");
//		dto.setTimeout(6000);
//		dto.setSuffix("pl");
//		String code ="";
//		CodeDTO codeDTO = new CodeDTO();
//		codeDTO.setFileName("Hello");
//		codeDTO.setCode(code);
//		ConsoleDTO p = null;
//
//		try {
//			when(tmlUtil.runCmd(anyString(), (File)anyObject(), anyLong()))
//			.thenReturn("run");
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//
//			p = tml.compile(codeDTO,dto);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		assertTrue("run".equals(p.getConsoleContent().get(0).getContent()));
//
//
//	}
//	@Test(expected=TMLException.class)
//	public void test_compileHelloWithIOException() throws TMLException{
//		LangParamDTO dto = new LangParamDTO();
//
//		dto.setCompileCmd("javac <filename>.<suffix>");
//
//		dto.setFeedbackRegex("<filename>.<suffix>:@:");
//
//		dto.setRunCmd("java <filename>");
//
//		dto.setTimeout(6000);
//		dto.setSuffix("java");
//		String code ="";
//		CodeDTO codeDTO = new CodeDTO();
//		codeDTO.setFileName("Hello");
//		codeDTO.setCode(code);
//		ConsoleDTO p = null;
//
//
//
//		try {
//			doThrow(new IOException()).when(tmlUtil)
//			.runCmd(anyString(), (File)anyObject(), anyLong());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//
//
//		p = tml.compile(codeDTO,dto);
//
//
//
//	}
//
//	@Test(expected=TMLException.class)
//	public void test_compileHelloWithIntruptedException() throws TMLException{
//		LangParamDTO dto = new LangParamDTO();
//
//		dto.setCompileCmd("javac <filename>.<suffix>");
//
//		dto.setFeedbackRegex("<filename>.<suffix>:@:");
//
//		dto.setRunCmd("java <filename>");
//
//		dto.setTimeout(6000);
//		dto.setSuffix("java");
//		String code ="";
//		CodeDTO codeDTO = new CodeDTO();
//		codeDTO.setFileName("Hello");
//		codeDTO.setCode(code);
//		ConsoleDTO p = null;
//
//
//
//		try {
//			doThrow(new InterruptedException()).when(tmlUtil)
//			.runCmd(anyString(), (File)anyObject(), anyLong());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//
//
//		p = tml.compile(codeDTO,dto);
//
//
//
//	}
//	@Test(expected=TMLException.class)
//	public void test_compileHelloWithTimeoutException() throws TMLException{
//		LangParamDTO dto = new LangParamDTO();
//
//		dto.setCompileCmd("javac <filename>.<suffix>");
//
//		dto.setFeedbackRegex("<filename>.<suffix>:@:");
//
//		dto.setRunCmd("java <filename>");
//
//		dto.setTimeout(6000);
//		dto.setSuffix("java");
//		String code ="";
//		CodeDTO codeDTO = new CodeDTO();
//		codeDTO.setFileName("Hello");
//		codeDTO.setCode(code);
//		ConsoleDTO p = null;
//
//
//
//		try {
//			doThrow(new TimeoutException()).when(tmlUtil)
//			.runCmd(anyString(), (File)anyObject(), anyLong());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//
//
//		p = tml.compile(codeDTO,dto);
//
//
//
//	}
//	@Test
//	public void test_compileHelloWithPlotEmptyDataset() throws TMLException{
//		LangParamDTO dto = new LangParamDTO();
//
//		//dto.setCompileCmd("javac <filename>.<suffix>");
//
//		dto.setFeedbackRegex("<filename>.<suffix>:@:");
//
//		dto.setRunCmd("java <filename>");
//		dto.setPlot("plot");
//		dto.setTimeout(6000);
//		dto.setSuffix("java");
//		String code ="";
//		CodeDTO codeDTO = new CodeDTO();
//		codeDTO.setFileName("Hello");
//		codeDTO.setCode(code);
//		ConsoleDTO p = null;
//
//
//
//		try {
//			when(tmlUtil.runCmd(anyString(), (File)anyObject(), anyLong()))
//			.thenReturn("plot\n");
//			when(convertorService.convert("")).thenReturn("");
//
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		} catch (ServiceConfigurationError e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//
//		p = tml.compile(codeDTO,dto);
//
//		assertTrue(p.isPlot());
//		assertTrue(p.getContent().equals(""));
//
//	}  
//@Test
//	public void test_compileHelloWithPlot() throws TMLException{
//		LangParamDTO dto = new LangParamDTO();
//
//		//dto.setCompileCmd("javac <filename>.<suffix>");
//
//		dto.setFeedbackRegex("<filename>.<suffix>:@:");
//
//		dto.setRunCmd("java <filename>");
//		dto.setPlot("plot");
//		dto.setTimeout(6000);
//		dto.setSuffix("java");
//		String code ="";
//		CodeDTO codeDTO = new CodeDTO();
//		codeDTO.setFileName("Hello");
//		codeDTO.setCode(code);
//		ConsoleDTO p = null;
//
//
//
//		try {
//			when(tmlUtil.runCmd(anyString(), (File)anyObject(), anyLong()))
//			.thenReturn("plot\ndata");
//			when(convertorService.convert("data")).thenReturn("{data}");
//
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		} catch (ServiceConfigurationError e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//
//		p = tml.compile(codeDTO,dto);
//
//		assertTrue(p.isPlot());
//		assertTrue(p.getContent().equals("{data}"));
//
//	}
//	@Test(expected=TMLException.class)
//	public void test_compileHelloWithPlotNoConvertorClass() throws TMLException{
//		LangParamDTO dto = new LangParamDTO();
//
//		//dto.setCompileCmd("javac <filename>.<suffix>");
//
//		dto.setFeedbackRegex("<filename>.<suffix>:@:");
//
//		dto.setRunCmd("java <filename>");
//		dto.setPlot("plot");
//		dto.setTimeout(6000);
//		dto.setSuffix("java");
//		String code ="";
//		CodeDTO codeDTO = new CodeDTO();
//		codeDTO.setFileName("Hello");
//		codeDTO.setCode(code);
//		ConsoleDTO p = null;
//
//
//
//		try {
//			when(tmlUtil.runCmd(anyString(), (File)anyObject(), anyLong()))
//			.thenReturn("plot\n");
//			doThrow(new ClassNotFoundException())
//			.when(convertorService).convert(anyString());
//
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		} catch (ServiceConfigurationError e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//
//		p = tml.compile(codeDTO,dto);
//
//
//
//	}  
//
//	@Test(expected=TMLException.class)
//	public void test_compileHelloWithPlotConverterError() throws TMLException{
//		LangParamDTO dto = new LangParamDTO();
//
//		//dto.setCompileCmd("javac <filename>.<suffix>");
//
//		dto.setFeedbackRegex("<filename>.<suffix>:@:");
//
//		dto.setRunCmd("java <filename>");
//		dto.setPlot("plot");
//		dto.setTimeout(6000);
//		dto.setSuffix("java");
//		String code ="";
//		CodeDTO codeDTO = new CodeDTO();
//		codeDTO.setFileName("Hello");
//		codeDTO.setCode(code);
//		ConsoleDTO p = null;
//
//
//
//		try {
//			when(tmlUtil.runCmd(anyString(), (File)anyObject(), anyLong()))
//			.thenReturn("plot\n");
//			doThrow(new ServiceConfigurationError("message"))
//			.when(convertorService).convert(anyString());
//
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		} catch (ServiceConfigurationError e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//
//		p = tml.compile(codeDTO,dto);
//
//
//
//	}
}
