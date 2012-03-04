package com.liu.trymylanguage.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



public class TabWidget extends Composite {
	private Label title;
	private Button close;
	private RenameDialog rename;
	private HorizontalPanel panel;
	private TabLayoutPanel parent;
	private boolean isCloseable;
	private Widget widget;
	public TabWidget(final TabLayoutPanel parent, boolean isCloseable, Widget widget){
		this.widget = widget;
		this.parent = parent;
		this.isCloseable = isCloseable;
		
		
		
		this.title= new Label("default");
		
		
		this.title.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				rename = new RenameDialog(title.getText());
				rename.center();
				rename.show();
				
			}
		});
		panel = new HorizontalPanel();
	
		panel.add(this.title);
		
		setIsCloseable(isCloseable);
		
		
		
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
					TabWidget.this.title.setText(nameText.getText());
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
	public String toString(){
		return title.getText();
		
		
	}
	
	public void setIsCloseable(boolean isCloseable){
		
		this.isCloseable = isCloseable;
		if(isCloseable){
			
			if(close == null){
				close = new Button("x");
				
			}
			close.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					//int index = parent.getSelectedIndex();
					int index = parent.getWidgetIndex(widget);
					try {
						parent.selectTab(index+1);
					} catch (AssertionError e) {
						parent.selectTab(index-1);
					}
					
					parent.remove(widget);
					 
				//	System.out.println(parent.getWidgetCount());
				//	parent.selectTab(parent.getWidgetCount()-1);
					if(parent.getWidgetCount()==1){
						((TabWidget)parent.getTabWidget(0)).setIsCloseable(false);
						
					}
				}
			});
			
			panel.add(close);
			
		}else if(close!=null)
			panel.remove(close);
		
		
	}
	
	
	
}
