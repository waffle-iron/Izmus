package com.izmus.api.startupassessment;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.activiti.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izmus.data.domain.startups.FinancialIndicator;
import com.izmus.data.domain.startups.FinancialIndicatorType;
import com.izmus.data.domain.startups.IndicatorPoint;
import com.izmus.data.domain.startups.Measurement;
import com.izmus.data.domain.startups.MeasurementQuestion;
import com.izmus.data.domain.startups.ScoreCardReport;
import com.izmus.data.domain.startups.Startup;
import com.izmus.data.domain.startups.StartupAdditionalDocument;
import com.izmus.data.domain.startups.StartupContact;
import com.izmus.data.domain.startups.StartupMeeting;
import com.izmus.data.domain.startups.StartupScoreCard;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.IFinancialIndicatorRepository;
import com.izmus.data.repository.IFinancialIndicatorTypeRepository;
import com.izmus.data.repository.IStartupAdditionalDocumentRepository;
import com.izmus.data.repository.IStartupRepository;
import com.izmus.data.repository.IStartupScoreCardReportRepository;
import com.izmus.data.repository.IStartupScoreCardRepository;
import com.izmus.mail.services.MailSenderService;
import com.izmus.reports.startups.StartupReport;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("api/StartupAssessment")
public class StartupAssessment {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(StartupAssessment.class);
	private static final String STARTUP_ASSESSING_PROCESS_ID = "StartupAssessingProcessId";
	@Autowired
	private IStartupRepository startupRepository;
	@Autowired
	private IStartupScoreCardRepository startupScoreCardRepository;
	@Autowired
	private IFinancialIndicatorRepository financialIndicatorRepository;
	@Autowired
	private IFinancialIndicatorTypeRepository financialIndicatorTypeRepository;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ObjectMapper jacksonObjectMapper;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private StartupReport startupReport;
	@Autowired
	private IStartupScoreCardReportRepository scoreCardReportRepository;
	@Autowired
	private IStartupAdditionalDocumentRepository startupAdditionalDocumentRepository;
	@Autowired
	private MailSenderService mailService;

	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/StartupAssessmentData", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Startup Assessment', '')")
	public List<Startup> getStartups() {
		List<Startup> startups = new ArrayList<>();
		try {
			startups = startupRepository.findAll();
			for (Startup startup : startups) {
				for (StartupScoreCard scoreCard : startup.getScoreCards()) {
					for (Measurement measurement : scoreCard.getMeasurements()) {
						try {
							measurement.setTitle(messageSource.getMessage(measurement.getTitleLocale(), null,
									LocaleContextHolder.getLocale()));
							measurement.setDescription(messageSource.getMessage(measurement.getDescriptionLocale(),
									null, LocaleContextHolder.getLocale()));
						} catch (Exception e) {
							LOGGER.error(e.getMessage());
						}
						for (MeasurementQuestion question : measurement.getMeasurementQuestions()) {
							try {
								question.setQuestion(messageSource.getMessage(question.getQuestionLocale(), null,
										LocaleContextHolder.getLocale()));
							} catch (Exception e) {
								LOGGER.error(e.getMessage());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return startups;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/FinancialIndicatorTypes", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Startup Assessment', '')")
	public List<FinancialIndicatorType> getIndicatorTypes() {
		List<FinancialIndicatorType> indicatorTypes = new ArrayList<>();
		try {
			indicatorTypes = financialIndicatorTypeRepository.findAll();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return indicatorTypes;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/FinancialIndicators", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Startup Assessment', '')")
	public List<FinancialIndicator> getIndicators(@RequestParam(value = "startupId", required = true) Integer startupId) {
		List<FinancialIndicator> indicators = new ArrayList<>();
		List<FinancialIndicatorType> indicatorTypes = new ArrayList<>();
		try {
			indicators = financialIndicatorRepository.findFinancialIndicatorByStartupId(startupId);
			indicatorTypes = financialIndicatorTypeRepository.findAll();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		if (indicators.size() < indicatorTypes.size()){
			adjustIndicatorsForStartup(indicators, startupId, indicatorTypes);
		}
		return indicators;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void adjustIndicatorsForStartup(List<FinancialIndicator> indicators, Integer startupId,
			List<FinancialIndicatorType> indicatorTypes) {
		for (Iterator<FinancialIndicator> itrIndicator = indicators.iterator();itrIndicator.hasNext();){
			FinancialIndicator nextIndicator = itrIndicator.next();
			boolean found = false;
			for (Iterator<FinancialIndicatorType> itr = indicatorTypes.iterator(); itr.hasNext();){
				FinancialIndicatorType nextType = itr.next();
				if (nextType.getTypeId().equals(nextIndicator.getTypeId())){
					found = true;
					itr.remove();
					break;
				}
			}
			if (!found){
				itrIndicator.remove();
			}
		}
		for (Iterator<FinancialIndicatorType> itr = indicatorTypes.iterator(); itr.hasNext();){
			FinancialIndicatorType nextType = itr.next();
			FinancialIndicator newIndicator = new FinancialIndicator();
			newIndicator.setStartupId(startupId);
			newIndicator.setTypeId(nextType.getTypeId());
			newIndicator = financialIndicatorRepository.save(newIndicator);
			indicators.add(newIndicator);
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/FinancialIndicators", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Startup Assessment', '')")
	public String saveIndicators(@RequestParam(value = "financialIndicators", required = true) String financialIndicatorsObject,
			@RequestParam(value = "startupId", required = true) Integer startupId) {
		try {
			List<FinancialIndicator> financialIndicators = jacksonObjectMapper.readValue(financialIndicatorsObject, 
					jacksonObjectMapper.getTypeFactory().constructCollectionType(List.class, FinancialIndicator.class));
			List<FinancialIndicator> startupFinancialIndicators = financialIndicatorRepository.findFinancialIndicatorByStartupId(startupId);
			for (Iterator<FinancialIndicator> itr = financialIndicators.iterator(); itr.hasNext();){
				FinancialIndicator nextIndicator = itr.next();
				for (IndicatorPoint point : nextIndicator.getPoints()){
					point.setFinancialIndicator(nextIndicator);
				}
				financialIndicatorRepository.save(nextIndicator);
				itr.remove();
			}
			for (FinancialIndicator deletedIndicator : startupFinancialIndicators){
				financialIndicatorRepository.delete(deletedIndicator);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return "{\"result\": \"fail\"}";
		}
		return "{\"result\": \"success\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/DefaultScoreCard", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Startup Assessment', '')")
	public StartupScoreCard getDefaultScoreCard(@RequestParam(value = "startupId", required = true) Integer startupId) {
		StartupScoreCard scoreCard = null;
		try {
			scoreCard = new StartupScoreCard();
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Startup parentStartup = startupRepository.findDistinctStartupByStartupId(startupId);
			if (parentStartup.getScoreCards() == null) {
				parentStartup.setScoreCards(new HashSet<StartupScoreCard>());
			}
			parentStartup.getScoreCards().add(scoreCard);
			Date cardDate = new Date();
			scoreCard.setScoreCardDate(cardDate);
			scoreCard.setStartup(parentStartup);
			parentStartup = startupRepository.save(parentStartup);
			for (StartupScoreCard parentScoreCard : parentStartup.getScoreCards()) {
				if (parentScoreCard.getScoreCardDate().equals(cardDate)) {
					scoreCard = parentScoreCard;
				}
			}
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("scoreCardId", scoreCard.getScoreCardId());
			variables.put("userId", user.getUserId());
			runtimeService.startProcessInstanceByKey(STARTUP_ASSESSING_PROCESS_ID, variables);
			scoreCard = startupScoreCardRepository.findOne(scoreCard.getScoreCardId());
		} catch (Exception e) {
			LOGGER.error("Could Not Start Assessment Process For Startup With ID: " + startupId);
		}
		return scoreCard;
	}

	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/ScoreCardReport", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Startup Assessment', '')")
	public String getScoreCardReport(@RequestParam(value = "scoreCard", required = true) String scoreCardString,
			@RequestParam(value = "startup", required = true) String startupString,
			@RequestParam(value = "additionalDocuments", required = false) String[] additionalDocuments) {
		ScoreCardReport scoreCardReport = null;
		Startup startup = null;
		try {
			StartupScoreCard scoreCard = jacksonObjectMapper.readValue(scoreCardString, StartupScoreCard.class);
			startup = jacksonObjectMapper.readValue(startupString, Startup.class);
			JasperPrint report = startupReport.createScoreCardReport(scoreCard, startup);
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			scoreCardReport = new ScoreCardReport();
			scoreCardReport.setCreatingUserId(user.getUserId());
			scoreCardReport.setReport(report);
			scoreCardReport.setScoreCardId(scoreCard.getScoreCardId());
			scoreCardReport.setReportDate(new Date());
			scoreCardReport.setReportName(startup.getStartupName() + "_Score_Card_Report_"
					+ new SimpleDateFormat("yyyyMMdd").format(scoreCard.getScoreCardDate()));
			scoreCardReport = scoreCardReportRepository.save(scoreCardReport);
			LOGGER.info("Report Saved Successfully To The Database For Score Card Report: " + scoreCardReport);
		} catch (Exception e) {
			LOGGER.error("Could Not Create Score Card Report For Score Card: " + scoreCardString + " With Error: "
					+ e.getMessage());
		}
		return createScoreCardReportReturnString(additionalDocuments, scoreCardReport, startup.getStartupId());
	}

	/*----------------------------------------------------------------------------------------------------*/
	private String createScoreCardReportReturnString(String[] additionalDocuments, ScoreCardReport scoreCardReport, Integer startupId) {
		String returnString = "{\"parameters\": \"" + scoreCardReport.getReportId().toString();
		returnString += "?startupId=" + startupId;
		if (additionalDocuments != null)
			for (int i = 0; i < additionalDocuments.length; i++) {
				returnString += "&additionalDocuments=" + additionalDocuments[i];
			}
		return returnString + "\"}";
	}

	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/EmailScoreCardReport", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Startup Assessment', '')")
	public void emailScoreCardReport(@RequestParam(value = "scoreCard", required = true) String scoreCardString,
			@RequestParam(value = "startup", required = true) String startupString,
			@RequestParam(value = "emails", required = true) ArrayList<String> emails,
			@RequestParam(value = "additionalDocuments", required = false) String[] additionalDocuments) {
		ScoreCardReport scoreCardReport = null;
		Startup startup = null;
		try {
			StartupScoreCard scoreCard = jacksonObjectMapper.readValue(scoreCardString, StartupScoreCard.class);
			startup = jacksonObjectMapper.readValue(startupString, Startup.class);
			JasperPrint report = startupReport.createScoreCardReport(scoreCard, startup);
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			scoreCardReport = new ScoreCardReport();
			scoreCardReport.setCreatingUserId(user.getUserId());
			scoreCardReport.setReport(report);
			scoreCardReport.setScoreCardId(scoreCard.getScoreCardId());
			scoreCardReport.setReportDate(new Date());
			scoreCardReport.setReportName(startup.getStartupName() + "_Score_Card_Report_"
					+ new SimpleDateFormat("yyyyMMdd").format(scoreCard.getScoreCardDate()));
			scoreCardReport = scoreCardReportRepository.save(scoreCardReport);
			LOGGER.info("Report Saved Successfully To The Database For Score Card Report: " + scoreCardReport);
			MimeMessage message = mailService.createHTMLMimeMessage(String.join(",", emails),
					scoreCardReport.getReportName(), "", null);
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			byte[] finalReport = concatinateReports(scoreCardReport, additionalDocuments, startup);
			DataSource attachment = new ByteArrayDataSource(finalReport, "application/pdf");
			messageBodyPart.setDataHandler(new DataHandler(attachment));
			messageBodyPart.setFileName(scoreCardReport.getReportName() + ".pdf");
			((MimeMultipart) message.getContent()).addBodyPart(messageBodyPart);
			mailService.sendMessage(message);
		} catch (Exception e) {
			LOGGER.error("Could Not Create Score Card Report For Score Card: " + scoreCardString + " With Error: "
					+ e.getMessage());
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private byte[] concatinateReports(ScoreCardReport scoreCardReport, String[] additionalDocuments, Startup startup) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			List<byte[]> fileList = getFileList(scoreCardReport, additionalDocuments, startup);
			// step 1
			Document document = new Document();
			// step 2
			PdfCopy copy = new PdfCopy(document, stream);
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
			stream.flush();
		} catch (Exception e) {
			LOGGER.error("Could Not Create Score Card Report For Score Card: " + scoreCardReport + " With Error: "
					+ e.getMessage());
		}
		return stream.toByteArray();
	}
	/*----------------------------------------------------------------------------------------------------*/
	private List<byte[]> getFileList(ScoreCardReport scoreCardReport, String[] additionalDocuments, Startup startup) throws Exception{
		List<byte[]> returnList = new ArrayList<>();
		Startup databaseStartup = startupRepository.findDistinctStartupByStartupId(startup.getStartupId());
		returnList.add(JasperExportManager.exportReportToPdf(scoreCardReport.getReport()));
		if (additionalDocuments != null) for (int i = 0; i < additionalDocuments.length; i++){
			StartupAdditionalDocument additionalDocument = getDocument(Integer.valueOf(additionalDocuments[i]), databaseStartup);
			returnList.add(additionalDocument.getDocument());
		}
		return returnList;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private StartupAdditionalDocument getDocument(Integer documentId, Startup startup) {
		for (Iterator<StartupAdditionalDocument> iterator = startup.getAdditionalDocuments().iterator(); iterator.hasNext();){
			StartupAdditionalDocument document = iterator.next();
			if (document.getDocumentId().equals(documentId)){
				return document;
			}
		}
		return null;
	}

	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/SaveStartupData", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Startup Assessment+Edit', '')")
	public String saveStartupData(@RequestParam(value = "startupData") final String startupData) {
		Startup startupObject = null;
		try {
			startupObject = jacksonObjectMapper.readValue(startupData, Startup.class);
			Startup changedStartup = null;
			if (startupObject.getStartupId() != null) {
				changedStartup = startupRepository.findDistinctStartupByStartupId(startupObject.getStartupId());
			} else {
				changedStartup = startupRepository
						.findDistinctStartupByStartupName(startupObject.getStartupName());
			}
			if (changedStartup == null)
				changedStartup = new Startup();
			changedStartup.setStartupName(startupObject.getStartupName());
			changedStartup.setAddress(startupObject.getAddress());
			changedStartup.setOfficePhone(startupObject.getOfficePhone());
			changedStartup.setMiscellaneous(startupObject.getMiscellaneous());
			changedStartup.setRequestedFunds(startupObject.getRequestedFunds());
			changedStartup.setAchivedFunds(startupObject.getAchivedFunds());
			changedStartup.setStartupOwnValuation(startupObject.getStartupOwnValuation());
			changedStartup.setIzmusValuation(startupObject.getIzmusValuation());
			changedStartup.setSector(startupObject.getSector());
			changedStartup.setLogo(startupObject.getLogo());
			changedStartup.setResponsibleUser(startupObject.getResponsibleUser());
			changedStartup.setSite(startupObject.getSite());
			setScoreCards(changedStartup, startupObject);
			setContacts(changedStartup, startupObject);
			setMeetings(changedStartup, startupObject);
			startupRepository.save(changedStartup);
			LOGGER.info("Startup Data Saved: " + changedStartup);
		} catch (Exception e) {
			LOGGER.error("Could Not Save Startup Information: " + startupObject);
			return "{\"result\": \"fail\"}";
		}
		return "{\"result\": \"success\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void setMeetings(Startup changedStartup, Startup startupObject) {
		if (startupObject.getMeetings() != null) {
			changedStartup.setMeetings(startupObject.getMeetings());
			for (StartupMeeting meeting : changedStartup.getMeetings()) {
				meeting.setStartup(changedStartup);
			}
		} else {
			HashSet<StartupMeeting> startupMeetings = new HashSet<>();
			changedStartup.setMeetings(startupMeetings);
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/AdditionalDocument", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Startup Assessment+Edit', '')")
	public String uploadAdditionalDocument(@RequestParam("file") MultipartFile file,
			@RequestParam("startupId") Integer startupId) {
		StartupAdditionalDocument newDocument = null;
		try {
			Startup parentStartup = startupRepository.findDistinctStartupByStartupId(startupId);
			newDocument = new StartupAdditionalDocument();
			newDocument.setDocument(file.getBytes());
			newDocument.setDocumentName(file.getOriginalFilename());
			newDocument.setStartup(parentStartup);
			if (parentStartup.getAdditionalDocuments() == null) {
				parentStartup.setAdditionalDocuments(new HashSet<StartupAdditionalDocument>());
			}
			parentStartup.getAdditionalDocuments().add(newDocument);
			newDocument = startupAdditionalDocumentRepository.save(newDocument);
		} catch (Exception e) {
			LOGGER.error("Could Not Save Startup Additional Document: " + file.getName());
		}
		LOGGER.info("Document Saved Successfully: " + file.getName());
		return "{\"documentId\": \"" + newDocument.getDocumentId() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/AdditionalDocument", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('Startup Assessment+Edit', '')")
	public String deleteAdditionalDocument(@RequestParam("documentId") Integer documentId, @RequestParam("startupId") Integer startupId) {
		try {
			Startup parentStartup = startupRepository.findDistinctStartupByStartupId(startupId);
			HashSet<StartupAdditionalDocument> newSet = new HashSet<StartupAdditionalDocument>();
			for (Iterator<StartupAdditionalDocument> iterator = parentStartup.getAdditionalDocuments().iterator(); iterator.hasNext();){
				StartupAdditionalDocument parentDocument = iterator.next();
				if (!parentDocument.getDocumentId().equals(documentId)){
					newSet.add(parentDocument);
				}
			}
			parentStartup.setAdditionalDocuments(newSet);
			startupRepository.save(parentStartup);
		} catch (Exception e) {
			LOGGER.error("Could Not Delete Startup Additional Document: " + documentId);
		}
		LOGGER.info("Document Deleted Successfully: " + documentId);
		return "{\"result\": \"success\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void setContacts(Startup changedStartup, Startup startupObject) {
		if (startupObject.getContacts() != null) {
			changedStartup.setContacts(startupObject.getContacts());
			for (StartupContact contact : changedStartup.getContacts()) {
				contact.setStartup(changedStartup);
			}
		} else {
			HashSet<StartupContact> startupContacts = new HashSet<>();
			changedStartup.setContacts(startupContacts);
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private void setScoreCards(Startup changedStartup, Startup startupObject) {
		if (startupObject.getScoreCards() != null) {
			changedStartup.setScoreCards(startupObject.getScoreCards());
			for (StartupScoreCard scoreCard : changedStartup.getScoreCards()) {
				scoreCard.setStartup(changedStartup);
				for (Measurement measurement : scoreCard.getMeasurements()) {
					measurement.setScoreCard(scoreCard);
					for (MeasurementQuestion question : measurement.getMeasurementQuestions()) {
						question.setMeasurement(measurement);
					}
				}
			}
		} else {
			HashSet<StartupScoreCard> scoreCards = new HashSet<>();
			changedStartup.setScoreCards(scoreCards);
		}
	}
}
