package com.izmus.view.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.izmus.data.domain.startups.ScoreCardReport;
import com.izmus.data.domain.startups.Startup;
import com.izmus.data.domain.startups.StartupAdditionalDocument;
import com.izmus.data.domain.startups.StartupMeetingSummaryReport;
import com.izmus.data.repository.IStartupMeetingSummaryRepository;
import com.izmus.data.repository.IStartupRepository;
import com.izmus.data.repository.IStartupScoreCardReportRepository;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import net.sf.jasperreports.engine.JasperExportManager;

@Controller
public class StartupAssessmentExportController {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(StartupAssessmentExportController.class);
	@Autowired
	private IStartupScoreCardReportRepository scoreCardReportRepository;
	@Autowired
	private IStartupRepository startupRepository;
	@Autowired
	private IStartupMeetingSummaryRepository startupMeetingSummaryReportRepository;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Export/StartupAdditionalDocuments/{documentId}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Export', '')")
	public void exportAdditionalDocument(@PathVariable("documentId") Integer documentId, HttpServletResponse response,
			@RequestParam(value = "startupId") Integer startupId) {
		try {
			StartupAdditionalDocument additionalDocument = getAdditionalDocument(documentId, startupId);
			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					additionalDocument.getDocumentName() + ".pdf");
			response.setHeader(headerKey, headerValue);
			response.setContentType("application/pdf");
			response.getOutputStream().write(additionalDocument.getDocument());
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("Could Not Export Document: " + documentId + " With Error: " + e.getMessage());
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private StartupAdditionalDocument getAdditionalDocument(Integer documentId, Integer startupId) {
		Startup startup = startupRepository.findDistinctStartupByStartupId(startupId);
		for (Iterator<StartupAdditionalDocument> iterator = startup.getAdditionalDocuments().iterator(); iterator.hasNext();){
			StartupAdditionalDocument document = iterator.next();
			if (document.getDocumentId().equals(documentId)){
				return document;
			}
		}
		return null;
	}

	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Export/StartupScoreCardReport/{reportId}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Export', '')")
	public void exportStartupReport(@PathVariable("reportId") Integer reportId, HttpServletResponse response,
			@RequestParam(value = "additionalDocuments", required = false) String[] additionalDocuments,
			@RequestParam(value = "startupId") Integer startupId) {
		try {
			ScoreCardReport scoreCardReport = scoreCardReportRepository.findOne(reportId);
			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", scoreCardReport.getReportName() + ".pdf");
			response.setHeader(headerKey, headerValue);
			response.setContentType("application/pdf");
			List<byte[]> fileList = getFileList(scoreCardReport, additionalDocuments, startupId);
			// step 1
			Document document = new Document();
			// step 2
			PdfCopy copy = new PdfCopy(document, response.getOutputStream());
			// step 3
			document.open();
			// step 4
			PdfReader reader;
			int n;
			// loop over the documents you want to concatenate
			for (int i = 0; i < fileList.size(); i++) {
				reader = new PdfReader(fileList.get(i));
				// loop over the pages in that document
				n = reader.getNumberOfPages();
				for (int page = 0; page < n;) {
					copy.addPage(copy.getImportedPage(reader, ++page));
				}
				copy.freeReader(reader);
				reader.close();
			}
			// step 5
			document.close();
			response.flushBuffer();
			// JasperExportManager.exportReportToHtmlFile(scoreCardReport.getReport(),
			// "/home/lior/test.html");
			// response.setContentType("text/html");
			// FileInputStream file = new FileInputStream(new
			// File("/home/lior/test.html"));
			// IOUtils.copy(file, response.getOutputStream());
		} catch (Exception e) {
			LOGGER.error("Could Not Export Score Card Report: " + reportId + " With Error: " + e.getMessage());
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private List<byte[]> getFileList(ScoreCardReport scoreCardReport, String[] additionalDocuments, Integer startupId) throws Exception {
		List<byte[]> returnList = new ArrayList<>();
		returnList.add(JasperExportManager.exportReportToPdf(scoreCardReport.getReport()));
		if (additionalDocuments != null)
			for (int i = 0; i < additionalDocuments.length; i++) {
				StartupAdditionalDocument additionalDocument = getAdditionalDocument(Integer.valueOf(additionalDocuments[i]), startupId);
				returnList.add(additionalDocument.getDocument());
			}
		return returnList;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Export/StartupMeetingSummaryReport/{reportId}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Export', '')")
	public void getLoginPage(@PathVariable("reportId") Integer reportId, HttpServletResponse response) {
		try {
			StartupMeetingSummaryReport meetingReport = startupMeetingSummaryReportRepository.findOne(reportId);
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
