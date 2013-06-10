package com.liu.trymylanguage.shared;

import java.io.Serializable;

public class Plot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String chartData;

	public String getChartData() {
		return chartData;
	}

	public void setChartData(String chartData) {
		this.chartData = chartData;
	}
	
}
