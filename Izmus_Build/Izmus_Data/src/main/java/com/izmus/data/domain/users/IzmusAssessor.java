package com.izmus.data.domain.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"ASSESSOR_FIRST_NAME","ASSESSOR_LAST_NAME","ENTITY_EMAIL"})}, 
		name = "IZMUS_ASSESSORS")
public class IzmusAssessor extends SystemEntity{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Column(name = "ASSESSOR_FIRST_NAME")
	private String assessorFirstName;
	@Column(name = "ASSESSOR_LAST_NAME")
	private String assessorLastName;
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public String toString() {
		return "Assessor Name: " + getAssessorFirstName() + " " + getAssessorLastName() +  ""
				+ ", Assessor E-Mail: " + getEntityEmail();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getAssessorFirstName() {
		return assessorFirstName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setAssessorFirstName(String assessorFirstName) {
		this.assessorFirstName = assessorFirstName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getAssessorLastName() {
		return assessorLastName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setAssessorLastName(String assessorLastName) {
		this.assessorLastName = assessorLastName;
	}
}
