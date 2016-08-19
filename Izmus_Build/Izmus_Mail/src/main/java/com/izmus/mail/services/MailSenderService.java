package com.izmus.mail.services;

import java.io.File;
import java.io.FileOutputStream;
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
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service("MailSenderService")
public class MailSenderService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderService.class);
	private static final String EMAIL_IMAGES_DIR = "email-images";
	private static final String SENT_EMAIL_DIR = "sent-emails";
	private static final String INJECT_DIVEDER = "!";
	private static final String FROM_EMAIL_ADDRESS = "info@izmus.com";
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private ServletContext context;
	/*----------------------------------------------------------------------------------------------------*/
	public void sendHTMLMail(String to, String subject, String body, HashMap<String, String> imageMap) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			checkEmailDirectories();
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			mimeMessage.setRecipients(Message.RecipientType.BCC, FROM_EMAIL_ADDRESS);
			mimeMessage.setSubject(subject);
			mimeMessage.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);
			addImagesToMultipart(multipart, imageMap);
			mimeMessage.setContent(multipart);
			mimeMessage.writeTo(new FileOutputStream(context.getRealPath("/") + "/WEB-INF/" + SENT_EMAIL_DIR + "/" + new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()) + ".mht"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		mailSender.send(mimeMessage);
	}
	/*----------------------------------------------------------------------------------------------------*/
	public MimeMessage createHTMLMimeMessage(String to, String subject, String body, HashMap<String, String> imageMap) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			checkEmailDirectories();
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			mimeMessage.setSubject(subject);
			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);
			addImagesToMultipart(multipart, imageMap);
			mimeMessage.setContent(multipart);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return mimeMessage;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void sendMessage(MimeMessage mimeMessage){
		try {
			mimeMessage.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
			mimeMessage.writeTo(new FileOutputStream(context.getRealPath("/") + "/WEB-INF/" + SENT_EMAIL_DIR + "/" + new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()) + ".mht"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		mailSender.send(mimeMessage);
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void checkEmailDirectories() {
		File emailImageDir = new File(context.getRealPath("/") + "/WEB-INF/" + EMAIL_IMAGES_DIR);
		if (!emailImageDir.exists()){
			emailImageDir.mkdirs();
		}
		File sentMailDir = new File(context.getRealPath("/") + "/WEB-INF/" + SENT_EMAIL_DIR);
		if (!sentMailDir.exists()){
			sentMailDir.mkdirs();
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void addImagesToMultipart(MimeMultipart multipart, HashMap<String, String> imageMap) {
		if (imageMap != null) for (Entry<String, String> entry : imageMap.entrySet()){
			try {
				BodyPart messageBodyPart = new MimeBodyPart();
				DataSource fds = null;
				File file = new File(context.getRealPath("/") + "/WEB-INF/" + EMAIL_IMAGES_DIR + "/" + entry.getValue());
				fds = new FileDataSource(file);
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.addHeader("Content-ID", entry.getKey());
				multipart.addBodyPart(messageBodyPart);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
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
