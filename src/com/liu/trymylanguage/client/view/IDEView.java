package com.liu.trymylanguage.client.view;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import se.liu.gwt.widgets.client.CodeMirror2;
import se.liu.gwt.widgets.client.CodeMirrorConf;
import se.liu.gwt.widgets.client.event.CursorActivityEvent;
import se.liu.gwt.widgets.client.event.CursorActivityHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.server.rpc.UnexpectedException;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.AreaChart;
import com.google.gwt.visualization.client.visualizations.corechart.BarChart;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.ScatterChart;
import com.liu.trymylanguage.client.ErrorDialog;


import com.liu.trymylanguage.client.TMLService;
import com.liu.trymylanguage.client.TMLServiceAsync;
import com.liu.trymylanguage.client.TabWidget;
import com.liu.trymylanguage.client.exception.LangNotFoundException;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.LangParamDTO;

public class IDEView extends Composite {



	@UiField Button runButton;
	@UiField Button newTabButton;
	@UiField TabLayoutPanel tabPanel;
	@UiField SimplePanel tutorialArea;
	@UiField SimplePanel consoleArea;
	@UiField Button renameButton;

	private static IDEViewUiBinder uiBinder = GWT.create(IDEViewUiBinder.class);
	private TMLServiceAsync service;
	private CodeMirrorConf conf;
	private ResizeableTextArea console;
	private LangParamDTO langParam;



	interface IDEViewUiBinder extends UiBinder<Widget, IDEView> {
	}

	public IDEView() {


		service = GWT.create(TMLService.class);

		service.getLangParam(new AsyncCallback<LangParamDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught instanceof LangNotFoundException)
					showAddLangErrorDialog();
				else{
					ErrorDialog dialog = new ErrorDialog(caught.getMessage());

					dialog.center();
					dialog.show();
					
				}
				
			}

