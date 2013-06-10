package com.liu.trymylanguage.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.liu.trymylanguage.client.exception.TMLException;

public class ErrorDialog extends DialogBox{
	
	HTML message;
	Button close;

	public ErrorDialog(Throwable error){


		VerticalPanel content = new VerticalPanel();

		message = new HTML();
		close = new Button("Close");
		content.add(message);

		DisclosurePanel detail = new DisclosurePanel();
		detail.setHeader(new Label("Details:"));
		ScrollPanel sp = new ScrollPanel(new HTML(error.getStackTrace()!=null && 
				error instanceof TMLException && 
				((TMLException)error).getStackTraceString() != null ?
						"<pre>" + ((TMLException)error).getStackTraceString() + "</pre>": "Check server log for details"));
		detail.setContent(sp);
		content.add(detail);
		sp.setWidth("600px");
		sp.setHeight("200px");

		content.add(close);
		close.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		setGlassEnabled(true);
		setHTML("<h3 style=\"color:red\">Error</h3>");
		message.setHTML(error.getMessage());

		setWidget(content);


	}	
	
	@Override
	protected void onLoad() {
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
