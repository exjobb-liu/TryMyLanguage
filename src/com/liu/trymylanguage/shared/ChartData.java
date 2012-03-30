package com.liu.trymylanguage.shared;

import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

public class ChartData {
	private Options options;
	private AbstractDataTable dataTable;
	
	
	public ChartData(AbstractDataTable dataTable, Options options){
		this.options = options;
		this.dataTable = dataTable;
	}
	public Options getOptions() {
		return options;
	}
	public void setOptions(Options options) {
		this.options = options;
	}
	public AbstractDataTable getDataTable() {
		return dataTable;
	}
	public void setDataTable(AbstractDataTable dataTable) {
		this.dataTable = dataTable;
	}
	
	
}
