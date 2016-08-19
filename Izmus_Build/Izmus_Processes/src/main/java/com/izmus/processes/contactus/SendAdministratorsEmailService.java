package com.izmus.processes.contactus;

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
import org.springframework.stereotype.Component;

import com.izmus.data.domain.users.Administrator;
import com.izmus.data.messages.MessageSource;
import com.izmus.data.repository.IAdministratorRepository;
import com.izmus.mail.services.MailSenderService;

@Component("SendAdministratorsEmailService")
public class SendAdministratorsEmailService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(SendAdministratorsEmailService.class);
	private static final String CONTACT_US_SUBJECT = "emails.contactUs.contactUsSubject";
	@Autowired
	private MailSenderService mailService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ServletContext context;
	@Autowired
	private IAdministratorRepository administratorRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) throws Exception{
		String contactName;
		String contactEmail;
		String contactSubject;
		String contactMessage;
		String administratorEmails = "";
		try {
			contactName = (String) runtimeService.getVariable(execution.getId(), "name");
			contactEmail = (String) runtimeService.getVariable(execution.getId(), "email");
			try {
				contactSubject = (String) runtimeService.getVariable(execution.getId(), "subject");
				if (contactSubject == null) contactSubject = "No Subject";
			} catch (Exception e) {
				contactSubject = "No Subject";
			}
			try {
				contactMessage = (String) runtimeService.getVariable(execution.getId(), "message");
				if (contactMessage == null) contactMessage = "No Message";
			} catch (Exception e) {
				contactMessage = "No Message";
			}
			administratorEmails = getAllAdminMail();
			LOGGER.info("Sending Contact Email To Administrators: " + administratorEmails);
			InputStream inputStream = context.getResourceAsStream("/WEB-INF/emails/contactus/contact-us-email-inline.html");
			String emailString = IOUtils.toString(inputStream);
			emailString = injectStringsToHTML(emailString, contactName, contactEmail, contactSubject, contactMessage);
			HashMap<String, String> imageMap = getWelcomeEmailImageMap();
			mailService.sendHTMLMail(administratorEmails,
					messageSource.getMessage(CONTACT_US_SUBJECT, null, Locale.ENGLISH), emailString,
					imageMap);
		} catch (Exception e) {
			LOGGER.debug("Failed to send out email to: " + administratorEmails + "\r\n" + e.getMessage());
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
	private String injectStringsToHTML(String emailString, String contactName, String contactEmail, String contactSubject, String contactMessage) {
		ArrayList<String> stringList = new ArrayList<String>();
/*0*/	stringList.add(messageSource.getMessage(CONTACT_US_SUBJECT, null,
				Locale.ENGLISH));
/*1*/	stringList.add(messageSource.getMessage("emails.contactUs.weGotMail", null,
				Locale.ENGLISH));
/*2*/	stringList.add(messageSource.getMessage("emails.contactUs.receivedNewMessage", null,
				Locale.ENGLISH));
/*3*/	stringList.add(contactName);
/*4*/	stringList.add(messageSource.getMessage("emails.contactUs.hisEmail", null,
				Locale.ENGLISH));
/*5*/	stringList.add(contactEmail);
/*6*/	stringList.add(messageSource.getMessage("emails.contactUs.hisMessage", null,
				Locale.ENGLISH));
/*7*/	stringList.add(messageSource.getMessage("emails.contactUs.subject", null,
				Locale.ENGLISH));
/*8*/	stringList.add(contactSubject);
/*9*/	stringList.add(contactMessage);
		return mailService.injectStringListToEmail(emailString, stringList);
	}
	/*----------------------------------------------------------------------------------------------------*/

	private HashMap<String, String> getWelcomeEmailImageMap() {
		HashMap<String, String> imageMap = new HashMap<>();
		imageMap.put("<logo>", "contact-us/izmus-logo.png");
		imageMap.put("<side-picture>", "contact-us/side-picture.png");
		return imageMap;
	}
}
