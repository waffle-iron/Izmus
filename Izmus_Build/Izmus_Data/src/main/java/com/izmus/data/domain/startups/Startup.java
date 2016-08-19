package com.izmus.data.domain.startups;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="STARTUPS")
public class Startup extends StartupAbstract{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "OFFICE_PHONE")
	private String officePhone;
	@Column(name = "REQUESTED_FUNDS", columnDefinition = "VARCHAR(5242880)")
	private String requestedFunds;
	@Column(name = "ACHIVED_FUNDS", columnDefinition = "VARCHAR(5242880)")
	private String achivedFunds;
	@Column(name = "STARTUP_OWN_VALUATION", columnDefinition = "VARCHAR(5242880)")
	private String startupOwnValuation;
	@Column(name = "IZMUS_VALUATION", columnDefinition = "VARCHAR(5242880)")
	private String izmusValuation;
	@Column(name = "RESPONSIBLE_USER")
	private String responsibleUser;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "startup", fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SELECT)
	private Set<StartupContact> contacts;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "startup", fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SELECT)
	private Set<StartupScoreCard> scoreCards;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "startup", fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SELECT)
	private Set<StartupAdditionalDocument> additionalDocuments;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "startup", fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SELECT)
	private Set<StartupMeeting> meetings;
	@Column(name = "STARTUP_GROUP_IDS")
	@ElementCollection(targetClass = Integer.class, fetch = FetchType.EAGER)
	private Set<Integer> startupGroupsIds;
	@Column(name = "FINANCIAL_INDICATORS_IDS")
	@ElementCollection(targetClass = Integer.class, fetch = FetchType.EAGER)
	private Set<Integer> financialIndicatorsIds;
	/*----------------------------------------------------------------------------------------------------*/

	public String getAddress() {
		return address;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setAddress(String address) {
		this.address = address;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getOfficePhone() {
		return officePhone;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getRequestedFunds() {
		return requestedFunds;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setRequestedFunds(String requestedFunds) {
		this.requestedFunds = requestedFunds;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getAchivedFunds() {
		return achivedFunds;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setAchivedFunds(String achivedFunds) {
		this.achivedFunds = achivedFunds;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getStartupOwnValuation() {
		return startupOwnValuation;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setStartupOwnValuation(String startupOwnValuation) {
		this.startupOwnValuation = startupOwnValuation;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getIzmusValuation() {
		return izmusValuation;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setIzmusValuation(String izmusValuation) {
		this.izmusValuation = izmusValuation;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<StartupContact> getContacts() {
		return contacts;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setContacts(Set<StartupContact> contacts) {
		this.contacts = contacts;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Set<StartupScoreCard> getScoreCards() {
		return scoreCards;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setScoreCards(Set<StartupScoreCard> scoreCards) {
		this.scoreCards = scoreCards;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getResponsibleUser() {
		return responsibleUser;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setResponsibleUser(String responsibleUser) {
		this.responsibleUser = responsibleUser;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<StartupAdditionalDocument> getAdditionalDocuments() {
		return additionalDocuments;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setAdditionalDocuments(Set<StartupAdditionalDocument> additionalDocuments) {
		this.additionalDocuments = additionalDocuments;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<Integer> getStartupGroupsIds() {
		return startupGroupsIds;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setStartupGroupsIds(Set<Integer> startupGroupsIds) {
		this.startupGroupsIds = startupGroupsIds;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<StartupMeeting> getMeetings() {
		return meetings;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setMeetings(Set<StartupMeeting> meetings) {
		this.meetings = meetings;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<Integer> getFinancialIndicatorsIds() {
		return financialIndicatorsIds;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setFinancialIndicatorsIds(Set<Integer> financialIndicatorsIds) {
		this.financialIndicatorsIds = financialIndicatorsIds;
	}
	
}
