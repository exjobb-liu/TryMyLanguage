package com.liu.trymylanguage.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.event.shared.EventBus;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.liu.trymylanguage.client.TMLServiceAsync;
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
		String getTimeout();
		String getCompileCmd();
		String getRunCmd();
		String getFeedbackRegex();
		String getSuffix();
		void addSaveButtonClickHandler(ClickHandler handler);
		Widget asWidget();
	
	}
	private final Display display;
	public NewLangPresenter(EventBus eventBus, Display view, TMLServiceAsync tmlService){
		display = view;
	
	}
	@Override
	public void go(HasWidgets container) {
		bind();
		
		container.clear();
		container.add(display.asWidget());
	}
	
	
	private void bind(){
		
		display.addSaveButtonClickHandler(new ClickHandler(){

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

		} */
		
		
		final LangParamDTO dto = new LangParamDTO();
		
		dto.setCommentMEnd(display.getCommentMEnd().trim());
		dto.setCommentMStart(display.getCommentMStart().trim());
		dto.setCommentSingle(display.getCommentSingle().trim());
		dto.setCompileCmd(display.getCompileCmd().trim());
		dto.setEscapeChar(display.getEscapeChar().trim());
		dto.setFeedbackRegex(display.getFeedbackRegex().trim());
		dto.setKeywords(display.getKeywords().trim());
		dto.setName(display.getName().trim());
		dto.setOperators(display.getOperators().trim());
		dto.setRunCmd(display.getRunCmd().trim());
		dto.setStringChar(display.getStringChar().trim());
		dto.setTimeout(Long.parseLong(display.getTimeout().trim()));
		dto.setSuffix(display.getSuffix().trim());
		
//		rpcService.saveLang(dto, new AsyncCallback<Void>() {
//			
//			@Override
//			public void onSuccess(Void result) {
//				eventBus.fireEvent(new SaveLangEvent(dto));
//				
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//		});	
//		
		


	}

 }
