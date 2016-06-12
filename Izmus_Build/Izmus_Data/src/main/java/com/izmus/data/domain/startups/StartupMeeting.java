package com.izmus.data.domain.startups;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="STARTUP_MEETINGS")
public class StartupMeeting implements Serializable, Comparable<StartupMeeting> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "MEETING_ID")
	private Integer meetingId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MEETING_DATE", nullable=false)
	private Date meetingDate;
	@Column(name = "MEETING_PURPOSE")
	private String meetingPurpose;
	@Column(name = "STARTUP_PARTY")
	private String startupParty;
	@Column(name = "COMPANY_PARTY")
	private String companyParty;
	@Column(name = "PARTIES")
	private String parties;
	@Column(name = "COMPANY_INTRO")
	private String companyIntro;
	@Column(name = "MEETING_FLOW")
	private String meetingFlow;
	@Column(name = "ONE_SENTENCE_SUMMARY")
	private String oneSentenceSummary;
	@Column(name = "FOLLOW_UP")
	private String followUp;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STARTUP_ID", nullable = false)
	@Fetch(value = FetchMode.JOIN)
	@JsonBackReference
	private Startup startup;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof StartupMeeting))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(StartupMeeting otherDocument) {
		return toString().compareTo(otherDocument.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"meetingId\": " + getMeetingId() + ", "
				+ "\"meetingDate: \"" + getMeetingDate() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Startup getStartup() {
		return startup;
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
	public void setStartup(Startup startup) {
		this.startup = startup;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Date getMeetingDate() {
		return meetingDate;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getMeetingPurpose() {
		return meetingPurpose;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setMeetingPurpose(String meetingPurpose) {
		this.meetingPurpose = meetingPurpose;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getStartupParty() {
		return startupParty;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setStartupParty(String startupParty) {
		this.startupParty = startupParty;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getCompanyParty() {
		return companyParty;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setCompanyParty(String companyParty) {
		this.companyParty = companyParty;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getParties() {
		return parties;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setParties(String parties) {
		this.parties = parties;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getCompanyIntro() {
		return companyIntro;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setCompanyIntro(String companyIntro) {
		this.companyIntro = companyIntro;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getMeetingFlow() {
		return meetingFlow;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setMeetingFlow(String meetingFlow) {
		this.meetingFlow = meetingFlow;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getOneSentenceSummary() {
		return oneSentenceSummary;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setOneSentenceSummary(String oneSentenceSummary) {
		this.oneSentenceSummary = oneSentenceSummary;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getFollowUp() {
		return followUp;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}
	
}
