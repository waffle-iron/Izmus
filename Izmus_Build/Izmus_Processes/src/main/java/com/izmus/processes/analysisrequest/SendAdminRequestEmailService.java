package com.izmus.processes.analysisrequest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.izmus.data.domain.contacts.IzmusContact;
import com.izmus.data.domain.startups.AvailableStartup;
import com.izmus.data.domain.users.Administrator;
import com.izmus.data.domain.users.User;
import com.izmus.data.messages.MessageSource;
import com.izmus.data.repository.IAdministratorRepository;
import com.izmus.data.repository.IAvailableStartupRepository;
import com.izmus.data.repository.IIzmusContactRepository;
import com.izmus.mail.services.MailSenderService;

@Component("SendAdminRequestEmailService")
public class SendAdminRequestEmailService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(SendAdminRequestEmailService.class);
	private static final String ADMIN_ANALYSIS_REQUEST = "emails.analysisRequest.adminAnalysisRequest";
	@Autowired
	private MailSenderService mailService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ServletContext context;
	@Autowired
	private IAvailableStartupRepository availableStartupRepository;
	@Autowired
	private IIzmusContactRepository izmusContactRepository;
	@Autowired
	private IAdministratorRepository administratorRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) throws Exception{
		String administratorEmails = "";
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer startupId = (Integer) runtimeService.getVariable(execution.getId(), "startupId");
			AvailableStartup startup = availableStartupRepository.findDistinctAvailableStartupByStartupId(startupId);
			administratorEmails = getAllAdminMail();
			LOGGER.info("Sending Request Email To Administrators: " + administratorEmails);
			InputStream inputStream = context.getResourceAsStream("/WEB-INF/emails/analysis-request/admin-request-email-inline.html");
			String emailString = IOUtils.toString(inputStream);
			emailString = injectStringsToHTML(emailString, user, startup);
			HashMap<String, String> imageMap = getWelcomeEmailImageMap();
			mailService.sendHTMLMail(administratorEmails,
					messageSource.getMessage(ADMIN_ANALYSIS_REQUEST, null, Locale.ENGLISH), emailString,
					imageMap);
		} catch (Exception e) {
			LOGGER.debug("Failed to send out email to Admins for analysis request\r\n" + e.getMessage());
			throw(e);
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private String getAllAdminMail() {
		String returnString = "";
		List<Administrator> administrators = administratorRepository.findAll();
		for (Administrator admin : administrators){
			if (returnString.isEmpty()){
				returnString += admin.getEntityEmail();
			}
			else {
				returnString += "," + admin.getEntityEmail();
			}
		}
		return returnString;
	}

	/*----------------------------------------------------------------------------------------------------*/
	private String injectStringsToHTML(String emailString, User user, AvailableStartup startup) {
		ArrayList<String> stringList = new ArrayList<String>();
/*0*/	stringList.add(messageSource.getMessage(ADMIN_ANALYSIS_REQUEST, null,
		Locale.ENGLISH));
/*1*/	stringList.add(messageSource.getMessage("emails.analysisRequest.weHaveANewRequest", null,
		Locale.ENGLISH));
		if(user.getEntity().getContactId() != null){
			IzmusContact contact = izmusContactRepository.findDistinctIzmusContactByContactId(user.getEntity().getContactId());
/*2*/		stringList.add(contact.getFirstName() + " " + contact.getLastName());
		}
		else {
/*2*/		stringList.add(user.getUserName());
		}
/*3*/	stringList.add(user.getEntity().getEntityEmail());
/*4*/	stringList.add(messageSource.getMessage("emails.analysisRequest.heWantAnalysisFor", null,
		Locale.ENGLISH));
/*5*/	stringList.add(startup.getStartupName());
/*6*/	stringList.add(startup.getSite());
/*7*/	stringList.add(startup.getSite());
/*8*/	stringList.add(messageSource.getMessage("emails.analysisRequest.letsContactThem", null,
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
