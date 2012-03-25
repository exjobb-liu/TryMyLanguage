package com.liu.trymylanguage.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

public interface TMLServiceAsync {

    void compile(CodeDTO code, AsyncCallback<ConsoleDTO> callback);
    void saveLang(LangParamDTO dto, AsyncCallback<Void> callback);
	void getLangParam(AsyncCallback<LangParamDTO> callback);
	void isConfigured(AsyncCallback<Boolean> callback);
	void getPlotData(String data, AsyncCallback<String> callback);
	
}
