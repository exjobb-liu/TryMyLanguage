package com.liu.trymylanguage.server;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.liu.trymylanguage.client.TMLService;
import com.liu.trymylanguage.client.exception.LangNotFoundException;
import com.liu.trymylanguage.client.exception.TMLException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpSession;

import net.zschech.gwt.comet.server.CometServlet;
import net.zschech.gwt.comet.server.CometSession;

import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

public class TMLServiceImpl extends RemoteServiceServlet 
implements TMLService {

	private static final long serialVersionUID = 1L;

	private CompileServerImpl server;
	private final File tmldir;
	private CometSession cometSession;

	public TMLServiceImpl(){
		this(new File(System.getProperty("catalina.base")==null ?
				"" : System.getProperty("catalina.base") + "/webapps/tml/"));
	}

	public TMLServiceImpl(File tmldir){
		this.server = new CompileServerImpl();
		this.tmldir= tmldir;
	}

	public void compile(CodeDTO code) throws TMLException {
		HttpSession httpSession = getThreadLocalRequest().getSession();
		cometSession = CometServlet.getCometSession(httpSession, false);
		if (cometSession == null) {
			cometSession = CometServlet.getCometSession(httpSession);
		}
		server.compile(code, cometSession);
	} 

	@Override
	public void saveLang(LangParamDTO dto) throws TMLException {
		try {
			Pattern.compile(dto.getFeedbackRegex().replace("@", "(\\d+?)"));
			ObjectOutput output = new ObjectOutputStream(
					new FileOutputStream(
							tmldir.getAbsolutePath()+"/langparam.bin",false));
			output.writeObject(dto);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new TMLException("Can not save the language configuration: \n"
					+e.getMessage());
		} catch (PatternSyntaxException e ){
			throw new TMLException("Syntax error in regular expression for line feedback");
		}

	}
	
	@Override
	public LangParamDTO getLangParam() throws TMLException{
		ObjectInput obj = null;
		LangParamDTO dto;
		try {
			obj = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(tmldir.getAbsolutePath()+"/langparam.bin")));

			dto = (LangParamDTO)obj.readObject();
			if(dto==null)
				throw new LangNotFoundException();
			obj.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new LangNotFoundException();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new TMLException("There has been an error loading language configuration: \n"
					+e.getMessage());
		}catch (IOException e ) {
			e.printStackTrace();
			throw new TMLException("There has been an error loading language configuration: \n"
					+e.getMessage());
		}finally{
			if(obj!=null){
				try {
					obj.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dto;
	}
	
	@Override
	public LangParamDTO getLangParamAddLang() throws TMLException{
		if(getThreadLocalRequest().getRemoteAddr().
				trim().equals("127.0.0.1") ||
				getThreadLocalRequest().getRemoteAddr().
				trim().equals("0:0:0:0:0:0:0:1"))

			return getLangParam();
		else
			throw new TMLException("Access Denied");
	}

	@Override
	public void InvalidateCometSession() {
		if (cometSession != null && cometSession.isValid()) {
			cometSession.invalidate();
		}
	}
}
