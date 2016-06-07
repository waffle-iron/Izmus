package com.izmus.processes.contactus;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.messages.MessageSource;
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
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		String contactName;
		String contactEmail;
		String contactSubject;
		String contactMessage;
		String administratorEmails = "";
		try {
			contactName = (String) runtimeService.getVariable(execution.getId(), "name");
			contactEmail = (String) runtimeService.getVariable(execution.getId(), "email");
			contactSubject = (String) runtimeService.getVariable(execution.getId(), "subject");
			contactMessage = (String) runtimeService.getVariable(execution.getId(), "message");
			administratorEmails = "nevo.lior@gmail.com";
			LOGGER.info("Sending Contact Email To Administrators");
			InputStream inputStream = context.getResourceAsStream("/WEB-INF/emails/contactus/contact-us-email-inline.html");
			String emailString = IOUtils.toString(inputStream);
			emailString = injectStringsToHTML(emailString, contactName, contactEmail, contactSubject, contactMessage);
			HashMap<String, String> imageMap = getWelcomeEmailImageMap();
			mailService.sendHTMLMail(administratorEmails,
					messageSource.getMessage(CONTACT_US_SUBJECT, null, LocaleContextHolder.getLocale()), emailString,
					imageMap);
		} catch (Exception e) {
			LOGGER.debug("Failed to send out email to: " + administratorEmails + "\r\n" + e.getMessage());
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private String injectStringsToHTML(String emailString, String contactName, String contactEmail, String contactSubject, String contactMessage) {
		ArrayList<String> stringList = new ArrayList<String>();
/*0*/	stringList.add(messageSource.getMessage(CONTACT_US_SUBJECT, null,
				LocaleContextHolder.getLocale()));
/*1*/	stringList.add(messageSource.getMessage("emails.contactUs.weGotMail", null,
				LocaleContextHolder.getLocale()));
/*2*/	stringList.add(messageSource.getMessage("emails.contactUs.receivedNewMessage", null,
				LocaleContextHolder.getLocale()));
/*3*/	stringList.add(contactName);
/*4*/	stringList.add(messageSource.getMessage("emails.contactUs.hisEmail", null,
				LocaleContextHolder.getLocale()));
/*5*/	stringList.add(contactEmail);
/*6*/	stringList.add(messageSource.getMessage("emails.contactUs.hisMessage", null,
				LocaleContextHolder.getLocale()));
/*7*/	stringList.add(messageSource.getMessage("emails.contactUs.subject", null,
				LocaleContextHolder.getLocale()));
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
