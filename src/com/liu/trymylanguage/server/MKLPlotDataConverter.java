package com.liu.trymylanguage.server;

import java.io.BufferedReader;
import java.io.Serializable;

public class MKLPlotDataConverter implements PlotDataConverter,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String convert(BufferedReader in) throws Exception {
		StringBuilder chartData = new StringBuilder();
		
		chartData.append("{\n");
		//String[] lines = in.split("\n");
		chartData.append("\tchartType:'LINE',\n");
		chartData.append("\tlineWidth:'2',\n");
		String first;
		if((first = in.readLine()) != null){
			String[] col = first.trim().split("\\s");
			
			chartData.append("\thAxis:{title:'");
			chartData.append(col[0]);
			chartData.append("'},\n");
			chartData.append("\tdata:{\n\t\tcols:[\n");
			for (int i = 0; i < col.length; i++) {
				chartData.append("\t\t\t{id: '");
				chartData.append(col[i]);
				chartData.append("',label: '");
				chartData.append(col[i]);
				chartData.append("', type: 'number'}");
				if(i!=col.length-1)
					chartData.append(",\n");
				
			}
			chartData.append("\n\t\t],\n");
		}
		chartData.append("\t\trows:[");
		String line;
		boolean hasRows = false;
		while ((line = in.readLine()) != null) {
			hasRows = true;
			chartData.append("\n\t\t\t{c:[");
			String[] col = line.trim().split("\\s");
			for (int j = 0; j < col.length; j++) {
				chartData.append("{v:");
				chartData.append(Double.parseDouble(col[j]));
				chartData.append("}");
				if(j!=col.length-1)
					chartData.append(",");
			}
			chartData.append("]},");
		}
		
		if(hasRows){
			chartData.deleteCharAt(chartData.length()-1);
		}
		chartData.append("\n\t\t]\n\t}\n}");
		
		
		
		return chartData.toString();
	}

}
