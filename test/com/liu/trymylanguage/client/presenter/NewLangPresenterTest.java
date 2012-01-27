package com.liu.trymylanguage.client.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.liu.trymylanguage.client.TMLServiceAsync;
import com.liu.trymylanguage.client.event.SaveLangEvent;
import com.liu.trymylanguage.shared.LangParamDTO;

public class NewLangPresenterTest {

	
	@Mock
	private HasWidgets container;
	@Mock
	private NewLangPresenter.Display display;
	@Mock
	private com.google.gwt.event.shared.EventBus eventBus;
	@Mock
	private TMLServiceAsync rpcService;
	
	
	public NewLangPresenterTest(){
		MockitoAnnotations.initMocks(this);
		
		
	}
	@Test
	public void testGo() {
		
		
		
		
		
		
		
		LangParamDTO dto = mock(LangParamDTO.class);
		
		NewLangPresenter p = new NewLangPresenter(eventBus, display, rpcService);
		p.go(container);
		//SaveLangEvent saveEvent = mock(SaveLangEvent.class);
		// display.getSaveButton().click();
		verify(display).addSaveButtonClickHandler(isA(ClickHandler.class));
		
		
	}

}
