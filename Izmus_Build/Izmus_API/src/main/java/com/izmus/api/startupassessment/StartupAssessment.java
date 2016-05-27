package com.izmus.api.startupassessment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izmus.data.domain.startups.Measurement;
import com.izmus.data.domain.startups.MeasurementQuestion;
import com.izmus.data.domain.startups.ScoreCardReport;
import com.izmus.data.domain.startups.Startup;
import com.izmus.data.domain.startups.StartupContact;
import com.izmus.data.domain.startups.StartupScoreCard;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.IStartupRepository;
import com.izmus.data.repository.IStartupScoreCardReportRepository;
import com.izmus.data.repository.IStartupScoreCardRepository;
import com.izmus.mail.services.MailSenderService;
import com.izmus.reports.startups.StartupReport;

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
	@RequestMapping(value = "/DefaultScoreCard", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Startup Assessment', '')")
	public StartupScoreCard getDefaultScoreCard(@RequestParam(value = "startupId", required = true) Integer startupId) {
		StartupScoreCard scoreCard = null;
		try {
			scoreCard = new StartupScoreCard();
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Startup parentStartup = startupRepository.findOne(startupId);
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
			@RequestParam(value = "startup", required = true) String startupString) {
		ScoreCardReport scoreCardReport = null;
		try {
			StartupScoreCard scoreCard = jacksonObjectMapper.readValue(scoreCardString, StartupScoreCard.class);
			Startup startup = jacksonObjectMapper.readValue(startupString, Startup.class);
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
		return scoreCardReport.getReportId().toString();
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/EmailScoreCardReport", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Startup Assessment', '')")
	public void emailScoreCardReport(@RequestParam(value = "scoreCard", required = true) String scoreCardString,
			@RequestParam(value = "startup", required = true) String startupString,
			@RequestParam(value = "emails", required = true) ArrayList<String> emails) {
		ScoreCardReport scoreCardReport = null;
		try {
			StartupScoreCard scoreCard = jacksonObjectMapper.readValue(scoreCardString, StartupScoreCard.class);
			Startup startup = jacksonObjectMapper.readValue(startupString, Startup.class);
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
			MimeMessage message= mailService.createHTMLMimeMessage(String.join(",", emails),
					scoreCardReport.getReportName(), "",
					null);
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			DataSource attachment = new  ByteArrayDataSource(JasperExportManager.exportReportToPdf(scoreCardReport.getReport()), "application/pdf");
			messageBodyPart.setDataHandler(new DataHandler(attachment));
			messageBodyPart.setFileName(scoreCardReport.getReportName() + ".pdf");
			((MimeMultipart)message.getContent()).addBodyPart(messageBodyPart);
			mailService.sendMessage(message);
		} catch (Exception e) {
			LOGGER.error("Could Not Create Score Card Report For Score Card: " + scoreCardString + " With Error: "
					+ e.getMessage());
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/SaveStartupData", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Startup Assessment+Edit', '')")
	public void saveStartupData(@RequestParam(value = "startupData")final String startupData) {
		Thread saveDataThread = new Thread(){
			@Override
			public void run() {
				Startup startupObject = null;
				try {
					startupObject = jacksonObjectMapper.readValue(startupData, Startup.class);
					Startup changedStartup = null;
					if (startupObject.getStartupId() != null) {
						changedStartup = startupRepository.findOne(startupObject.getStartupId());
					} else {
						changedStartup = startupRepository.findDistinctStartupByStartupName(startupObject.getStartupName());
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
					startupRepository.save(changedStartup);
					LOGGER.info("Startup Data Saved: " + changedStartup);
				} catch (Exception e) {
					LOGGER.error("Could Not Save Startup Information: " + startupObject);
				}
				super.run();
			}
		};
		saveDataThread.setName("STARTUP_SAVE_DATA_THREAD");
		saveDataThread.start();
	}

	/*----------------------------------------------------------------------------------------------------*/
	private void setContacts(Startup changedStartup, Startup startupObject) {
		if (startupObject.getContacts() != null) {
			changedStartup.setContacts(startupObject.getContacts());
			for (StartupContact contact : changedStartup.getContacts()) {
				contact.setStartup(changedStartup);
			}
		} else {
			Set<StartupContact> startupContacts = new HashSet<>();
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
			Set<StartupScoreCard> scoreCards = new HashSet<>();
			changedStartup.setScoreCards(scoreCards);
		}
	}
}
