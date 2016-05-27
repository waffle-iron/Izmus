package com.izmus.data.domain.startups;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="STARTUP_CONTACTS")
public class StartupContact implements Serializable, Comparable<StartupContact> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "CONTACT_ID")
	private Integer contactId;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "OFFICE_PHONE")
	private String officePhone;
	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "POSITION")
	private String position;
	@Column(name = "LOGO", columnDefinition = "VARCHAR(5242880)")
	private String contactAvatar;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STARTUP_ID", nullable = true)
	@Fetch(value = FetchMode.JOIN)
	@JsonBackReference
	private Startup startup;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof StartupContact))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(StartupContact otherStartupContact) {
		return toString().compareTo(otherStartupContact.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"contactId\": " + getContactId() + ", "
				+ "\"contactFirstName\":\"" + getFirstName() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getContactId() {
		return contactId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getFirstName() {
		return firstName;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getLastName() {
		return lastName;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getMobilePhone() {
		return mobilePhone;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getEmail() {
		return email;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setEmail(String email) {
		this.email = email;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Startup getStartup() {
		return startup;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setStartup(Startup startup) {
		this.startup = startup;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getPosition() {
		return position;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setPosition(String position) {
		this.position = position;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getContactAvatar() {
		return contactAvatar;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setContactAvatar(String contactAvatar) {
		this.contactAvatar = contactAvatar;
	}
	
}
