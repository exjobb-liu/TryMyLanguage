package com.liu.trymylanguage.client;

import com.liu.trymylanguage.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.liu.trymylanguage.client.presenter.Presenter;
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
	
	HasHandlers eventBus= new SimpleEventBus();
	final TMLServiceAsync tmlService = GWT.create(TMLService.class);
	Presenter appViewer = new AppController(eventBus,tmlService);
	appViewer.go(RootLayoutPanel.get());
	

    }

      
}
