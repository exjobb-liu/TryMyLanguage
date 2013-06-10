package com.liu.trymylanguage.client.view;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.zschech.gwt.comet.client.CometClient;
import net.zschech.gwt.comet.client.CometListener;
import net.zschech.gwt.comet.client.CometSerializer;
import net.zschech.gwt.comet.client.SerialTypes;

import se.liu.gwt.widgets.client.CodeMirror2;
import se.liu.gwt.widgets.client.CodeMirrorConf;
import se.liu.gwt.widgets.client.Cursor;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
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
import com.liu.trymylanguage.client.exception.TMLException;
import com.liu.trymylanguage.shared.CodeDTO;
import com.liu.trymylanguage.shared.ConsoleDTO;
import com.liu.trymylanguage.shared.LangParamDTO;
import com.liu.trymylanguage.shared.OutputDTO;
import com.liu.trymylanguage.shared.Plot;



public class IDEView extends Composite {



	@UiField Button runButton;
	@UiField Button newTabButton;
	@UiField TabLayoutPanel tabPanel;
	@UiField SimplePanel tutorialArea;
	@UiField ScrollPanel consoleArea;
	@UiField Button renameButton;
	@UiField CheckBox plotCheckBox;
	


	private static IDEViewUiBinder uiBinder = GWT.create(IDEViewUiBinder.class);
	private TMLServiceAsync service;
	private CodeMirrorConf conf;
	private FlowPanel console;
	private LangParamDTO langParam;
	private CometClient cometClient;
	private boolean canRun = true;


	//private HTML consoleHtml;
	private boolean cometStarted = false;

	@SerialTypes({ Boolean.class, String.class, Plot.class, TMLException.class })
	public static abstract class InstantMessagingCometSerializer extends CometSerializer {}


	interface IDEViewUiBinder extends UiBinder<Widget, IDEView> {
	}

	public IDEView() {
		console = new FlowPanel();

		service = GWT.create(TMLService.class);

		service.getLangParam(new AsyncCallback<LangParamDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught instanceof LangNotFoundException)
					showAddLangErrorDialog();
				else{
					ErrorDialog dialog = new ErrorDialog(caught);

					dialog.center();
					dialog.show();

				}

			}

