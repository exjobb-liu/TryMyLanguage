package com.liu.trymylanguage.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.liu.trymylanguage.client.ErrorDialog;
import com.liu.trymylanguage.client.TMLService;
import com.liu.trymylanguage.client.TMLServiceAsync;
import com.liu.trymylanguage.client.exception.LangNotFoundException;
import com.liu.trymylanguage.shared.LangParamDTO;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextArea;

public class NewLangUi extends Composite {

	@UiField Button saveButton;
	@UiField TextBox nameTextBox;
	@UiField TextArea keywordsTextArea;
	@UiField TextBox commentSingleTextBox;
	@UiField TextBox commentMStartTextBox;
	@UiField TextBox commentMEndTextBox;
	@UiField TextBox stringCharTextBox;
	@UiField TextBox escapeCharTextBox;
	@UiField TextBox operatorsTextBox;
	@UiField TextBox timeoutTextBox;
	@UiField TextBox compileTextBox;
	@UiField TextBox runTextBox;
	@UiField TextBox feedbackTextBox;
	@UiField TextBox suffixTextBox;
	@UiField HTML valMessage;

	private LangParamDTO dto;
	private TMLServiceAsync service;
	private static NewLangUiUiBinder uiBinder = GWT
			.create(NewLangUiUiBinder.class);

	interface NewLangUiUiBinder extends UiBinder<Widget, NewLangUi> {
	}

	public NewLangUi() {
		service = GWT.create(TMLService.class);
		
		loadConf();
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
		
	@UiHandler("saveButton")
	void handleSaveClick(ClickEvent e){
		if(dto==null)
			dto = new LangParamDTO();
		String errorMsg="";
		
		if(nameTextBox.getText()==null || nameTextBox.getText().trim().equals(""))
			errorMsg+="Name field can not be empty<br />";
		if(timeoutTextBox.getText()== null ||
				timeoutTextBox.getText().trim().equals(""))
			timeoutTextBox.setText("0");
		
		if(runTextBox.getText()==null || 
				runTextBox.getText().trim().equals(""))
			errorMsg+="Run command can not be empty<br />";
		if(feedbackTextBox.getText()!=null  &&
				!feedbackTextBox.getText().trim().equals("") &&
				!feedbackTextBox.getText().contains("@"))
			errorMsg+="Regular expression for line feedback should contain @<br />";
		if(feedbackTextBox.getText()!=null && 
				feedbackTextBox.getText().trim().equals("@"))
			errorMsg+="Regular expression for line feedback should be more specific<br />";
		if(commentMStartTextBox.getText()
				.trim().equals(commentMEndTextBox.getText().trim()))
			errorMsg+="Characters for starting and ending comments can not be the same<br />";
		try {
			Long.parseLong(timeoutTextBox.getText().trim());
		} catch (NumberFormatException e2) {
			errorMsg+="Only long integers are allowed for timeout";
		}
		
		
		
		if(errorMsg.equals("")){
			dto.setCommentMEnd(commentMEndTextBox.getText());
			dto.setCommentMStart(commentMStartTextBox.getText());
			dto.setCommentSingle(commentSingleTextBox.getText());
			dto.setCompileCmd(compileTextBox.getText());
			dto.setEscapeChar(escapeCharTextBox.getText());
			dto.setFeedbackRegex(feedbackTextBox.getText());
			dto.setKeywords(keywordsTextArea.getText());
			dto.setName(nameTextBox.getText());
			dto.setOperators(operatorsTextBox.getText());
			dto.setRunCmd(runTextBox.getText());
			dto.setStringChar(stringCharTextBox.getText());
			dto.setSuffix(suffixTextBox.getText());
			dto.setTimeout(Long.parseLong(timeoutTextBox.getText().trim()));
			
			
			service.saveLang(dto, new AsyncCallback<Void>() {
	
				@Override
				public void onFailure(Throwable caught) {
					new ErrorDialog(caught).show();
					
				}
	
				@Override
				public void onSuccess(Void result) {
					History.newItem("ide");
					
				}
			});
		} else {
			
			valMessage.setHTML(errorMsg);
			
		}
	}
	private void loadConf(){
		service.getLangParamAddLang(new AsyncCallback<LangParamDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().equals("Access Denied")){
					DialogBox dialog = new DialogBox();
					dialog.setHTML("<h3 style=\"color:red\">Access Denied</h3>");
					dialog.setGlassEnabled(true);
					Button backBtn = new Button("Back");
					backBtn.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							History.newItem("ide");
							
						}
					});
					dialog.setAutoHideOnHistoryEventsEnabled(true);
					dialog.setModal(true);
					dialog.setWidget(backBtn);
					dialog.center();
					dialog.show();
				}else if(!(caught instanceof LangNotFoundException))
					 new ErrorDialog(caught).show();
					
				
			}

			@Override
			public void onSuccess(LangParamDTO result) {
				if(result!=null){
					nameTextBox.setText(result.getName());
					keywordsTextArea.setText(result.getKeywords());
					commentSingleTextBox.setText(result.getCommentSingle());
					commentMStartTextBox.setText(result.getCommentMStart());
					commentMEndTextBox.setText(result.getCommentMEnd());
					stringCharTextBox.setText(result.getStringChar());
					escapeCharTextBox.setText(result.getEscapeChar());
					operatorsTextBox.setText(result.getOperators());
					timeoutTextBox.setText(result.getTimeout()+"");
					compileTextBox.setText(result.getCompileCmd());
					runTextBox.setText(result.getRunCmd());
					feedbackTextBox.setText(result.getFeedbackRegex());
					suffixTextBox.setText(result.getSuffix());
					
				
				}
				
			}
		});
		
		
		
	}

}
