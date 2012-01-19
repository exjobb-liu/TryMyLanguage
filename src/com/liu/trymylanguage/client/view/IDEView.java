package com.liu.trymylanguage.client.view;

import java.util.Collection;
import java.util.Iterator;

import com.google.gwt.event.logical.shared.AttachEvent;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.liu.trymylanguage.client.presenter.IDEPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;

import com.liu.trymylanguage.shared.FileTypeDTO;
import se.liu.gwt.widgets.client.CodeMirrorConf;
import se.liu.gwt.widgets.client.CodeMirror2;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.AttachEvent;

/**
 * Describe class IDEView here.
 *
 *
 * Created: Fri Dec  2 15:19:53 2011
 *
 * @author <a href="mailto:amir@amir-laptop">Amir Hossein Fouladi</a>
 * @version 1.0
 */
public class IDEView extends Composite implements IDEPresenter.Display{
	private SplitLayoutPanel mainPanel = new SplitLayoutPanel();

	private LayoutPanel editorPanel = new LayoutPanel();
	private Button runButton = new Button(" Run ");
	private Button addLangButton = new Button("+");
	private Button upButton = new Button();
	private Button nextButton = new Button();
	private Button previousButton = new Button();
	private ListBox chooseLanguageBox = new ListBox();
	private TextArea tutorialArea = new TextArea();
	private TextArea consoleArea = new TextArea();
	private FlowPanel toolbarPanel = new FlowPanel();
	private CodeMirror2 editor;
	private CodeMirrorConf conf;
	private ScrollPanel codeMirrorPanel;
	public IDEView(){

		codeMirrorPanel = new ScrollPanel();

		conf= new CodeMirrorConf();

		
		conf.setValue("public class Test{\n"+
				"	public static void main(String[] argsv){\n"+
				"		System.out.println(\"test\");\n"+
				"	}\n"+
				"}");
		conf.setMode(new JSONObject());
		conf.setLineNumbers(true);
		editor = new CodeMirror2(conf);

		codeMirrorPanel.add(editor);

		toolbarPanel.add(runButton);
		toolbarPanel.add(chooseLanguageBox);
		toolbarPanel.add(addLangButton);
		runButton.addAttachHandler(new Handler(){
			public void onAttachOrDetach(AttachEvent event){
				editorPanel.setWidgetTopHeight(toolbarPanel,0,Unit.PX,runButton.getOffsetHeight(),Unit.PX);

				editorPanel.setWidgetTopBottom(codeMirrorPanel,runButton.getOffsetHeight(),Unit.PX,0,Unit.PX);
				System.out.println(runButton.getOffsetHeight());
			}
		});
		//Attach widgets to editorPanel
		editorPanel.add(toolbarPanel);
		editorPanel.add(codeMirrorPanel);

		//	editorPanel.setWidgetVerticalPosition(toolbarPanel,Alignment.BEGIN);

		//	editorPanel.setWidgetVerticalPosition(codeMirrorPanel,Alignment.END);


		//Adding widgets to panel areas
		mainPanel.addWest(tutorialArea,150);
		mainPanel.addNorth(editorPanel,384);
		mainPanel.add(consoleArea);

		// Attach 3 widgets to a DockLayoutPanel
		// Lay them out in 'em' units.
		DockLayoutPanel lp = new DockLayoutPanel(Unit.EM);
		lp.addNorth(new HTML("Try My Language"),2);
		lp.addSouth(new HTML("Footer"),2);
		lp.add(mainPanel);
		initWidget(lp);
		//Add mainPanel to the HTML element with mainPanel as id
		//RootLayoutPanel.get().add(lp);


	}
	public HasClickHandlers getRunButton(){
		return runButton;
	}
	public void setConsoleData(String data){

		consoleArea.setText(data);
	}
	public HasKeyPressHandlers getConsole(){
		return consoleArea;
	}
	public String getSelectedTabValue(){
		return editor.getValue();
	}
	public int getSelectedTabTypeId(){
		return Integer.parseInt(chooseLanguageBox.getValue(chooseLanguageBox.getSelectedIndex()));
	}
	public void setSupportedTypes(Collection<FileTypeDTO> c){
		Iterator<FileTypeDTO> it = c.iterator(); 
		while(it.hasNext()){
			FileTypeDTO type = it.next();
			chooseLanguageBox.addItem(type.getName(),type.getId()); 

		} 
	} 
	public Widget asWidget(){
		return this;
	}
	@Override
	public HasClickHandlers getAddLangButton() {
		return addLangButton;
	}
	@Override
	public ListBox getLangBox() {
		return chooseLanguageBox;
	}
	@Override
	public CodeMirror2 getEditor() {
		return editor;
	}

}
