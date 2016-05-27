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

import com.izmus.data.domain.startups.ScoreCardReport;
import com.izmus.data.repository.IStartupScoreCardReportRepository;

import net.sf.jasperreports.engine.JasperExportManager;

@Controller
public class ExportController {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportController.class);
	@Autowired
	private IStartupScoreCardReportRepository scoreCardReportRepository;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Export/StartupScoreCardReport/{reportId}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Export', '')")
	public void getLoginPage(@PathVariable("reportId") Integer reportId, HttpServletResponse response) {
		try {
			ScoreCardReport scoreCardReport = scoreCardReportRepository.findOne(reportId);
			// set headers for the response
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"",
	                scoreCardReport.getReportName() + ".pdf");
	        response.setHeader(headerKey, headerValue);
	        response.setContentType("application/pdf");
	        response.getOutputStream().write(JasperExportManager.exportReportToPdf(scoreCardReport.getReport()));
			response.flushBuffer();
//	        JasperExportManager.exportReportToHtmlFile(scoreCardReport.getReport(), "/home/lior/test.html");
//	        response.setContentType("text/html");
//			FileInputStream file = new FileInputStream(new File("/home/lior/test.html"));
//			IOUtils.copy(file, response.getOutputStream());
		} catch (Exception e) {
			LOGGER.error("Could Not Export Report: " + reportId + " With Error: " + e.getMessage());
		}
	}
}
