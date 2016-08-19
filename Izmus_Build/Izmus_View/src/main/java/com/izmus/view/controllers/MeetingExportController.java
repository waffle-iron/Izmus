package com.izmus.view.controllers;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.izmus.data.domain.meetings.MeetingReport;
import com.izmus.data.repository.IMeetingReportRepository;

import net.sf.jasperreports.engine.JasperExportManager;

@Controller
public class MeetingExportController {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(MeetingExportController.class);
	@Autowired
	private IMeetingReportRepository meetingReportRepository;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Export/MeetingReport/{reportId}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Export', '')")
	public void getLoginPage(@PathVariable("reportId") Integer reportId, HttpServletResponse response) {
		try {
			MeetingReport meetingReport = meetingReportRepository.findOne(reportId);
			// set headers for the response
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"",
	        		meetingReport.getReportName() + ".pdf");
	        response.setHeader(headerKey, headerValue);
	        response.setContentType("application/pdf");
	        response.getOutputStream().write(JasperExportManager.exportReportToPdf(meetingReport.getReport()));
			response.flushBuffer();
//	        JasperExportManager.exportReportToHtmlFile(scoreCardReport.getReport(), "/home/lior/test.html");
//	        response.setContentType("text/html");
//			FileInputStream file = new FileInputStream(new File("/home/lior/test.html"));
//			IOUtils.copy(file, response.getOutputStream());
		} catch (Exception e) {
			LOGGER.error("Could Not Export Meeting Report: " + reportId + " With Error: " + e.getMessage());
		}
	}
}
