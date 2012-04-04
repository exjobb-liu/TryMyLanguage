package com.liu.trymylanguage.server;

import java.io.Serializable;

public class MKLPlotDataConvertor implements PlotDataConvertor,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String convert(String s) {
			
		String chartData = new String("{\n");
		String[] lines = s.split("\n");
		chartData+="chartType:'SCATTER',\n";
		
		if(lines[0]!=null){
			String[] col = lines[0].trim().split("\\s");
			
			chartData += "hAxis:{title:'"+col[0]+"'},\n";
			chartData += "data:{cols:[\n";
			for (int i = 0; i < col.length; i++) {
				chartData+="{id: '"+col[i]+"',label: '"+col[i]+"'}";
				if(i!=col.length-1)
					chartData+=",\n";
				
			}
			chartData+="],\n";
		}
		chartData+="rows: [\n";
		for (int i = 1; i < lines.length; i++) {
			chartData+="{c:[\n";
			String[] col = lines[i].trim().split("\\s");
			for (int j = 0; j < col.length; j++) {
				chartData+="{v:" + Double.parseDouble(col[j])+"}";
				if(j!=col.length-1)
					chartData+=",\n";
			}
			chartData+="]}";
			if(i!=lines.length-1)
				chartData+=",\n";
		}
		chartData+="]}\n";
		
		
		chartData+="}";
		return chartData;
	}

}
