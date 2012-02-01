package com.liu.trymylanguage.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;



public class TabWidget extends Composite {
	private HTML title;
	private HTML close;
	private RenameDialog rename;
	private HorizontalPanel panel;
	public TabWidget(final String title,ClickHandler closeHandler){
		close = new HTML("<span style='margin: 3px; border:1px solid black'>x</span>");
		
		this.title= new HTML(title);
		close.addClickHandler(closeHandler);
		
		this.title.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				rename = new RenameDialog(title);
				rename.center();
				rename.show();
				
			}
		});
		panel = new HorizontalPanel();
		panel.add(this.title);
		panel.add(close);
		initWidget(panel);
		
	}
	
	public class RenameDialog extends DialogBox{
		
		private TextBox nameText;
		private Button ok;
		private Button close;
		private VerticalPanel mainPanel;
		private FlowPanel buttonPanel;
		public RenameDialog(String title){
			
			mainPanel = new VerticalPanel();
			buttonPanel = new FlowPanel();
			setText("Rename:");
			nameText = new TextBox();
			nameText.setText(title);
			ok = new Button("Ok");
			close = new Button("close");
			
			ok.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					TabWidget.this.title.setHTML(nameText.getText());
					hide();
					
				}
			});
			close.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					hide();
					
				}
			});
			
			buttonPanel.add(ok);
			buttonPanel.add(close);
			mainPanel.add(nameText);
			mainPanel.add(buttonPanel);
			setWidget(mainPanel);
		}
		
		
	}
}
