package com.liu.trymylanguage.client.view;

import se.liu.gwt.widgets.client.CodeMirror2;
import se.liu.gwt.widgets.client.CodeMirrorConf;
import se.liu.gwt.widgets.client.event.CursorActivityEvent;
import se.liu.gwt.widgets.client.event.CursorActivityHandler;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.liu.trymylanguage.shared.LangParamDTO;

public class CodeMirrorScrollWidget extends Composite {
	//private LangParamDTO paramDTO;
	
	public CodeMirrorScrollWidget(LangParamDTO dto){
		//paramDTO = dto;
		final CodeMirror2 editor = new CodeMirror2(getConf(dto));
		final ScrollPanel sp = new ScrollPanel();
		editor.addCursorActivityHandler(new CursorActivityHandler() {
			
			@Override
			public void onCursorActivity(CursorActivityEvent event) {
				
			}
		});
		
		sp.setWidget(editor);
		initWidget(sp);
	}
	private CodeMirrorConf getConf(LangParamDTO dto){

		String[] keywords = dto.getKeywords().split("\\s");
		JSONArray array = new JSONArray();
		for (int i=0;i<keywords.length ;i++ ){
			array.set(i,new JSONString(keywords[i]));

		} 
		String[] stringChar = dto.getStringChar().trim().split("\\s");
		JSONArray sarray = new JSONArray();
		for (int i=0;i<stringChar.length ;i++ ){
			sarray.set(i,new JSONString(stringChar[i]));

		} 

		JSONObject mode = new JSONObject();
		mode.put("name",new JSONString("basemode"));
		mode.put("keywords",array);
		mode.put("stringCh",sarray);
		mode.put("commentSingle",new JSONString(dto.getCommentSingle()));
		mode.put("commentMStart",new JSONString(dto.getCommentMStart()));
		mode.put("commentMEnd",new JSONString(dto.getCommentMEnd()));
		mode.put("escapeCh",new JSONString(dto.getEscapeChar()));
		dto.setOperators(dto.getOperators().replace("-", "\\-"));
		dto.setOperators(dto.getOperators().replace("^", "\\^"));
		mode.put("isOperatorChar",new JSONString("["+dto.getOperators().
				replace(" ", "")+"]"));
		
		CodeMirrorConf conf= new CodeMirrorConf();



		conf.setMode(mode);
		conf.setLineNumbers(true);


		return conf;


	}
	
}
