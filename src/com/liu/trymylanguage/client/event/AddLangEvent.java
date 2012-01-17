package com.liu.trymylanguage.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddLangEvent extends GwtEvent<AddLangEventHandler>{
	public static Type<AddLangEventHandler> TYPE = new Type<AddLangEventHandler>();
	@Override
	public Type<AddLangEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddLangEventHandler handler) {
		handler.onAddLang(this);		
	}
	
}
