package com.liu.trymylanguage.client;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.event.shared.EventBus;
import com.liu.trymylanguage.client.presenter.Presenter;
import com.google.gwt.event.shared.HasHandlers;
import com.liu.trymylanguage.client.presenter.IDEPresenter;

import com.liu.trymylanguage.client.view.IDEView;

/**
 * Describe class AppController here.
 *
 *
 * Created: Mon Nov 28 13:57:12 2011
 *
 * @author <a href="mailto:amir@amir-laptop">Amir Hossein Fouladi</a>
 * @version 1.0
 */
public class AppController implements Presenter {

    /**
     * Creates a new <code>AppController</code> instance.
     *
     */
   
   
    private final HasHandlers eventBus;
    private final TMLServiceAsync rpcService;
    public AppController(HasHandlers eventBus,TMLServiceAsync rpcService) {
	this.eventBus = eventBus;
	this.rpcService= rpcService;
    }

    // Implementation of com.liu.trymylanguage.client.presenter.Presenter

    /**
     * Describe <code>go</code> method here.
     * A Peresenter which acts as the the main controller of the application
     * @param hasWidgets a <code>HasWidgets</code> value
     */
    public final void go(final HasWidgets container) {
	Presenter presenter = new IDEPresenter(rpcService,eventBus,new IDEView());
	presenter.go(container);
    }

}
