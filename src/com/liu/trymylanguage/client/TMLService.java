package com.liu.trymylanguage.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.FileTypeDTO;

@RemoteServiceRelativePath("tmlservice")
public interface TMLService extends RemoteService {

    ConsoleDTO compile(CodeDTO source);
    ConsoleDTO run(CodeDTO source);
    ArrayList<FileTypeDTO> getSupportedTypes();  

}
