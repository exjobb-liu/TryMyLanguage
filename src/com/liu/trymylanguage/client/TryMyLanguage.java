package com.liu.trymylanguage.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;

import com.liu.trymylanguage.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import static com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.core.client.JavaScriptObject;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import se.liu.gwt.widgets.client.CodeMirrorConf;

import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.liu.trymylanguage.client.presenter.IDEPresenter;
import com.liu.trymylanguage.client.presenter.NewLangPresenter;
import com.liu.trymylanguage.client.presenter.Presenter;
import com.liu.trymylanguage.client.view.IDEView;
import com.liu.trymylanguage.client.view.IDEViewPre;
import com.liu.trymylanguage.client.view.NewLangUi;
import com.liu.trymylanguage.client.AppController;
import com.liu.trymylanguage.client.TMLServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TryMyLanguage implements EntryPoint {


   

  /**
   * This is the entry point method.
   */
    public void onModuleLoad() {
    	
    	History.newItem("ide");
    	RootLayoutPanel.get().add(new IDEView());
    	History.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String token = event.getValue();
				if (token !=null){
					
					if (token.equals("addLang")){
						RootLayoutPanel.get().add(new NewLangUi());

					} 
					if (token.equals("ide")){
						
						RootLayoutPanel.get().add(new IDEView());
					
					}
					
				} 
					
					
					
				
					
				
			}
		});
    	
    	
	

    }



      
}
