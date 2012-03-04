package com.liu.trymylanguage.client.view;

import se.liu.gwt.widgets.client.CodeMirror2;
import se.liu.gwt.widgets.client.CodeMirrorConf;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.liu.trymylanguage.client.ErrorDialog;
import com.liu.trymylanguage.client.TMLService;
import com.liu.trymylanguage.client.TMLServiceAsync;
import com.liu.trymylanguage.client.TabWidget;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

public class IDEView extends Composite {

	
	
	@UiField Button runButton;
	@UiField Button newTabButton;
	@UiField TabLayoutPanel tabPanel;
	
	
	private static IDEViewUiBinder uiBinder = GWT.create(IDEViewUiBinder.class);
	private TMLServiceAsync service;
	private CodeMirrorConf conf;
	
	interface IDEViewUiBinder extends UiBinder<Widget, IDEView> {
	}

	public IDEView() {
		service = GWT.create(TMLService.class);
		
		service.getLangParam(new AsyncCallback<LangParamDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				ErrorDialog dialog = new ErrorDialog();
				dialog.setMessage(caught.getMessage());
				dialog.center();
				dialog.show();
			}

			@Override
			public void onSuccess(LangParamDTO result) {
				
				if(result!=null){
					conf = getConf(result);
					ScrollPanel w = new ScrollPanel(new CodeMirror2(conf));
					tabPanel.add(w,
							new TabWidget(tabPanel,false,w));
					
				}else
					showAddLangErrorDialog();
				
				
			}
		});
		
		
		
		
		
		
		
		
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	
	//************ UI Handlers ******************//
	
	@UiHandler("runButton")
	void handleRunClick(ClickEvent e){
		int selected  = tabPanel.getSelectedIndex();
		ScrollPanel sp = (ScrollPanel)tabPanel.getWidget(selected);
		CodeMirror2 cm = (CodeMirror2)sp.getWidget();
		
		
		//service.compile(new CodeDTO(,cm.getValue()), callback)
	}
	
	@UiHandler("newTabButton")
	void handleAddTabClick(ClickEvent e){
		ScrollPanel w = new ScrollPanel(new CodeMirror2(conf));
		tabPanel.add(w,
				new TabWidget(tabPanel, true,w));
		tabPanel.selectTab(w);
		if(tabPanel.getWidgetCount()==2){
			((TabWidget)tabPanel.getTabWidget(0)).setIsCloseable(true);
			
		}
	}
	
	
	
	//************ UI Handlers ******************//
	
	
	
	
	
	
	
	
	
	
	
	private CodeMirrorConf getConf(LangParamDTO dto){
		
		String[] keywords = dto.getKeywords().split("\\s");
		JSONArray array = new JSONArray();
		for (int i=0;i<keywords.length ;i++ ){
			array.set(i,new JSONString(keywords[i]));

		} 
		String[] stringChar = dto.getStringChar().trim().split("\\s");
		JSONArray sarray = new JSONArray();
		for (int i=0;i<stringChar.length ;i++ ){
			sarray.set(i,new JSONString(stringChar[i]));

		} 
		
		JSONObject mode = new JSONObject();
		mode.put("name",new JSONString("basemode"));
		mode.put("keywords",array);
		mode.put("stringCh",sarray);
		mode.put("commentSingle",new JSONString(dto.getCommentSingle()));
		mode.put("commentMStart",new JSONString(dto.getCommentMStart()));
		mode.put("commentMEnd",new JSONString(dto.getCommentMEnd()));
		mode.put("escapeCh",new JSONString(dto.getEscapeChar()));
		mode.put("isOperatorChar",new JSONString("/"+dto.getOperators()+"/"));
		
		CodeMirrorConf conf= new CodeMirrorConf();

		
		
		conf.setMode(mode);
		conf.setLineNumbers(true);
		
		
		return conf;
		
		
	}
	
	private void showAddLangErrorDialog(){
		
		
		ErrorDialog errorDialog = new ErrorDialog();
		errorDialog.setMessage("No Language is Configured");
		errorDialog.center();
		errorDialog.setCloseButtonText("Click here to configure a language");
		errorDialog.addCloseButtonClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("addLang");
				
			}
		});		
		errorDialog.show();
	}
}
