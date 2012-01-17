package com.liu.trymylanguage.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.liu.trymylanguage.client.presenter.NewLangPresenter;

public class NewLangView extends Composite implements NewLangPresenter.Display {
	private VerticalPanel mainPanel = new VerticalPanel();
	private TextArea  keywords = new TextArea(); 
	private TextBox commentSingle = new TextBox(); 
	private TextBox commentMStart= new TextBox(); 
	private TextBox commentMEnd = new TextBox(); 
	private TextBox stringChar = new TextBox(); 
	private TextBox escapeChar= new TextBox();
	private TextBox operators= new TextBox();
	private Button saveBtn = new Button("Save");
	public NewLangView(){
		mainPanel.add(keywords);
		mainPanel.add(commentSingle);
		mainPanel.add(commentMStart);
		mainPanel.add(commentMEnd);
		mainPanel.add(stringChar);
		mainPanel.add(escapeChar);
		mainPanel.add(operators);
		mainPanel.add(saveBtn);
		initWidget(mainPanel);	
	
	}
	@Override
	public String getKeywords() {
		return keywords.getText().trim();
	}

	@Override
	public String getOperators() {
		return operators.getText().trim();
	}

	@Override
	public String getCommentSingle() {
		return commentSingle.getText().trim();
	}

	@Override
	public String getCommentMStart() {
		return commentMStart.getText().trim();
	}

	@Override
	public String getCommentMEnd() {
		return commentMEnd.getText().trim();
	}

	@Override
	public String getEscapeChar() {
		return escapeChar.getText().trim();
	}

	@Override
	public String getStringChar() {
		return stringChar.getText().trim();
	}

	@Override
	public Button getSaveButton() {
		return saveBtn;
	}
	public Widget asWidget(){
		return this;	
	}
	
}
