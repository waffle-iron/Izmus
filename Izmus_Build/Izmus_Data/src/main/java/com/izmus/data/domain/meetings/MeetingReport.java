package com.izmus.data.domain.meetings;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.jasperreports.engine.JasperPrint;

@Entity
@Table(name="MEETING_REPORT")
public class MeetingReport implements Serializable, Comparable<MeetingReport> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "REPORT_ID")
	private Integer reportId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REPORT_DATE", nullable=false)
	private Date reportDate;
	@Column(name = "CREATING_USER_ID")
	private Integer creatingUserId;
	@Column(name = "MEETING_ID")
	private Integer meetingId;
	@Column(name = "REPORT_NAME")
	private String reportName;
	@Lob
	@Column(name = "REPORT", length = 16777215)
	private JasperPrint report;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof MeetingReport))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(MeetingReport otherMeetingSummaryReport) {
		return toString().compareTo(otherMeetingSummaryReport.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"summaryReportId\": " + getReportId() + ", "
				+ "\"summaryReportDate: \"" + getReportDate() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getCreatingUserId() {
		return creatingUserId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getMeetingId() {
		return meetingId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Integer getReportId() {
		return reportId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Date getReportDate() {
		return reportDate;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setCreatingUserId(Integer creatingUserId) {
		this.creatingUserId = creatingUserId;
	}

	/*----------------------------------------------------------------------------------------------------*/

	public JasperPrint getReport() {
		return report;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setReport(JasperPrint report) {
		this.report = report;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getReportName() {
		return reportName;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
}
