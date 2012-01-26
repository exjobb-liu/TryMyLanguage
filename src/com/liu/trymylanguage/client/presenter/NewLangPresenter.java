package com.liu.trymylanguage.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.event.shared.EventBus;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.liu.trymylanguage.client.event.SaveLangEvent;

import com.liu.trymylanguage.shared.LangParamDTO;

 public class NewLangPresenter implements Presenter {
	public interface Display {
		String getName();
		String getKeywords();
		String getOperators();
		String getCommentSingle();
		String getCommentMStart();
		String getCommentMEnd();
		String getEscapeChar();
		String getStringChar();
		Button getSaveButton();
		Widget asWidget();
	
	}
	private final Display display;
	private final EventBus eventBus;
	//private final TMLServiceAsync rpcService;
	
	

	public NewLangPresenter(EventBus eventBus, Display view){
		this.eventBus = eventBus;
		display = view;
	
	}
	@Override
	public void go(HasWidgets container) {
		bind();
		
		container.clear();
		container.add(display.asWidget());
	}
	
	
	private void bind(){
		
		display.getSaveButton().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				run();
			}
			
		});
	
	}
	private void run(){
/*		langDTO.setKeywords(display.getKeywords()
				.replace("\\","\\\\").replace("\"","\\\"").replace("\'","\\\'"));
		langDTO.setOperators(display.getOperators()
				.replace("\\","\\\\").replace("\"","\\\"").replace("\'","\\\'"));
langDTO.setCommentSingle(display.getCommentSingle()
				.replace("\\","\\\\").replace("\"","\\\"").replace("\'","\\\'"));
		langDTO.setCommentMStart(display.getCommentMStart()
				.replace("\\","\\\\").replace("\"","\\\"").replace("\'","\\\'"));
		langDTO.setCommentMEnd(display.getCommentMEnd()
				.replace("\\","\\\\").replace("\"","\\\"").replace("\'","\\\'"));
		langDTO.setEscapeChar(display.getEscapeChar()
				.replace("\\","\\\\").replace("\"","\\\"").replace("\'","\\\'"));
		langDTO.setStringChar(display.getStringChar()
				.replace("\\","\\\\").replace("\"","\\\"").replace("\'","\\\'"));
*/	
		LangParamDTO dto = new LangParamDTO();
		String[] keywords = display.getKeywords().trim().split("\\s");
		JSONArray array = new JSONArray();
		for (int i=0;i<keywords.length ;i++ ){
			array.set(i,new JSONString(keywords[i]));

		} 
		String[] stringChar = display.getStringChar().trim().split("\\s");
		JSONArray sarray = new JSONArray();
		for (int i=0;i<stringChar.length ;i++ ){
			sarray.set(i,new JSONString(stringChar[i]));

		} 
		dto.setStringChar(sarray);
		dto.setKeywords(array);
		dto.setName(new JSONString(display.getName()));
		dto.setCommentMEnd(new JSONString(display.getCommentMEnd()));
		dto.setCommentMStart(new JSONString(display.getCommentMStart()));
		dto.setCommentSingle(new JSONString(display.getCommentSingle()));
		dto.setEscapeChar(new JSONString(display.getEscapeChar()));
		dto.setOperators(new JSONString(display.getOperators()));

		eventBus.fireEvent(new SaveLangEvent(dto));



	}

 }
