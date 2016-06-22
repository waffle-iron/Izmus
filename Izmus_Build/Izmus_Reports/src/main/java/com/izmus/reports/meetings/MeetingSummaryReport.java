package com.izmus.reports.meetings;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.meetings.GeneralMeeting;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Component("MeetingSummaryReport")
public class MeetingSummaryReport {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(MeetingSummaryReport.class);
	@Autowired
	private ServletContext context;
	/*----------------------------------------------------------------------------------------------------*/
	public JasperPrint createMeetingReport(GeneralMeeting meeting){
		try {
			InputStream inputStream = context.getResourceAsStream("/WEB-INF/reports/meeting/MeetingLTR.jasper");
			JasperReport report = (JasperReport) JRLoader.loadObject(inputStream);
			Map<String, Object> parameters = new HashMap<>();
			buildParameters(parameters, meeting);
			JasperPrint print = JasperFillManager.fillReport(report, parameters);
			LOGGER.info("Report Created Successfully For Meeting: " + meeting);
			return print;
		} catch (Exception e) {
			LOGGER.error("Could Not Create Report For Meeting: " + meeting + " With Error: " +  e.getMessage());
			return null;
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void buildParameters(Map<String, Object> parameters, GeneralMeeting meeting) {
		parameters.put("meeting", meeting);
		parameters.put("logo", context.getRealPath("/") + "/META-INF/views-public/core/logo/logo708X181.png");
	}
}
