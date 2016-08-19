package com.izmus.mail.test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:/Izmus-mail-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ContactUsTest {
	/*----------------------------------------------------------------------------------------------------*/
	private static final String INJECT_DIVEDER = "!";
	private static final String WEB_INF = "../Izmus_View/src/main/webapp/WEB-INF/";
	private static final String EMAIL_IMAGES_DIR = WEB_INF + "email-images/";
	private static final String SENT_EMAIL_DIR = WEB_INF + "sent-emails/";
	private static final String FROM_EMAIL_ADDRESS = "lior@izmus.com";
	@Autowired
	private JavaMailSenderImpl mailSender;
	/*----------------------------------------------------------------------------------------------------*/
	@Test
	public void testSendEmail(){
		try {
			checkEmailDirectories();
			InputStream inputStream = new FileInputStream(WEB_INF + "emails/contactus/contact-us-email-inline.html");
			String emailString = IOUtils.toString(inputStream);
			emailString = injectStringsToHTML(emailString);
			HashMap<String, String> imageMap = getConfirmationEmailImageMap();
			sendHTMLMail("nevo.lior@gmail.com, lior@izmus.com, lior.nevo@ubs.com", "Test Welcome Email", emailString,
					imageMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void checkEmailDirectories() {
		File emailImageDir = new File(EMAIL_IMAGES_DIR);
		if (!emailImageDir.exists()){
			emailImageDir.mkdirs();
		}
		File sentMailDir = new File(SENT_EMAIL_DIR);
		if (!sentMailDir.exists()){
			sentMailDir.mkdirs();
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private String injectStringsToHTML(String emailString) {
		ArrayList<String> stringList = new ArrayList<String>();
		stringList.add("emails.izmusWelcomeEmail.izmusWelcomeSubject");
		stringList.add("emails.izmusWelcomeEmail.welcomeMessage");
		stringList.add("emails.izmusWelcomeEmail.password");
		stringList.add("emails.izmusWelcomeEmail.address");
		stringList.add("emails.izmusWelcomeEmail.unsubscribeMessage");
		stringList.add("emails.izmusWelcomeEmail.unsubscribe");
		stringList.add("emails.izmusWelcomeEmail.dir");
		stringList.add("temp password");
		stringList.add("emails.izmusWelcomeEmail.izmusWelcomeSubject");
		stringList.add("emails.izmusWelcomeEmail.logInNow");
		stringList.add("emails.izmusWelcomeEmail.userNameIs");
		stringList.add("emails.izmusWelcomeEmail.tempPassIs");
		stringList.add("user name");
		return injectStringListToEmail(emailString, stringList);
	}
	/*----------------------------------------------------------------------------------------------------*/

	private HashMap<String, String> getConfirmationEmailImageMap() {
		HashMap<String, String> imageMap = new HashMap<>();
		imageMap.put("<logo>", "contact-us/izmus-logo.png");
		imageMap.put("<side-picture>", "contact-us/side-picture.png");
		return imageMap;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void sendHTMLMail(String to, String subject, String body, HashMap<String, String> imageMap) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			mimeMessage.setSubject(subject);
			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);
			addImagesToMultipart(multipart, imageMap);
			mimeMessage.setContent(multipart);
			mimeMessage.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
			mimeMessage.writeTo(new FileOutputStream(SENT_EMAIL_DIR + new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()) + ".mht"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mailSender.send(mimeMessage);
	}

	/*----------------------------------------------------------------------------------------------------*/
	private void addImagesToMultipart(MimeMultipart multipart, HashMap<String, String> imageMap) {
		if (imageMap != null) for (Entry<String, String> entry : imageMap.entrySet()){
			try {
				BodyPart messageBodyPart = new MimeBodyPart();
				DataSource fds = null;
				File file = new File(EMAIL_IMAGES_DIR + entry.getValue());
				fds = new FileDataSource(file);
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.addHeader("Content-ID", entry.getKey());
				multipart.addBodyPart(messageBodyPart);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String injectStringListToEmail(String emailString, ArrayList<String> stringList) {
		for (int i = 0; i < stringList.size(); i++){
			emailString = emailString.replaceAll(INJECT_DIVEDER + i + INJECT_DIVEDER, stringList.get(i));
		}
		return emailString;
	}
}
