package com.izmus.data.domain.contacts;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

@Entity
@Table(name="INVESTOR_CONTACTS")
public class InvestorContact extends IzmusContact{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Column(name = "ENTITY_ID")
	private Integer entityId;
	@Column(name = "FOUNDED", columnDefinition = "VARCHAR(5242880)")
	private String founded;
	@Column(name = "INVESTOR_TYPE")
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	private Set<String> investorType;
	@Column(name = "COMPANY_LOGO", columnDefinition = "VARCHAR(5242880)")
	private String companyLogo;
	@Column(name = "ASSETS_UNDER_MANAGEMENT")
	private String assetsUnderManagement;
	@Column(name = "AVERAGE_INVESTMENT_SIZE")
	private String averageInvestmentSize;
	@Column(name = "NUMBER_OF_PAST_INVESTMENTS")
	private String numberOfPastInvestments;
	@Column(name = "NUMBER_OF_PAST_EXITS")
	private String numberOfPastExits;
	@Column(name = "ASSOSIATED_FINDERS")
	@ElementCollection(targetClass = Integer.class, fetch = FetchType.EAGER)
	private Set<Integer> assosiatedFinders;
	@Column(name = "INVESTMENT_STAGE")
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	private Set<String> investmentStage;
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getEntityId() {
		return entityId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public String getFounded() {
		return founded;
	}
	public void setFounded(String founded) {
		this.founded = founded;
	}
	public Set<String> getInvestorType() {
		return investorType;
	}
	public void setInvestorType(Set<String> investorType) {
		this.investorType = investorType;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getAssetsUnderManagement() {
		return assetsUnderManagement;
	}
	public void setAssetsUnderManagement(String assetsUnderManagement) {
		this.assetsUnderManagement = assetsUnderManagement;
	}
	public String getAverageInvestmentSize() {
		return averageInvestmentSize;
	}
	public void setAverageInvestmentSize(String averageInvestmentSize) {
		this.averageInvestmentSize = averageInvestmentSize;
	}
	public String getNumberOfPastInvestments() {
		return numberOfPastInvestments;
	}
	public void setNumberOfPastInvestments(String numberOfPastInvestments) {
		this.numberOfPastInvestments = numberOfPastInvestments;
	}
	public String getNumberOfPastExits() {
		return numberOfPastExits;
	}
	public void setNumberOfPastExits(String numberOfPastExits) {
		this.numberOfPastExits = numberOfPastExits;
	}
	public Set<Integer> getAssosiatedFinders() {
		return assosiatedFinders;
	}
	public void setAssosiatedFinders(Set<Integer> assosiatedFinders) {
		this.assosiatedFinders = assosiatedFinders;
	}
	public Set<String> getInvestmentStage() {
		return investmentStage;
	}
	public void setInvestmentStage(Set<String> investmentStage) {
		this.investmentStage = investmentStage;
	}
	
}
