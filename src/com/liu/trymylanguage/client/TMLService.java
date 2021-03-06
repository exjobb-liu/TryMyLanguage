package com.liu.trymylanguage.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.liu.trymylanguage.client.exception.TMLException;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

@RemoteServiceRelativePath("tmlservice")
public interface TMLService extends RemoteService {

    void compile(CodeDTO source) throws  TMLException;
    
    
    void saveLang(LangParamDTO dto) throws TMLException;
    LangParamDTO getLangParam() throws TMLException;
    LangParamDTO getLangParamAddLang() throws TMLException;
    void InvalidateCometSession();
   
   
}
