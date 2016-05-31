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

import com.izmus.data.domain.users.User;
import com.izmus.data.messages.MessageSource;
import com.izmus.mail.services.MailSenderService;

@Component("SendAdministratorsEmailService")
public class SendAdministratorsEmailService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(SendAdministratorsEmailService.class);
	private static final String IZMUS_WELCOME_SUBJECT = "emails.izmusWelcomeEmail.izmusWelcomeSubject";
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
		try {
			contactName = (String) runtimeService.getVariable(execution.getId(), "name");
			contactEmail = (String) runtimeService.getVariable(execution.getId(), "email");
			contactSubject = (String) runtimeService.getVariable(execution.getId(), "subject");
			contactMessage = (String) runtimeService.getVariable(execution.getId(), "message");
			LOGGER.info("Sending Contact Email To Administrator: ");
//			InputStream inputStream = context.getResourceAsStream("/WEB-INF/emails/welcome-email-inline.html");
//			String emailString = IOUtils.toString(inputStream);
//			emailString = injectStringsToHTML(emailString, user);
//			HashMap<String, String> imageMap = getWelcomeEmailImageMap();
//			mailService.sendHTMLMail(userEmail,
//					messageSource.getMessage(IZMUS_WELCOME_SUBJECT, null, LocaleContextHolder.getLocale()), emailString,
//					imageMap);
		} catch (Exception e) {
//			LOGGER.debug("Failed to send out email to: " + userEmail + "\r\n" + e.getMessage());
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private String injectStringsToHTML(String emailString, User user) {
		ArrayList<String> stringList = new ArrayList<String>();
		stringList.add(messageSource.getMessage(IZMUS_WELCOME_SUBJECT, null,
				LocaleContextHolder.getLocale()));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.welcomeMessage", null,
				LocaleContextHolder.getLocale()));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.password", null,
				LocaleContextHolder.getLocale()));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.address", null,
				LocaleContextHolder.getLocale()));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.unsubscribeMessage", null,
				LocaleContextHolder.getLocale()));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.unsubscribe", null,
				LocaleContextHolder.getLocale()));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.dir", null,
				LocaleContextHolder.getLocale()));
		stringList.add(user.getPassword());
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.izmusWelcomeSubject", null,
				LocaleContextHolder.getLocale()));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.logInNow", null,
				LocaleContextHolder.getLocale()));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.userNameIs", null,
				LocaleContextHolder.getLocale()));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.tempPassIs", null,
				LocaleContextHolder.getLocale()));
		stringList.add(user.getUserName());
		return mailService.injectStringListToEmail(emailString, stringList);
	}
	/*----------------------------------------------------------------------------------------------------*/

	private HashMap<String, String> getWelcomeEmailImageMap() {
		HashMap<String, String> imageMap = new HashMap<>();
		imageMap.put("<logo>", "welcome-email/izmus-logo.png");
		imageMap.put("<side-picture>", "welcome-email/side-picture.png");
		return imageMap;
	}
}
