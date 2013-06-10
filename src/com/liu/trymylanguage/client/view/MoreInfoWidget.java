package com.liu.trymylanguage.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class MoreInfoWidget extends Composite implements HasHTML {

	private static MoreInfoWidgetUiBinder uiBinder = GWT
			.create(MoreInfoWidgetUiBinder.class);

	interface MoreInfoWidgetUiBinder extends UiBinder<Widget, MoreInfoWidget> {
	}

	public MoreInfoWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Image questionIcon;
	
	DialogBox dialog = new DialogBox();
	
	HTML dialogContent = new HTML();
	
	
	public MoreInfoWidget(String firstName) {
		
		dialog.add(dialogContent);
		
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	

	@UiHandler("questionIcon")
	void onClick(ClickEvent e) {
		dialog.setAnimationEnabled(true);
		dialog.setAutoHideEnabled(true);
		
		/*dialog.addHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				dialog.hide();
				
			}
		}, MouseOutEvent.getType());
		*/
		if(dialog.isAttached()){
			
			dialog.hide();
		}
		else
			dialog.showRelativeTo(questionIcon);
	}

	
	@Override
	public String getHTML() {
		
		return dialogContent.getHTML();
	}

	@Override
	public void setHTML(String html) {
		dialog.setHTML(html);
		
	}

	@Override
	public String getText() {
		
		return dialog.getText();
	}

	@Override
	public void setText(String text) {
		dialog.setText(text);
		
	}
	
}
