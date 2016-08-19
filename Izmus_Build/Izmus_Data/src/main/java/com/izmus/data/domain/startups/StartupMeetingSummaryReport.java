package com.izmus.data.domain.startups;

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
@Table(name="STARTUP_MEETING_SUMMARY_REPORT")
public class StartupMeetingSummaryReport implements Serializable, Comparable<StartupMeetingSummaryReport> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "SUMMARY_REPORT_ID")
	private Integer summaryReportId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUMMARY_REPORT_DATE", nullable=false)
	private Date summaryReportDate;
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
		if ((obj != null) && ((obj instanceof StartupMeetingSummaryReport))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(StartupMeetingSummaryReport otherMeetingSummaryReport) {
		return toString().compareTo(otherMeetingSummaryReport.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"summaryReportId\": " + getSummaryReportId() + ", "
				+ "\"summaryReportDate: \"" + getSummaryReportDate() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getCreatingUserId() {
		return creatingUserId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Integer getSummaryReportId() {
		return summaryReportId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setSummaryReportId(Integer summaryReportId) {
		this.summaryReportId = summaryReportId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Date getSummaryReportDate() {
		return summaryReportDate;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setSummaryReportDate(Date summaryReportDate) {
		this.summaryReportDate = summaryReportDate;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Integer getMeetingId() {
		return meetingId;
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
