/*
 * This interface is implemented by people how are interested in using plotting
 * feature of TML. 
 *  The Implementation of this interface is used to convert 
 *  the format of the plotting data to the format described below:
 *  The output of the convert method should be a 
 *  <a href="http://json.org/">JSON literal string</a> which is basically a simple 
 *  string starting with { and ends with } with a set of key/value pairs in between.
 *  Since <a href="http://code.google.com/apis/chart/">Google charting tools</a> is used
 *  for plotting, all the key/value pairs documented in their website are valid
 *  for customizing charts. However there are two additional keys that should be 
 *  defined alongside their values. Those keys are 'data' and 'chartType'.
 *  The value for 'data' is another JSON object conforming to the format defined for
 *  google charting data. The data format can be different for different charts
 *  The value for 'chartType' can be any of AREA,LINE,SCATTER,BARS,COLUMNS,PIE 
 *  {@value com.google.gwt.visualization.client.visualizations.corechart#TYPE}
 *  To make it easier, an example for the data input and the expected output is 
 *  given below: 
 *  Consider that we want to draw a linechart with the following data:
 *  
 *  time	ground			y
 *	0.		-2.99999999998	-1.41477653833
 *	0.01	-3.				-1.41502154559
 *	0.02	-3.				-1.41575608363
 *	0.03	-3.				-1.41697905793
 *  
 *  One possible out put for the above data would be:
 *  
 *  {
 *		chartType:'LINE',
 *		hAxis:{title:'time'},
 *		data:{
 *				cols:[
 *					{id: 'time',label: 'time'},
 *					{id: 'ground',label: 'ground'},
 *					{id: 'y',label: 'y'}
 *			  	],
 *			  	rows: [
 *					{c:[
 *					{v:0.0},
 *					{v:-2.99999999998},
 *					{v:-1.41477653833}]},
 *					{c:[
 *					{v:0.01},
 *					{v:-3.0},
 *					{v:-1.41502154559}]},
 *					{c:[
 *					{v:0.02},
 *					{v:-3.0},
 *					{v:-1.41575608363}]},
 *					{c:[
 *					{v:0.03},
 *					{v:-3.0},
 *					{v:-1.41697905793}]}
 *			  	]
 *			}
 *	}
 *
 * In order for the application to pick up and use your implementation
 * you should make a jar package which complies with Java provider registration
 * requirements. Here, the content of an example package is given assuming that the Class 
 * implementing the interface is called "TestPlot"
 * 
 * TestPlot.jar
 * ************************************************************
 * 	+ META-INF
 * 		+ services
 * 			- com.liu.trymylanguage.server.PlotDataConvertor
 * 	+ com
 * 		+ liu
 * 			+ trymylanguage
 * 				+ server
 * 					- TestPlot.class
 * *************************************************************
 * 
 * The file in services directory should be text file containing a single line
 * listing the fully concrete class name of the implementation which in this case
 * is com.liu.trymylanguage.server.TestPlot
 *
 * The only thing left to do is to package the above directories in a jar file and
 * place the jar file in application's classpath which in this case is the WEB-INF 
 * directory where you deployed the application.
 * Bear in mind that you must restart the servlet container after dropping the 
 * jar file in the classpath.
 * 
 *    
 */
package com.liu.trymylanguage.server;

import java.io.BufferedReader;




public interface PlotDataConverter {
	String convert(BufferedReader s) throws Exception;
}
