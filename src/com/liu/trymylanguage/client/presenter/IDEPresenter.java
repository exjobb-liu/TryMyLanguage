package com.liu.trymylanguage.client.presenter;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.event.shared.HasHandlers;

import com.google.gwt.user.client.ui.ListBox;
import com.liu.trymylanguage.client.TMLServiceAsync;

import com.liu.trymylanguage.client.event.AddLangEvent;
import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.CodeDTO;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import com.liu.trymylanguage.shared.FileTypeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

import se.liu.gwt.widgets.client.CodeMirror2;

/**
 * Describe class IDEPresenter here.
 *
 *
 * Created: Mon Nov 28 07:57:26 2011
 *
 * @author <a href="mailto:amir@amir-laptop">Amir Hossein Fouladi</a>
 * @version 1.0
 */






public class IDEPresenter implements  Presenter {

    /**
     * Creates a new <code>IDEPresenter</code> instance.
     *
     */
    private ConsoleDTO consoleDTO;
    private CodeDTO codeDTO;
   
    public interface Display{
	HasClickHandlers getRunButton();
	HasClickHandlers getAddLangButton();
	void setConsoleData(String data);
	HasKeyPressHandlers getConsole();
	String getSelectedTabValue();
	ListBox getLangBox();
	int getSelectedTabTypeId();
	void setSupportedTypes(Collection<FileTypeDTO> c);
	CodeMirror2 getEditor();
	Widget asWidget();

    }
   
    private final TMLServiceAsync tmlService;
    private final HasHandlers eventBus;
    private final Display display;
	private ArrayList<LangParamDTO> dtos;

    public IDEPresenter(TMLServiceAsync tmlService,HasHandlers eventBus, Display view, ArrayList<LangParamDTO> dtos) {
	this.tmlService = tmlService;
	this.eventBus = eventBus;
	this.display = view;
	this.dtos= dtos;
	
       
    }

    public void bind() {
	display.getRunButton().addClickHandler(new ClickHandler(){
		public void onClick(ClickEvent event){
		    run();
		    
		}
	    });
	display.getAddLangButton().addClickHandler(new ClickHandler(){
	
		public void onClick(ClickEvent event){
			eventBus.fireEvent(new AddLangEvent());
		}
	});
	for (LangParamDTO dto : dtos) {

		display.getLangBox().addItem(dto.getName().toString());
	}
	display.getLangBox().addChangeHandler(new ChangeHandler(){

		public void onChange(ChangeEvent event){
			int index = display.getLangBox().getSelectedIndex();
			LangParamDTO dto = dtos.get(index);
			JSONObject mode = new JSONObject();
			mode.put("name",new JSONString("basemode"));
			mode.put("keywords",dto.getKeywords());
			mode.put("stringCh",dto.getStringChar());
			mode.put("commentSingle",dto.getCommentSingle());
			mode.put("commentMStart",dto.getCommentMStart());
			mode.put("commentMEnd",dto.getCommentMEnd());
			mode.put("escapeCh",dto.getEscapeChar());
			mode.put("isOperatorChar",dto.getOperators());
			display.getEditor().setMode(mode);	
		}
	});

	tmlService.getSupportedTypes(new AsyncCallback<ArrayList<FileTypeDTO>>(){
		// Implementation of com.google.gwt.user.client.rpc.AsyncCallback
		public final void onFailure(final Throwable throwable) {

			//System.out.println("*************");	   
		}

		public final void onSuccess(final ArrayList<FileTypeDTO> types) {
			display.setSupportedTypes(types);		
			System.out.println(types.size());	   
		}
	});


    }


    // Implementation of com.liu.trymylanguage.client.presenter.Presenter

    /**
     * Describe <code>go</code> method here.
     *
     * @param hasWidgets a <code>HasWidgets</code> value
     */
    public final void go(final HasWidgets container) {
	    bind();
	    container.clear();
	    container.add(display.asWidget());
    }


    /**
     * Describe <code>run</code> method here.
     *
     */
    private void run(){
	    CodeDTO codeDTO = new CodeDTO();
	    codeDTO.setCode(display.getSelectedTabValue());
	    codeDTO.setTypeId(display.getSelectedTabTypeId());
	    tmlService.compile(codeDTO, new AsyncCallback<ConsoleDTO>(){
		    // Implementation of com.google.gwt.user.client.rpc.AsyncCallback

		    public final void onFailure(final Throwable throwable) {

		    }

		    public final void onSuccess(final ConsoleDTO consoleDTO) {
			    display.setConsoleData(consoleDTO.getContent());

			    System.out.println("failed");

		    }
	    });
    }
}
