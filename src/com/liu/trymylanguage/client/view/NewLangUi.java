package com.liu.trymylanguage.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.liu.trymylanguage.client.presenter.NewLangPresenter;

public class NewLangUi extends Composite implements NewLangPresenter.Display{

	@UiField
	Button saveButton;
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
		return null;
	}

	@Override
	public String getKeywords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOperators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommentSingle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommentMStart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommentMEnd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEscapeChar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStringChar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Button getSaveButton() {
		// TODO Auto-generated method stub
		return saveButton;
	}

}
