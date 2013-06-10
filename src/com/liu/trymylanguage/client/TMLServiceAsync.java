package com.liu.trymylanguage.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

public interface TMLServiceAsync {

    void compile(CodeDTO source, AsyncCallback<Void> callback);
    void saveLang(LangParamDTO dto, AsyncCallback<Void> callback);
	void getLangParam(AsyncCallback<LangParamDTO> callback);
	void getLangParamAddLang(AsyncCallback<LangParamDTO> callback);
	void InvalidateCometSession(AsyncCallback<Void> callback);
	
}
