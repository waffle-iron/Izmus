package com.izmus.logger;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.izmus.data.domain.log.SystemLog;
import com.izmus.data.repository.ISystemLogRepository;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class DatabaseLogger extends AppenderBase<ILoggingEvent> {
	/*----------------------------------------------------------------------------------------------------*/
	@Autowired
	private ISystemLogRepository systemLogRepository;
	private PatternLayoutEncoder encoder;
	public static final String FUNCTION_LOG_SEPERATOR = "|";
	/*----------------------------------------------------------------------------------------------------*/
	public void append(ILoggingEvent event) {
		SystemLog newLogEvent = new SystemLog();
		newLogEvent.setLogLevel(event.getLevel().levelStr);
		newLogEvent.setLogTime(new Date(event.getTimeStamp()));
		newLogEvent.setLogClass(event.getLoggerName());
		newLogEvent.setMessage(getMessage(event.getFormattedMessage()));
		newLogEvent.setLogFunction(getFunctionFromMessage(event.getFormattedMessage()));
		String userId = event.getMDCPropertyMap().get("userId");
		if (userId == null){
			userId = "System User";
		}
		newLogEvent.setUserName(userId);
		systemLogRepository.save(newLogEvent);
	}
	/*----------------------------------------------------------------------------------------------------*/
	private String getMessage(String formattedMessage) {
		String message = formattedMessage;
		try {
			message = formattedMessage.substring(formattedMessage.indexOf(FUNCTION_LOG_SEPERATOR) + 1);
		} catch (Exception e) {}
		return message;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private String getFunctionFromMessage(String formattedMessage) {
		String function = "";
		try {
			function = formattedMessage.substring(0, formattedMessage.indexOf(FUNCTION_LOG_SEPERATOR));
		} catch (Exception e) {}
		return function;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public void start() {
		if (this.encoder == null) {
			addError("No encoder set for the appender named [" + name + "].");
			return;
		}
		try {
			encoder.init(System.out);
		} catch (IOException e) {
		}
		super.start();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public PatternLayoutEncoder getEncoder() {
		return encoder;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setEncoder(PatternLayoutEncoder encoder) {
		this.encoder = encoder;
	}
}
