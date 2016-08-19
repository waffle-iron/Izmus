package com.izmus.reports.startups;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.general.DefaultValueDataset;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

public class StartupReportMeterCustomizer implements JRChartCustomizer{
	@Override
	public void customize (JFreeChart chart, JRChart jasperChart){
		MeterPlot plot = (MeterPlot) chart.getPlot();
		//Disable the tick labels
		plot.setTickLabelsVisible(false);
		//Parse value from title
		try {
			String title = chart.getTitle().getText();
			Double value = Double.parseDouble(title);
			if (value == 0){
				value = 1d;
			}
			chart.setTitle("");
			DefaultValueDataset valueDataset = new DefaultValueDataset(value);
			plot.setDataset(valueDataset);
		} catch (Exception e) {
			chart.setTitle("");
			DefaultValueDataset valueDataset = new DefaultValueDataset(1);
			plot.setDataset(valueDataset);
		}
		//Make value paint white so it will be invisible
		plot.setValuePaint(Color.WHITE);
		plot.setValueFont(new Font("Serif", Font.PLAIN, 0));
	}
}
