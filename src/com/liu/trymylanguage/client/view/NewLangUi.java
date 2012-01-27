package com.liu.trymylanguage.client.view;

import com.google.gwt.core.client.GWT;
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
	private static NewLangUiUiBinder uiBinder = GWT
			.create(NewLangUiUiBinder.class);

	interface NewLangUiUiBinder extends UiBinder<Widget, NewLangUi> {
	}

	public NewLangUi() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return nameTextBox.getText();
	}

	@Override
	public String getKeywords() {
		// TODO Auto-generated method stub
		return keywordsTextArea.getText();
	}

	@Override
	public String getOperators() {
		// TODO Auto-generated method stub
		return operatorsTextBox.getText();
	}

	@Override
	public String getCommentSingle() {
		// TODO Auto-generated method stub
		return commentSingleTextBox.getText();
	}

	@Override
	public String getCommentMStart() {
		// TODO Auto-generated method stub
		return commentMStartTextBox.getText();
	}

	@Override
	public String getCommentMEnd() {
		// TODO Auto-generated method stub
		return commentMEndTextBox.getText();
	}

	@Override
	public String getEscapeChar() {
		// TODO Auto-generated method stub
		return escapeCharTextBox.getText();
	}

	@Override
	public String getStringChar() {
		// TODO Auto-generated method stub
		return stringCharTextBox.getText();
	}

	@Override
	public Button getSaveButton() {
		// TODO Auto-generated method stub
		return saveButton;
	}

	@Override
	public String getTimeout() {
		// TODO Auto-generated method stub
		return timeoutTextBox.getText();
	}

	@Override
	public String getCompileCmd() {
		// TODO Auto-generated method stub
		return compileTextBox.getText();
	}

	@Override
	public String getRunCmd() {
		// TODO Auto-generated method stub
		return runTextBox.getText();
	}

	@Override
	public String getFeedbackRegex() {
		// TODO Auto-generated method stub
		return feedbackTextBox.getText();
	}

}
