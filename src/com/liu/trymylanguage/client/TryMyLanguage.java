package com.liu.trymylanguage.client;

import com.liu.trymylanguage.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import static com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TryMyLanguage implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";
  
  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
    /**
     *Define UI widgets
     */
    private SplitLayoutPanel mainPanel = new SplitLayoutPanel();
    // private HorizontalSplitPanel mainPanel = new HorizontalSplitPanel();
    private VerticalPanel editorPanel = new VerticalPanel();
    private Button runButton = new Button(" Run ");
    private Button upButton = new Button();
    private Button nextButton = new Button();
    private Button previousButton = new Button();
    private ListBox chooseLanguageBox = new ListBox();
    private TextArea tutorialArea = new TextArea();
    private TextArea codeArea = new TextArea();
    private TextArea consoleArea = new TextArea();
    private FlowPanel toolbarPanel = new FlowPanel();



  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
      //
      toolbarPanel.add(runButton);
      toolbarPanel.add(chooseLanguageBox);

      //Attach widgets to editorPanel
      editorPanel.add(toolbarPanel);
      editorPanel.add(new HTML("This is a test"));
      codeArea.setCharacterWidth(101);
      codeArea.setVisibleLines(22);


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

      //Add mainPanel to the HTML element with mainPanel as id
      RootLayoutPanel.get().add(lp);
      //RootPanel.get("mainPanel").add(mainPanel);

      // Set the cursor focus on code editor
      //consoleArea.setFocus(true);
      // this.loadCodeMirror();
  }
    public static native void loadCodeMirror() /*-{
       var codemirror = CodeMirror(document.getElementById("codemirror"));;
	  }-*/;
}
