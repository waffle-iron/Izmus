package com.izmus.data.domain.startups;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="STARTUPS")
public class Startup implements Serializable, Comparable<Startup> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "STARTUP_ID")
	private Integer startupId;
	@Column(name = "STARTUP_NAME")
	private String startupName;
	@Column(name = "SECTOR")
	private String sector;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "OFFICE_PHONE")
	private String officePhone;
	@Column(name = "MISCELLANEOUS", columnDefinition = "VARCHAR(5242880)")
	private String miscellaneous;
	@Column(name = "REQUESTED_FUNDS")
	private String requestedFunds;
	@Column(name = "ACHIVED_FUNDS")
	private String achivedFunds;
	@Column(name = "STARTUP_OWN_VALUATION")
	private String startupOwnValuation;
	@Column(name = "IZMUS_VALUATION")
	private String izmusValuation;
	@Column(name = "SITE")
	private String site;
	@Column(name = "RESPONSIBLE_USER")
	private String responsibleUser;
	@Column(name = "LOGO", columnDefinition = "VARCHAR(5242880)")
	private String logo;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "startup", fetch = FetchType.LAZY, orphanRemoval = true)
	@Fetch(value = FetchMode.JOIN)
	private Set<StartupContact> contacts;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "startup", fetch = FetchType.LAZY, orphanRemoval = true)
	@Fetch(value = FetchMode.JOIN)
	private Set<StartupScoreCard> scoreCards;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "startup", fetch = FetchType.LAZY, orphanRemoval = true)
	@Fetch(value = FetchMode.JOIN)
	private Set<StartupAdditionalDocument> additionalDocuments;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GROUP_ID", nullable = true)
	@Fetch(value = FetchMode.JOIN)
	@JsonBackReference
	private StartupGroup startupGroup;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof Startup))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(Startup otherStartup) {
		return toString().compareTo(otherStartup.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"startupId\": " + getStartupId() + ", "
				+ "\"startupName: \"" + getStartupName() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getStartupId() {
		return startupId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setStartupId(Integer startupId) {
		this.startupId = startupId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getStartupName() {
		return startupName;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setStartupName(String startupName) {
		this.startupName = startupName;
	}
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

	public String getMiscellaneous() {
		return miscellaneous;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setMiscellaneous(String miscellaneous) {
		this.miscellaneous = miscellaneous;
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

	public String getSector() {
		return sector;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setSector(String sector) {
		this.sector = sector;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getLogo() {
		return logo;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getSite() {
		return site;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setSite(String site) {
		this.site = site;
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
	public StartupGroup getStartupGroup() {
		return startupGroup;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setStartupGroup(StartupGroup startupGroup) {
		this.startupGroup = startupGroup;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<StartupAdditionalDocument> getAdditionalDocuments() {
		return additionalDocuments;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setAdditionalDocuments(Set<StartupAdditionalDocument> additionalDocuments) {
		this.additionalDocuments = additionalDocuments;
	}
}
