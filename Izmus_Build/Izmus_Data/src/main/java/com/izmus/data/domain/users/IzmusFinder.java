package com.izmus.data.domain.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"FINDER_FIRST_NAME","FINDER_LAST_NAME","ENTITY_EMAIL"})}, 
		name = "IZMUS_FINDERS")
public class IzmusFinder extends SystemEntity {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Column(name = "FINDER_FIRST_NAME")
	private String finderFirstName;
	@Column(name = "FINDER_LAST_NAME")
	private String finderLastName;
	@Column(name = "COMMISSION")
	private Double commission;
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public String toString() {
		return "Finder Name: " + getFinderFirstName() + " " + getFinderLastName() +  ""
						+ ", Finder E-Mail: " + getEntityEmail();
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public String getFinderFirstName() {
		return finderFirstName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setFinderFirstName(String finderFirstName) {
		this.finderFirstName = finderFirstName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getFinderLastName() {
		return finderLastName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setFinderLastName(String finderLastName) {
		this.finderLastName = finderLastName;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Double getCommission() {
		return commission;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setCommission(Double commission) {
		this.commission = commission;
	}
	
}
