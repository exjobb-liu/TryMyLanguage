package com.liu.trymylanguage.client;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.liu.trymylanguage.client.view.IDEView;
import com.liu.trymylanguage.client.view.NewLangUi;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TryMyLanguage implements EntryPoint {


   

  /**
   * This is the entry point method.
   */
    public void onModuleLoad() {
    	
    	History.newItem("ide");
    	
				RootPanel.get("content").add(new IDEView());
				Window.addResizeHandler(new ResizeHandler() {
					
					@Override
					public void onResize(ResizeEvent event) {
						setContentSize();
					}
				});
				
			
    	History.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String token = event.getValue();
				if (token !=null){
				
					if (token.equals("configure")){
						
						RootPanel.get("content").clear();
						RootPanel.get("content").add(new NewLangUi());

					} 
					if (token.equals("ide")){
						RootPanel.get("content").clear();
						RootPanel.get("content").add(new IDEView());
					
					}
					
				} 
					
					
					
				
					
				
			}
		});
    
    	
    	setContentSize();	

    }

    private void setContentSize(){
    	
    	int size=DOM.
				getElementById("header").getOffsetHeight();
		size +=DOM.getElementById("footer").getOffsetHeight();
		
		RootPanel.get("content").setHeight(Window.getClientHeight()-size-10+"px");
    	
    }

      
}
