package com.liu.trymylanguage.server;


import java.io.File;


import net.zschech.gwt.comet.server.CometSession;

import org.junit.Test;

import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;



import com.liu.trymylanguage.client.exception.TMLException;
import com.liu.trymylanguage.shared.CodeDTO;


public class CompileServerImplTest {

	@Mock private CometSession cSession;
	
	public CompileServerImplTest() {
		MockitoAnnotations.initMocks(this);

	}
	
	@Test
	public void testCompile() {
		

		//CometSession cSession = mock(CometSession.class);
		CompileServerImpl cs = new CompileServerImpl(PlotDataConvertorService.getInstance(),
				new TmlUtil(),
				new File(""));
		try {
			CodeDTO dto = new CodeDTO(
					"Test",
					"test",
					"echo -e Compile1\\nCompile2",
					"echo -e \"Run1\\nRun2\"",
					"",
					false,
					10000);
			dto.setCode("");
			cs.compile(dto, cSession);
			
			verify(cSession).enqueue("Compile1<br />Compile2<br />");
			verify(cSession).enqueue(true);
		} catch (TMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testCompileWithRegex() {
		

		//CometSession cSession = mock(CometSession.class);
		CompileServerImpl cs = new CompileServerImpl(PlotDataConvertorService.getInstance(),
				new TmlUtil(),
				new File(""));
		try {
			CodeDTO dto = new CodeDTO(
					"Test",
					"test",
					"echo -e Compile1\\nCompile2",
					"echo -e \"Run1\\nRun2\"",
					"le@",
					false,
					10000);
			dto.setCode("");
			cs.compile(dto, cSession);
			
			verify(cSession).enqueue("Compi<span class='lineFeedback' onclick='selectLine(event)' id='1'>le1</span><br />" +
					"Compi<span class='lineFeedback' onclick='selectLine(event)' id='2'>le2</span><br />");
			verify(cSession).enqueue(true);
		} catch (TMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
