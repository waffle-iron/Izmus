package com.izmus.data.domain.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="SYSTEM_LOG")
public class SystemLog implements Serializable, Comparable<SystemLog> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "LOG_ID")
	private Integer logId;
	@Column(name = "LOG_LEVEL")
	private String logLevel;
	@Column(name = "LOG_CLASS")
	private String logClass;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOG_TIME")
	private Date logTime;
	@Column(name = "MESSAGE")
	private String message;
	@Column(name = "USER_NAME")
	private String userName;
	@Column(name = "LOG_FUNCTION")
	private String logFunction;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof SystemLog))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(SystemLog otherSystemLog) {
		return toString().compareTo(otherSystemLog.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "Log ID: " + getLogId();
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getLogId() {
		return logId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getLogLevel() {
		return logLevel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getLogClass() {
		return logClass;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setLogClass(String logClass) {
		this.logClass = logClass;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getMessage() {
		return message;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setMessage(String message) {
		this.message = message;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getUserName() {
		return userName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Date getLogTime() {
		return logTime;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getLogFunction() {
		return logFunction;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setLogFunction(String logFunction) {
		this.logFunction = logFunction;
	}
	
}
