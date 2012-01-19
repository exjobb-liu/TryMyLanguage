package com.liu.trymylanguage.client.event;

import com.google.gwt.event.shared.GwtEvent;

import com.liu.trymylanguage.shared.LangParamDTO;

public class SaveLangEvent extends GwtEvent<SaveLangEventHandler>{
	private LangParamDTO value;
	public SaveLangEvent(LangParamDTO value){
	
		this.value= value;
	}
	public static Type<SaveLangEventHandler> TYPE = new Type<SaveLangEventHandler>();
	@Override
	public Type<SaveLangEventHandler> getAssociatedType() {
		return TYPE;
	}
	public LangParamDTO getValue(){
		return value;
	
	}

	@Override
	protected void dispatch(SaveLangEventHandler handler) {
		handler.onSaveLang(this);		
	}
	
}
