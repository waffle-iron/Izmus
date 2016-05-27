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
@Table(name="STARTUP_SCORE_CARD_REPORT")
public class ScoreCardReport implements Serializable, Comparable<ScoreCardReport> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "REPORT_ID")
	private Integer reportId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REPORT_DATE", nullable=false)
	private Date reportDate;
	@Column(name = "REPORT_NAME")
	private String reportName;
	@Column(name = "CREATING_USER_ID")
	private Integer creatingUserId;
	@Column(name = "SCORE_CARD_ID")
	private Integer scoreCardId;
	@Lob
	@Column(name = "REPORT", length = 16777215)
	private JasperPrint report;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof ScoreCardReport))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(ScoreCardReport otherScoreCardRepport) {
		return toString().compareTo(otherScoreCardRepport.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"reportId\": " + getReportId() + ", "
				+ "\"reportDate: \"" + getReportDate() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
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

	public Integer getCreatingUserId() {
		return creatingUserId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setCreatingUserId(Integer creatingUserId) {
		this.creatingUserId = creatingUserId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Integer getScoreCardId() {
		return scoreCardId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setScoreCardId(Integer scoreCardId) {
		this.scoreCardId = scoreCardId;
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
