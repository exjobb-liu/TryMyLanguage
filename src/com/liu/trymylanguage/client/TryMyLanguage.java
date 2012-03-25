package com.liu.trymylanguage.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
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
	
    	
    	History.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String token = event.getValue();
				if (token !=null){
					
					if (token.equals("addLang")){
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
    	
    	
	

    }



      
}
