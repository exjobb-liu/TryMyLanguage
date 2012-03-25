package com.liu.trymylanguage.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ErrorDialog extends DialogBox{
	HTML message;
	Button close;
	public ErrorDialog(String error){
		VerticalPanel content = new VerticalPanel();
		message = new HTML();
		close = new Button("Close");
		content.add(message);	
		content.add(close);
		close.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		setGlassEnabled(true);
		// center();
		setHTML("<h3 style=\"color:red\">Error</h3>");
		message.setHTML(error);
		setWidget(content);
		

	}	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		center();
	}
	public void addCloseButtonClickHandler(ClickHandler handler){
		close.addClickHandler(handler);
	
	}
	
	public void setCloseButtonText(String text){
	
		close.setText(text);
	}
	public void setMessage(String content){
	
		message.setHTML(content);
	}
}
