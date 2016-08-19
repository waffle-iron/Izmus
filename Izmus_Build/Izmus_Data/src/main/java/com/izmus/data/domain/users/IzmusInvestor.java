package com.izmus.data.domain.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"INVESTOR_FIRST_NAME","INVESTOR_LAST_NAME","ENTITY_EMAIL"})}, 
		name = "IZMUS_INVESTORS")
public class IzmusInvestor extends SystemEntity {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Column(name = "INVESTOR_FIRST_NAME")
	private String investorFirstName;
	@Column(name = "INVESTOR_LAST_NAME")
	private String investorLastName;
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public String toString() {
		return "Investor Name: " + getInvestorFirstName() + " " + getInvestorLastName() +  ""
						+ ", Investor E-Mail: " + getEntityEmail();
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public String getInvestorFirstName() {
		return investorFirstName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setInvestorFirstName(String investorFirstName) {
		this.investorFirstName = investorFirstName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getInvestorLastName() {
		return investorLastName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setInvestorLastName(String investorLastName) {
		this.investorLastName = investorLastName;
	}
}
