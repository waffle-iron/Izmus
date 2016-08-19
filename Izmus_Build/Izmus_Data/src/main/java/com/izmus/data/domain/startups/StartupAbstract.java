package com.izmus.data.domain.startups;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class StartupAbstract implements Serializable, Comparable<StartupAbstract> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "STARTUP_ID")
	private Integer startupId;
	@Column(name = "STARTUP_NAME")
	private String startupName;
	@Column(name = "LOGO", columnDefinition = "VARCHAR(5242880)")
	private String logo;
	@Column(name = "MISCELLANEOUS", columnDefinition = "VARCHAR(5242880)")
	private String miscellaneous;
	@Column(name = "SITE")
	private String site;
	@Column(name = "FOUNDED")
	private String founded;
	@Column(name = "NUMBER_OF_EMPLOYEES")
	private String numberOfEmployees;
	@Column(name = "PRODUCT_STAGE")
	private String productStage;
	@Column(name = "FUNDING_STAGE")
	private String fundingStage;
	@Column(name = "SECTOR")
	private String sector;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof Startup))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(StartupAbstract otherStartup) {
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
	public String getLogo() {
		return logo;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setLogo(String logo) {
		this.logo = logo;
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

	public String getSector() {
		return sector;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setSector(String sector) {
		this.sector = sector;
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

	public String getFounded() {
		return founded;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setFounded(String founded) {
		this.founded = founded;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getNumberOfEmployees() {
		return numberOfEmployees;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setNumberOfEmployees(String numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getProductStage() {
		return productStage;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setProductStage(String productStage) {
		this.productStage = productStage;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getFundingStage() {
		return fundingStage;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setFundingStage(String fundingStage) {
		this.fundingStage = fundingStage;
	}
	
}
