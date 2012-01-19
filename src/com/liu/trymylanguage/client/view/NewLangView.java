package com.liu.trymylanguage.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.liu.trymylanguage.client.presenter.NewLangPresenter;

public class NewLangView extends Composite implements NewLangPresenter.Display {
	private VerticalPanel mainPanel = new VerticalPanel();
	private TextArea name = new TextArea();
	private TextArea  keywords = new TextArea(); 
	private TextBox commentSingle = new TextBox(); 
	private TextBox commentMStart= new TextBox(); 
	private TextBox commentMEnd = new TextBox(); 
	private TextBox stringChar = new TextBox(); 
	private TextBox escapeChar= new TextBox();
	private TextBox operators= new TextBox();
	private Button saveBtn = new Button("Save");
	private HTML title;
	private Label nameLbl;
	private Label keywordsLbl;
	private Label commentSingleLbl;
	private Label commentMStartLbl;
	private Label commentMEndLbl;
	private Label stirngCharLab;
	private Label escapeCharLbl;
	private Label operatorsLbl;
	public NewLangView(){
		nameLbl = new Label("Name:");
		keywordsLbl = new Label("Keywords:");
		commentSingleLbl = new Label("Character(s) for single line comment:");
		commentMStartLbl = new Label("Character(s) for start of the multiple line comment:");
		commentMEndLbl = new Label("Character(s) for end of the multiple line comment:");
		stirngCharLab = new Label("Character(s) for specifing the start and end of an string:");
		escapeCharLbl = new Label("Character for escaping string character within a string:");
		operatorsLbl = new Label("Operator characters:");
		title= new HTML("<h1>Enter the specification of the langugage. You can seperate entities with space.</h1>");
		keywords.setSize("75%","100px");
		mainPanel.add(title);
		mainPanel.add(nameLbl);
		mainPanel.add(name);
		mainPanel.add(keywordsLbl);
		mainPanel.add(keywords);
		mainPanel.add(commentSingleLbl);
		mainPanel.add(commentSingle);
		mainPanel.add(commentMStartLbl);
		mainPanel.add(commentMStart);
		mainPanel.add(commentMEndLbl);
		mainPanel.add(commentMEnd);
		mainPanel.add(stirngCharLab);
		mainPanel.add(stringChar);
		mainPanel.add(escapeCharLbl);
		mainPanel.add(escapeChar);
		mainPanel.add(operatorsLbl);
		mainPanel.add(operators);
		mainPanel.add(saveBtn);
		initWidget(mainPanel);	
	
	}
	@Override
	public String getKeywords() {
		return keywords.getText();
	}

	@Override
	public String getOperators() {
		return operators.getText();
	}

	@Override
	public String getCommentSingle() {
		return commentSingle.getText();
	}

	@Override
	public String getCommentMStart() {
		return commentMStart.getText();
	}

	@Override
	public String getCommentMEnd() {
		return commentMEnd.getText();
	}

	@Override
	public String getEscapeChar() {
		return escapeChar.getText();
	}

	@Override
	public String getStringChar() {
		return stringChar.getText();
	}

	@Override
	public Button getSaveButton() {
		return saveBtn;
	}
	public Widget asWidget(){
		return this;	
	}
	@Override
	public String getName() {
		return name.getText();
	}
	
}
