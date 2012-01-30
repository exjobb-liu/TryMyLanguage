package com.liu.trymylanguage.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.FileTypeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

public interface TMLServiceAsync {

    void compile(CodeDTO code, AsyncCallback<ConsoleDTO> callback);
    void getSupportedTypes(AsyncCallback<ArrayList<FileTypeDTO>> callback);
	void saveLang(LangParamDTO dto, AsyncCallback<Void> callback);
	void getLangParam(AsyncCallback<LangParamDTO> callback);
}
