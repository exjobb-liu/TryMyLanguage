package com.liu.trymylanguage.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
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
	@UiField TextBox plot;
	@UiField TextArea sampleProgram;
	

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
		dto.setCommentMEnd(commentMEndTextBox.getText());
		dto.setCommentMStart(commentMStartTextBox.getText());
		dto.setCommentSingle(commentSingleTextBox.getText());
		dto.setCompileCmd(compileTextBox.getText());
		dto.setEscapeChar(escapeCharTextBox.getText());
		dto.setFeedbackRegex(feedbackTextBox.getText());
		dto.setKeywords(keywordsTextArea.getText());
		dto.setName(nameTextBox.getText());
		dto.setOperators(operatorsTextBox.getText());
		dto.setPlot(plot.getText());
		dto.setRunCmd(runTextBox.getText());
		dto.setSampleProgram(sampleProgram.getText());
		
		dto.setStringChar(stringCharTextBox.getText());
		dto.setSuffix(suffixTextBox.getText());
		dto.setTimeout(Long.parseLong(timeoutTextBox.getText().trim()));
		
		
		service.saveLang(dto, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				new ErrorDialog("An Error occured while saving langugage data: \n"
						+caught.getMessage()).show();
				
			}

			@Override
			public void onSuccess(Void result) {
				History.newItem("ide");
				
			}
		});
		
	}
	private void loadConf(){
		service.getLangParam(new AsyncCallback<LangParamDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				if(!(caught instanceof LangNotFoundException))
					new ErrorDialog(caught.getMessage()).show();
				
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
					plot.setText(result.getPlot());
					sampleProgram.setText(result.getSampleProgram());
					
					
				
				}
				
			}
		});
		
		
		
	}

}
