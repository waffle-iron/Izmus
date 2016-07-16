package com.izmus.processes.analysisrequest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.startups.AvailableStartup;
import com.izmus.data.domain.users.User;
import com.izmus.data.messages.MessageSource;
import com.izmus.data.repository.IAvailableStartupRepository;
import com.izmus.mail.services.MailSenderService;

@Component("SendUserConfirmationEmailService")
public class SendUserConfirmationEmailService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(SendUserConfirmationEmailService.class);
	private static final String ANALYSIS_REQUEST = "emails.analysisRequest.analysisRequest";
	@Autowired
	private MailSenderService mailService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IAvailableStartupRepository availableStartupRepository;
	@Autowired
	private ServletContext context;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) throws Exception{
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer startupId = (Integer) runtimeService.getVariable(execution.getId(), "startupId");
			AvailableStartup startup = availableStartupRepository.findDistinctAvailableStartupByStartupId(startupId);
			LOGGER.info("Sending Confirmation of Analysis Email To User: " + user.getUserName());
			InputStream inputStream = context.getResourceAsStream("/WEB-INF/emails/analysis-request/analysis-request-email-inline.html");
			String emailString = IOUtils.toString(inputStream);
			emailString = injectStringsToHTML(emailString, user, startup);
			HashMap<String, String> imageMap = getWelcomeEmailImageMap();
			mailService.sendHTMLMail(user.getEntity().getEntityEmail(),
					messageSource.getMessage(ANALYSIS_REQUEST, null, Locale.ENGLISH), emailString,
					imageMap);
		} catch (Exception e) {
			LOGGER.debug("Failed to send out email to confirm user analysis request\r\n" + e.getMessage());
			throw(e);
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private String injectStringsToHTML(String emailString, User user, AvailableStartup startup) {
		ArrayList<String> stringList = new ArrayList<String>();
/*0*/	stringList.add(messageSource.getMessage(ANALYSIS_REQUEST, null,
		Locale.ENGLISH));
/*1*/	stringList.add(messageSource.getMessage("emails.analysisRequest.thankYouForChoosing", null,
		Locale.ENGLISH));
/*2*/	stringList.add(startup.getStartupName());
/*3*/	stringList.add(startup.getSite());
/*4*/	stringList.add(startup.getSite());
/*5*/	stringList.add(messageSource.getMessage("emails.analysisRequest.yourWishList", null,
		Locale.ENGLISH));
/*6*/	stringList.add("www.izmus.com");
/*7*/	stringList.add(messageSource.getMessage("emails.analysisRequest.contactASAP", null,
		Locale.ENGLISH));
		return mailService.injectStringListToEmail(emailString, stringList);
	}
	/*----------------------------------------------------------------------------------------------------*/

	private HashMap<String, String> getWelcomeEmailImageMap() {
		HashMap<String, String> imageMap = new HashMap<>();
		imageMap.put("<logo>", "analysis-request/izmus-logo.png");
		imageMap.put("<side-picture>", "analysis-request/side-picture.png");
		return imageMap;
	}
}
