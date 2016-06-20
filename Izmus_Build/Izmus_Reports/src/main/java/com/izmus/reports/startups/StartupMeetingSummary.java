package com.izmus.reports.startups;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.startups.Startup;
import com.izmus.data.domain.startups.StartupMeeting;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Component("StartupMeetingSummary")
public class StartupMeetingSummary {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(StartupMeetingSummary.class);
	@Autowired
	private ServletContext context;
	/*----------------------------------------------------------------------------------------------------*/
	public JasperPrint createMeetingSummaryReport(StartupMeeting meeting, Startup startup){
		try {
			InputStream inputStream = context.getResourceAsStream("/WEB-INF/reports/startup-meeting/StartupMeetingLTR.jasper");
			JasperReport report = (JasperReport) JRLoader.loadObject(inputStream);
			Map<String, Object> parameters = new HashMap<>();
			buildParameters(parameters, meeting, startup);
			JasperPrint print = JasperFillManager.fillReport(report, parameters);
			LOGGER.info("Report Created Successfully For Meeting: " + meeting);
			return print;
		} catch (Exception e) {
			LOGGER.error("Could Not Create Report For Meeting: " + meeting + " With Error: " +  e.getMessage());
			return null;
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void buildParameters(Map<String, Object> parameters, StartupMeeting meeting, Startup startup) {
		
	}
}