			@Override
			public void onSuccess(LangParamDTO result) {

				
					langParam = result;
					conf = getConf(result);
					final CodeMirror2 c = new CodeMirror2(conf);

					ScrollPanel w = new ScrollPanel(c);


					tabPanel.add(w,
							new TabWidget(tabPanel,false,w));
					console = new ResizeableTextArea();
					consoleArea.setWidget(console);
					console.setStyleName("consoleTextArea");
					
					//clear line marking in case of cursor activity
					c.addCursorActivityHandler(new CursorActivityHandler() {
						
						@Override
						public void onCursorActivity(CursorActivityEvent event) {
							
							if(console.getLastSelectedLine()!=null){
								c.markLine(console.getLastSelectedLine());
								console.setLastSelectedLine(null);
							}
						}
					});
					ResizeableFrame f = new ResizeableFrame("doc/index.html");
					
					tutorialArea.setWidget(f);
					f.setStyleName("tutorialArea");
					

			}
		});
		
		exportCopyCode();


		initWidget(uiBinder.createAndBindUi(this));
	}



	//************ UI Handlers ******************//

	@UiHandler("runButton")
	void handleRunClick(ClickEvent e){


		CodeDTO code = new CodeDTO();
		code.setCode(getSelectedEditor().getValue());
		code.setFileName(getSelectedTabTitle());
		service.compile(code, new AsyncCallback<ConsoleDTO>() {

			@Override
			public void onSuccess(final ConsoleDTO result) {
				if(result.isPlot()){
					try {
						Runnable onLoadCallback = new Runnable() {

							@Override
							public void run() {	
								try {
									drawChart(result.getContent());
								} catch (JavaScriptException e) {
									new ErrorDialog(
											"There's been an error with the data format supplied by the plot data convertor"
											+e.getMessage()).show();
								}
							}
						};
						VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);									
					} catch (Exception e) {
						new ErrorDialog(e.getMessage()).show();
					}
				}else{
					console.setText(result.getContent());
					if(result.getLineFeedback()!=null)
						setLineFeedBack(result.getLineFeedback());
				}


			}

			@Override
			public void onFailure(Throwable caught) {
					
						new ErrorDialog(caught.getMessage()).show();
					

			}
		});
	}

	@UiHandler("newTabButton")
	void handleAddTabClick(ClickEvent e){
		ScrollPanel w = new ScrollPanel(new CodeMirror2(conf));
		tabPanel.add(w,
				new TabWidget(tabPanel, true,w));
		tabPanel.selectTab(w);
		if(tabPanel.getWidgetCount()==2){
			((TabWidget)tabPanel.getTabWidget(0)).setCloseable(true);

		}
	}

	@UiHandler("renameButton")
	void handleRenameClick(ClickEvent e){
		
		int selected  = tabPanel.getSelectedIndex();
		TabWidget w= (TabWidget)tabPanel.getTabWidget(selected);
		w.showRenameDialog();
	}

	//*********************************************//











	private CodeMirrorConf getConf(LangParamDTO dto){

		String[] keywords = dto.getKeywords().split("\\s");
		JSONArray array = new JSONArray();
		for (int i=0;i<keywords.length ;i++ ){
			array.set(i,new JSONString(keywords[i]));

		} 
		String[] stringChar = dto.getStringChar().trim().split("\\s");
		JSONArray sarray = new JSONArray();
		for (int i=0;i<stringChar.length ;i++ ){
			sarray.set(i,new JSONString(stringChar[i]));

		} 

		JSONObject mode = new JSONObject();
		mode.put("name",new JSONString("basemode"));
		mode.put("keywords",array);
		mode.put("stringCh",sarray);
		mode.put("commentSingle",new JSONString(dto.getCommentSingle()));
		mode.put("commentMStart",new JSONString(dto.getCommentMStart()));
		mode.put("commentMEnd",new JSONString(dto.getCommentMEnd()));
		mode.put("escapeCh",new JSONString(dto.getEscapeChar()));
		dto.setOperators(dto.getOperators().replace("-", "\\-"));
		dto.setOperators(dto.getOperators().replace("^", "\\^"));
		mode.put("isOperatorChar",new JSONString("["+dto.getOperators().
				replace(" ", "")+"]"));
		
		CodeMirrorConf conf= new CodeMirrorConf();



		conf.setMode(mode);
		conf.setLineNumbers(true);


		return conf;


	}

	private void showAddLangErrorDialog(){


		ErrorDialog errorDialog = new ErrorDialog("Welcome to TryMyLanguage");
		errorDialog.setHTML("");
		errorDialog.setCloseButtonText("Click here to configure a language");
		errorDialog.addCloseButtonClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				History.newItem("configure");

			}
		});		
		errorDialog.show();
	}


	public void copyCode(String code){
		code = code.replace("<br>", "\n");
		code = code.replace("<br />", "\n");
		code = code.replace("&nbsp;", " ");
		ScrollPanel p = (ScrollPanel)tabPanel.getWidget(tabPanel.getSelectedIndex());
		CodeMirror2 m = (CodeMirror2)p.getWidget();

		m.setValue(code);

	}

	private native void exportCopyCode()/*-{
		var that = this;
		$wnd.copyCode = 
			$entry(function(code){
				that.@com.liu.trymylanguage.client.view.IDEView::copyCode(Ljava/lang/String;)(code)
			});

	}-*/;

	private void setLineFeedBack(final Map<Integer, Integer> map) {
		final CodeMirror2 c = getSelectedEditor();

		console.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Integer ln = (Integer)map.get(console.getSelectedLine());
				
				if (console.getLastSelectedLine()!=null){
					
					c.markLine(console.getLastSelectedLine());
					console.setLastSelectedLine(null);	
				}
				
				
				if(ln!=null){
					c.setCursor(ln-1, 0);
					c.markLine(ln-1);
					c.setFocus(true);
					console.setLastSelectedLine(ln-1);
					
					
				}
			}
		});

	}
	private CodeMirror2 getSelectedEditor(){

		int selected  = tabPanel.getSelectedIndex();
		ScrollPanel sp = (ScrollPanel)tabPanel.getWidget(selected);
		CodeMirror2 cm = (CodeMirror2)sp.getWidget();
		return cm;
	}
	private String getSelectedTabTitle(){

		int selected  = tabPanel.getSelectedIndex();
		TabWidget w= (TabWidget)tabPanel.getTabWidget(selected);
		return w.toString();
	}



	class ResizeableTextArea extends TextArea implements RequiresResize{

		private Integer lastSelectedLine;
		public ResizeableTextArea(){
			super();
			setReadOnly(true);

		}
		@Override
		public void setText(String text) {
			// TODO Auto-generated method stub
			super.setText(text);

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
	class ResizeableFrame extends Frame implements RequiresResize{
		public  ResizeableFrame(String url) {
			super(url);
			
			
			
		}
		@Override
		public void onResize() {
			int width = ResizeableFrame.this.getParent().getOffsetWidth();
			int height = ResizeableFrame.this.getParent().getOffsetHeight();

			ResizeableFrame.this.setWidth(width+"px");
			ResizeableFrame.this.setHeight(height+"px");
		}
		
		
		
	}
	//Create the chart based on the data
	private void drawChart(String data) throws JavaScriptException {
		JSONObject chart = null;
		JavaScriptObject dataObj = null;
		Options options = null;
		String type = null;
		
		chart = new JSONObject(parseJSON(data));


			Set<String> keys = chart.keySet();
			options = Options.create();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				if(!string.equals("data") && !string.equals("chartType")){
					JSONValue v = chart.get(string);
					if(v.isString()!=null)
						options.set(string, (v.isString()).stringValue());
					else if(v.isObject()!=null)
						options.set(string, (v.isObject()).getJavaScriptObject());
					else if(v.isNumber()!=null)
						options.set(string, (v.isNumber()).doubleValue());
					else if(v.isBoolean()!=null)
						options.set(string, (v.isBoolean()).booleanValue());
					else if(v.isArray()!=null)
						options.set(string, (v.isArray()).getJavaScriptObject());
				}

			}
			dataObj = ((JSONObject)chart.get("data"))
					.getJavaScriptObject();

			type = ((JSONString)chart.get("chartType")).stringValue();
		
			//throw new Exception("There has been an error in your data format: \n"
			//		+ e.getMessage());
		
		final DataTable dataTable = DataTable.create(dataObj);

		switch(CoreChart.Type.valueOf(type)) {
		case LINE:

			consoleArea.setWidget(new LineChart(dataTable, options));

			break;
		case AREA:
			consoleArea.setWidget(new AreaChart(dataTable, options));
			break;
		case BARS:
			consoleArea.setWidget(new BarChart(dataTable, options));
			break;
		case COLUMNS:
			consoleArea.setWidget(new ColumnChart(dataTable, options));
			break;
		case PIE:
			consoleArea.setWidget(new PieChart(dataTable, options));
			break;
		case SCATTER:
			consoleArea.setWidget(new ScatterChart(dataTable, options));
			break;

		default:
			break;
		}


	}
	private native 	JavaScriptObject parseJSON(String jsonString) throws JavaScriptException/*-{

		return eval('('+jsonString+')');

	}-*/;
}
