package com.liu.trymylanguage.client;

import java.util.ArrayList;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import com.google.gwt.user.client.History;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.event.shared.EventBus;

import com.liu.trymylanguage.client.event.AddLangEvent;
import com.liu.trymylanguage.client.event.AddLangEventHandler;
import com.liu.trymylanguage.client.event.SaveLangEvent;
import com.liu.trymylanguage.client.event.SaveLangEventHandler;
import com.liu.trymylanguage.client.presenter.NewLangPresenter;
import com.liu.trymylanguage.client.presenter.Presenter;
import com.liu.trymylanguage.client.presenter.IDEPresenter;

import com.liu.trymylanguage.client.view.IDEView;
import com.liu.trymylanguage.client.view.NewLangUi;

import com.liu.trymylanguage.shared.LangParamDTO;

/**
 * Describe class AppController here.
 *
 *
 * Created: Mon Nov 28 13:57:12 2011
 *
 * @author <a href="mailto:amir@amir-laptop">Amir Hossein Fouladi</a>
 * @version 1.0
 */
public class AppController implements Presenter,ValueChangeHandler<String> {

	/**
	 * Creates a new <code>AppController</code> instance.
	 *
	 */


	private final EventBus eventBus;
	private final TMLServiceAsync rpcService;
	private HasWidgets container;
	private ArrayList<LangParamDTO> dtos;
	public AppController(EventBus eventBus,TMLServiceAsync rpcService) {
		this.eventBus = eventBus;
		this.rpcService= rpcService;
		this.bind();
		dtos = new ArrayList<LangParamDTO>();
	}

	private void bind() {
		History.addValueChangeHandler(this);
		eventBus.addHandler(AddLangEvent.TYPE,
				new AddLangEventHandler(){
					public void onAddLang(AddLangEvent event){
						doAddLang();
					}

				});

		eventBus.addHandler(SaveLangEvent.TYPE,
				new SaveLangEventHandler(){
					public void onSaveLang(SaveLangEvent event){
						LangParamDTO dto = event.getValue();
						
						History.newItem("ide");
					}

				});

	}
	private void doAddLang() {
		History.newItem("addLang");

	}
	// Implementation of com.liu.trymylanguage.client.presenter.Presenter

	/**
	 * Describe <code>go</code> method here.
	 * A Peresenter which acts as the the main controller of the application
	 * @param hasWidgets a <code>HasWidgets</code> value
	 */
	public final void go(final HasWidgets container) {
		this.container=container;
		if ("".equals(History.getToken())) {
			History.newItem("ide");
		}
		else {
			History.fireCurrentHistoryState();
   		}

	}

	@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			String token = event.getValue();
			if (token !=null){
				Presenter presenter = null;
				if (token.equals("addLang")){
					presenter = new NewLangPresenter(eventBus, new NewLangUi(), rpcService);

				} 
				if (token.equals("ide")){
					
					presenter = new IDEPresenter(rpcService,eventBus);
				
				}
				if (presenter !=null){
					presenter.go(container);

				} 
			} 
		}

}
