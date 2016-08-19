package com.izmus.processes.newuser;

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
import org.springframework.stereotype.Component;

import com.izmus.data.domain.users.User;
import com.izmus.data.messages.MessageSource;
import com.izmus.data.repository.IUserRepository;
import com.izmus.mail.services.MailSenderService;

@Component("IzmusWelcomeEmail")
public class IzmusWelcomeEmail {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(IzmusWelcomeEmail.class);
	private static final String IZMUS_WELCOME_SUBJECT = "emails.izmusWelcomeEmail.izmusWelcomeSubject";
	@Autowired
	private MailSenderService mailService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ServletContext context;
	@Autowired
	private IUserRepository userRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		User user = null;
		String userEmail = "unknown";
		try {
			Integer userId = (Integer) runtimeService.getVariable(execution.getId(), "userId");
			user = userRepository.findDistinctUserByUserId(userId);
			userEmail = user.getEntity().getEntityEmail();
			LOGGER.info("Sending Welcome Email To: " + userEmail);
			InputStream inputStream = context.getResourceAsStream("/WEB-INF/emails/welcome-email-inline.html");
			String emailString = IOUtils.toString(inputStream);
			emailString = injectStringsToHTML(emailString, user);
			HashMap<String, String> imageMap = getWelcomeEmailImageMap();
			mailService.sendHTMLMail(userEmail,
					messageSource.getMessage(IZMUS_WELCOME_SUBJECT, null, Locale.ENGLISH), emailString,
					imageMap);
		} catch (Exception e) {
			LOGGER.debug("Failed to send out email to: " + userEmail + "\r\n" + e.getMessage());
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private String injectStringsToHTML(String emailString, User user) {
		ArrayList<String> stringList = new ArrayList<String>();
		stringList.add(messageSource.getMessage(IZMUS_WELCOME_SUBJECT, null,
				Locale.ENGLISH));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.welcomeMessage", null,
				Locale.ENGLISH));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.password", null,
				Locale.ENGLISH));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.address", null,
				Locale.ENGLISH));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.unsubscribeMessage", null,
				Locale.ENGLISH));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.unsubscribe", null,
				Locale.ENGLISH));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.dir", null,
				Locale.ENGLISH));
		stringList.add(user.getPassword());
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.izmusWelcomeSubject", null,
				Locale.ENGLISH));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.logInNow", null,
				Locale.ENGLISH));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.userNameIs", null,
				Locale.ENGLISH));
		stringList.add(messageSource.getMessage("emails.izmusWelcomeEmail.tempPassIs", null,
				Locale.ENGLISH));
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
