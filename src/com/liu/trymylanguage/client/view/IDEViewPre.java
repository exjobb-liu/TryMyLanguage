package com.liu.trymylanguage.client.view;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.gargoylesoftware.htmlunit.javascript.host.Event;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;

import com.google.gwt.event.logical.shared.AttachEvent;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;

import com.liu.trymylanguage.client.ErrorDialog;
import com.liu.trymylanguage.client.TabWidget;

import com.liu.trymylanguage.client.event.AddLangEvent;
import com.liu.trymylanguage.client.presenter.IDEPresenter;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.TabLayoutPanel;
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
import com.google.gwt.layout.client.Layout;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * Describe class IDEView here.
 *
 *
 * Created: Fri Dec  2 15:19:53 2011
 *
 * @author <a href="mailto:amir@amir-laptop">Amir Hossein Fouladi</a>
 * @version 1.0
 */
public class IDEViewPre extends Composite implements IDEPresenter.Display{
	private SplitLayoutPanel mainPanel = new SplitLayoutPanel();

	
	private Button runButton = new Button(" Run ");
	
	private Button upButton = new Button();
	private Button nextButton = new Button();
	private Button previousButton = new Button();
	private Button addTab = new Button("New Tab");
	private ListBox chooseLanguageBox = new ListBox();
	private TextArea tutorialArea = new TextArea();
	private ResizeableTextArea consoleArea = new ResizeableTextArea();
	private HorizontalPanel toolbarPanel = new HorizontalPanel();
	private CodeMirror2 editor;
	private CodeMirrorConf conf;
	private ScrollPanel codeMirrorPanel;
	private ErrorDialog errorDialog = new ErrorDialog();
	private TabLayoutPanel editorTabPanel = new TabLayoutPanel(2.5, Unit.EM);
	private TabWidget tabWidget; 
	private Map<Integer,Integer> feedback;
	
	public IDEViewPre(){
		
		consoleArea.setReadOnly(true);
		
		
		
		codeMirrorPanel = new ScrollPanel();

		
		editor = new CodeMirror2(conf);

		codeMirrorPanel.add(editor);

		
		
		
		//Attach widgets to editorPanel
		toolbarPanel.add(runButton);
		
		addTab.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		toolbarPanel.add(addTab);
		
		
		//tabWidget = new TabWidget(editorTabPanel,false);	
		editorTabPanel.add(codeMirrorPanel,tabWidget);
		//	editorPanel.setWidgetVerticalPosition(toolbarPanel,Alignment.BEGIN);

		//	editorPanel.setWidgetVerticalPosition(codeMirrorPanel,Alignment.END);


		//Adding widgets to panel areas
		mainPanel.addWest(tutorialArea,150);
		mainPanel.addNorth(editorTabPanel,384);
		mainPanel.add(consoleArea);
		
		
		// Attach 3 widgets to a DockLayoutPanel
		// Lay them out in 'em' units.
		DockLayoutPanel lp = new DockLayoutPanel(Unit.EM);
		lp.addNorth(toolbarPanel,3);
		lp.addSouth(new HTML("Footer"),2);
		lp.add(mainPanel);
		initWidget(lp);
		
		//Add mainPanel to the HTML element with mainPanel as id
		//RootLayoutPanel.get().add(lp);
		

	}
	
	
	public void addRunClickHandler(ClickHandler handler){
		runButton.addClickHandler(handler);
	}
	public void setConsoleData(String data){

		consoleArea.setText(data);
	}
	public HasKeyPressHandlers getConsole(){
		return consoleArea;
	}
	public String getSelectedTabValue(){//Attach widgets to editorPanel
		ScrollPanel panel = (ScrollPanel) editorTabPanel
				.getWidget(editorTabPanel.getSelectedIndex());
		
		return ((CodeMirror2)panel.getWidget()).getValue();
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
	public ListBox getLangBox() {
		return chooseLanguageBox;
	}
	@Override
	public CodeMirror2 getEditor() {
		return editor;
	}
	@Override
	public void showError(String error, String closeText, ClickHandler closeHandler) {
		errorDialog.setMessage(error);
		errorDialog.center();
		errorDialog.setCloseButtonText(closeText);
		errorDialog.addCloseButtonClickHandler(closeHandler);		
		errorDialog.show();
	}
	public void showError(String error){
		errorDialog.setMessage(error);
		errorDialog.center();
	}
	@Override
	public String getSelectedFileName() {
		
		return editorTabPanel
				.getTabWidget(editorTabPanel.getSelectedIndex()).toString();
	}
	
	
	public class ResizeableTextArea extends TextArea implements RequiresResize{
		
		private Integer lastSelectedLine;
		public ResizeableTextArea(){
			super();
			
		}
		@Override
		public void setText(String text) {
			// TODO Auto-generated method stub
			super.setText(text);
			onResize();
		}
		
		@Override
		public void onResize() {
			int width = ResizeableTextArea.this.getParent().getOffsetWidth();
			int height = ResizeableTextArea.this.getParent().getOffsetHeight();
			
			ResizeableTextArea.this.setPixelSize(width, height);
			
		}
		public int getSelectedLine(){
			int pos = this.getCursorPos();
			String content = this.getValue();
			int ln = 1;
			for(int i = 0 ; i<pos; i++){
				if(content.charAt(i)=='\n')
					ln++;
			}
			return ln;
			
		}
		public Integer getLastSelectedLine(){
			
			return lastSelectedLine;
		}
		
		public void setLastSelectedLine(Integer ln){
			lastSelectedLine = ln;
		}
		
	}


	@Override
	public void setLineFeedBack(final Map<Integer, Integer> map) {
		feedback = map;
		
		consoleArea.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (consoleArea.getLastSelectedLine()!=null){
					editor.clearMarker(consoleArea.getLastSelectedLine());
				}
				Integer ln = (Integer)map.get(consoleArea.getSelectedLine());
				System.out.println(ln);
				if(ln!=null){
					editor.setMarker(ln-1);
					consoleArea.setLastSelectedLine(ln-1);
					editor.setCursor(ln-1, 0);
					editor.focus();
				}
			}
		});
	
	}
	
	
	
	
}
