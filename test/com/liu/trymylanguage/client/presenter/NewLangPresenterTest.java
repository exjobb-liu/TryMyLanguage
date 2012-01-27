package com.liu.trymylanguage.client.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import org.junit.Test;

import com.google.gwt.event.shared.EventBus;

public class NewLangPresenterTest {

	@Test
	public void testGo() {
		
		NewLangPresenter.Display display = mock(NewLangPresenter.Display.class);
		com.google.gwt.event.shared.EventBus eventBus = mock(EventBus.class);
		Presenter p = new NewLangPresenter(eventBus,display);
	
		
		
	}

}
