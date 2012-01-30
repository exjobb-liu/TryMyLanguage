package com.liu.trymylanguage.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.liu.trymylanguage.client.presenter.NewLangPresenter;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextArea;

public class NewLangUi extends Composite implements NewLangPresenter.Display{

	@UiField
	Button saveButton;
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

	
	private static NewLangUiUiBinder uiBinder = GWT
			.create(NewLangUiUiBinder.class);

	interface NewLangUiUiBinder extends UiBinder<Widget, NewLangUi> {
	}

	public NewLangUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String getName() {
		
		return nameTextBox.getText();
	}

	@Override
	public String getKeywords() {
		
		return keywordsTextArea.getText();
	}

	@Override
	public String getOperators() {
		
		return operatorsTextBox.getText();
	}

	@Override
	public String getCommentSingle() {
		
		return commentSingleTextBox.getText();
	}

	@Override
	public String getCommentMStart() {
		
		return commentMStartTextBox.getText();
	}

	@Override
	public String getCommentMEnd() {
		
		return commentMEndTextBox.getText();
	}

	@Override
	public String getEscapeChar() {
		return escapeCharTextBox.getText();
	}

	@Override
	public String getStringChar() {
		
		return stringCharTextBox.getText();
	}

	

	@Override
	public String getTimeout() {
		
		return timeoutTextBox.getText();
	}

	@Override
	public String getCompileCmd() {
		
		return compileTextBox.getText();
	}

	@Override
	public String getRunCmd() {
		
		return runTextBox.getText();
	}

	@Override
	public String getFeedbackRegex() {
		
		return feedbackTextBox.getText();
	}
	
	
	public String getSuffix(){
		return suffixTextBox.getText();
	}

	@Override
	public void addSaveButtonClickHandler(ClickHandler handler) {
		
		saveButton.addClickHandler(handler);
	}

}
