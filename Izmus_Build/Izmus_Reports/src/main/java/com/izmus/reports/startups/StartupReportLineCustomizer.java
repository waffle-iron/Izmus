package com.izmus.reports.startups;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.title.LegendTitle;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

public class StartupReportLineCustomizer implements JRChartCustomizer{
	@Override
	public void customize (JFreeChart chart, JRChart jasperChart){
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		CategoryAxis yAxis = plot.getDomainAxis();
		yAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 7));
		NumberAxis xAxis = (NumberAxis) plot.getRangeAxis();
		xAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 7));
		xAxis.setTickUnit(new NumberTickUnit(4000000));
		LegendTitle legend = chart.getLegend();
		legend.setFrame(BlockBorder.NONE);
		legend.setItemFont(new Font("SansSerif", Font.PLAIN, 7));
	}
}
