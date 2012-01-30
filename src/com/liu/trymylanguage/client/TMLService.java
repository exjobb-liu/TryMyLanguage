package com.liu.trymylanguage.client;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.FileTypeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

@RemoteServiceRelativePath("tmlservice")
public interface TMLService extends RemoteService {

    ConsoleDTO compile(CodeDTO source) throws Exception;
    
    
    void saveLang(LangParamDTO dto);
    LangParamDTO getLangParam(); 
}
