package com.izmus.data.domain.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"ADMIN_FIRST_NAME","ADMIN_LAST_NAME","ENTITY_EMAIL"})}, 
		name = "ADMINISTRATORS")
public class Administrator extends SystemEntity{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Column(name = "ADMIN_FIRST_NAME")
	private String adminFirstName;
	@Column(name = "ADMIN_LAST_NAME")
	private String adminLastName;
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public String toString() {
		return "Administrator Name: " + getAdminFirstName() + " " + getAdminLastName() +  ""
				+ ", Administrator E-Mail: " + getEntityEmail();
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public String getAdminFirstName() {
		return adminFirstName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getAdminLastName() {
		return adminLastName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}
}
