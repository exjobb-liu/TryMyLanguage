package com.liu.trymylanguage.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.liu.trymylanguage.shared.LangParamDTO;

 public class NewLangPresenter implements Presenter {
	public interface Display {
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
	private LangParamDTO langDTO = new LangParamDTO();

	public NewLangPresenter(Display view){
		display = view;
	
	}
	@Override
	public void go(HasWidgets container) {
		
		container.clear();
		container.add(display.asWidget());
	}
	private void bind(){
		display.getSaveButton().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
	
	}
	private void run(){
		langDTO.setKeywords(display.getKeywords()
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
	
	}
 	
 }