			@Override
			public void onSuccess(LangParamDTO result) {


				langParam = result;
				conf = getConf(result);
				CodeMirror2 c = new CodeMirror2(conf);



				tabPanel.add(c,
						new TabWidget(tabPanel,false,c));
				
				c.onResize();
				//	console = new ResizeableTextArea();
				//console = new FlowPanel();
				//consoleArea.setWidget(console);
				//console.setStyleName("consoleTextArea");



				ResizeableFrame f = new ResizeableFrame("doc/index.html");

				tutorialArea.setWidget(f);
				f.setStyleName("tutorialArea");


			}
		});

		exportCopyCode();
		exportSelectLine();


		initWidget(uiBinder.createAndBindUi(this));


		
		Window.addWindowClosingHandler(new Window.ClosingHandler() {
			
			@Override
			public void onWindowClosing(ClosingEvent event) {
				doStop();
				
			}
		});
		
		

		consoleArea.setWidget(console);
	}

	
	@Override
	protected void onUnload() {
		
		doStop();
		
		super.onUnload();
	}


	//************ UI Handlers ******************//
	
	

	@UiHandler("runButton")
	void handleRunClick(ClickEvent e){

		if (canRun) {
			runButton.setEnabled(false);
			console.clear();
			canRun = false;
			final CodeDTO code = new CodeDTO(getSelectedTabTitle(),
					langParam.getSuffix(),
					langParam.getCompileCmd(), 
					langParam.getRunCmd(),
					langParam.getFeedbackRegex(),
					plotCheckBox.getValue(),
					langParam.getTimeout());
			code.setCode(getSelectedEditor().getValue());



			service.compile(code, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					canRun = true;
					runButton.setEnabled(true);
					new ErrorDialog(caught).show();

				}

				@Override
				public void onSuccess(Void result) {

					executed();

				}
			});
		}
	}



	private void executed(){



		//doStop();
		if (cometClient == null)  {
			CometSerializer serializer = GWT.create(InstantMessagingCometSerializer.class);
			String url = GWT.getModuleBaseURL() + "comet";
			cometClient = new CometClient(url, serializer, new CometListener() {

				@Override
				public void onRefresh() {
					
					System.out.println("Refresh");

				}

				@Override
				public void onMessage(List<? extends Serializable> messages) {

					for (final Serializable serializable : messages) {

						if (serializable instanceof String) {
							String message = (String)serializable;

							console.add(new InlineHTML(message));

							consoleArea.setWidget(console);
							consoleArea.scrollToBottom();


						} else if (serializable instanceof Plot) {
							final String message = ((Plot)serializable).getChartData();
							try {
								Runnable onLoadCallback = new Runnable() {

									@Override
									public void run() {	
										try {
											drawChart(message);
										} catch (JavaScriptException e) {
											new ErrorDialog(e).show();
										}
									}
								};
								VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);									
							} catch (Exception e) {
								new ErrorDialog(e).show();
							}

						} else if (serializable instanceof Boolean) {
							canRun = true;
							runButton.setEnabled(true);

						} else if (serializable instanceof TMLException) {
							canRun = true;
							runButton.setEnabled(true);
							new ErrorDialog((TMLException)serializable).show();
						}
					}

				}

				@Override
				public void onHeartbeat() {
					//System.out.println("heartbeat");

				}

				@Override
				public void onError(Throwable exception, boolean connected) {
					new ErrorDialog(exception).show();

					exception.printStackTrace();
					
					


				}

				@Override
				public void onDisconnected() {
					System.out.println("disconnected");
					//doStop();


				}

				@Override
				public void onConnected(int heartbeat) {
					System.out.println("connected: " + heartbeat);
					canRun = true;
					runButton.setEnabled(true);
				}
			});


		}

		doStart();
		
	}

	private void doStop() {
		if (cometClient != null) {

			cometClient.stop();
			service.InvalidateCometSession(new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					//new ErrorDialog(caught).show();
					
				}

				@Override
				public void onSuccess(Void result) {
					
					
				}
			});
		//	cometStarted = false;


		}

	}
	
	private void doStart() {
		if (cometClient != null && !cometClient.isRunning()) {

			cometClient.start();
			//cometStarted = true;

		}

	}
	
	private void doRestart() {
		doStop();
		doStart();
	}

	@UiHandler("newTabButton")
	void handleAddTabClick(ClickEvent e){
		CodeMirror2 codeMirror = new CodeMirror2(conf);


		tabPanel.add(codeMirror,
				new TabWidget(tabPanel, true,codeMirror));
		tabPanel.selectTab(codeMirror);
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


		final DialogBox confDialog = 
				new DialogBox();
		VerticalPanel content = new VerticalPanel();

		HTML message = new HTML("Welcome to TryMyLanguage");
		Button close = new Button("Click here to configure a language");
		content.add(message);	

		content.add(close);
		close.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				confDialog.hide();
			}
		});
		confDialog.setGlassEnabled(true);





		close.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				History.newItem("configure");

			}
		});
		confDialog.setWidget(content);
		confDialog.center();
		confDialog.show();
	}


	public void copyCode(String code){
		code = code.replace("<br>", "\n");
		code = code.replace("<br />", "\n");
		code = code.replace("&nbsp;", " ");
		//ScrollPanel p = (ScrollPanel)tabPanel.getWidget(tabPanel.getSelectedIndex());
		CodeMirror2 m = (CodeMirror2)tabPanel.getWidget(tabPanel.getSelectedIndex());

		m.setValue(code);

	}

	private native void exportCopyCode()/*-{
		var that = this;
		$wnd.copyCode = 
			$entry(function(code){
				that.@com.liu.trymylanguage.client.view.IDEView::copyCode(Ljava/lang/String;)(code)
			});

	}-*/;

	private native void exportSelectLine()/*-{
		var that  = this;
		$wnd.selectLine = 
			$entry(function(event){
				that.@com.liu.trymylanguage.client.view.IDEView::selectLine(Ljava/lang/String;)(event.target.id)
			});
	}-*/;

	/*private void setLineFeedBack(final Map<RegexPos, Integer> map) {

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

	}*/

	private void setConsoleContent(ConsoleDTO dto,final int tabIndex){
		/*Map<RegexPos,Integer> feedBack = dto.getLineFeedback();
		String content = dto.getContent();
		if(feedBack==null || feedBack.size()==0)
			console.add(new InlineLabel(TmlUtil.htmlEscape(content)));
		else{
			int start=-1;
			for (RegexPos pos : feedBack.keySet()) {
				if(start==-1)
					console.add(new InlineLabel(
							TmlUtil.htmlEscape(content
									.substring(0, pos.getStart()))));
				else


			}


		}

		 */
		console.clear();
		List<OutputDTO> out = dto.getConsoleContent();

		for (Iterator<OutputDTO> iterator = out.iterator(); iterator.hasNext();) {
			OutputDTO outputDTO = (OutputDTO) iterator.next();
			final int num = outputDTO.getLineNum();
			String content = outputDTO.getContent();
			if(num==0)
				console.add(new InlineHTML(content));
			else{
				InlineHTML lbl = new InlineHTML(content);
				lbl.setStyleName("lineFeedback");
				lbl.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						tabPanel.selectTab(tabIndex);
						tabPanel.forceLayout();
						CodeMirror2 editor =  (CodeMirror2)tabPanel.getWidget(tabIndex);



						editor.setSelection(new Cursor(num-1,0),
								new Cursor(num-1,editor.getLine(num-1).length()));


						editor.setFocus(true);

					}
				});
				console.add(lbl);
			}
		}


	}

	public void selectLine(String lineNumber){
		int num = Integer.parseInt(lineNumber);
		int tabIndex = tabPanel.getSelectedIndex();
		tabPanel.selectTab(tabIndex);
		tabPanel.forceLayout();
		CodeMirror2 editor =  (CodeMirror2)tabPanel.getWidget(tabIndex);



		editor.setSelection(new Cursor(num-1,0),
				new Cursor(num-1,editor.getLine(num-1).length()));


		editor.setFocus(true);

	}
	private CodeMirror2 getSelectedEditor(){

		int selected  = tabPanel.getSelectedIndex();
		CodeMirror2 cm = (CodeMirror2)tabPanel.getWidget(selected);

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
			if(string.equals("data")) {
				dataObj = ((JSONObject)chart.get("data"))
						.getJavaScriptObject();
			} else if (string.equals("chartType")) {
				type = ((JSONString)chart.get("chartType")).stringValue();
			} else {
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
