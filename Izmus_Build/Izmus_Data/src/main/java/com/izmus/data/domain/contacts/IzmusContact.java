package com.izmus.data.domain.contacts;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class IzmusContact implements Serializable, Comparable<IzmusContact> {
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
	@Column(name = "OTHER_PHONE")
	private String otherPhone;
	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "POSITION")
	private String position;
	@Column(name = "LOGO", columnDefinition = "VARCHAR(5242880)")
	private String contactAvatar;
	@Column(name = "COMPANY_AVATAR", columnDefinition = "VARCHAR(5242880)")
	private String companyAvatar;
	@Column(name = "COMPANY_NAME")
	private String companyName;
	@Column(name = "HOW_WE_MET")
	private String howWeMet;
	@Column(name = "FOCUS_AREAS")
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	private Set<String> focusAreas;
	@Column(name = "NOTES")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "izmusContact", fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SELECT)
	private Set<ContactNote> notes;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof IzmusContact))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(IzmusContact otherContact) {
		return toString().compareTo(otherContact.toString());
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
	/*----------------------------------------------------------------------------------------------------*/

	public String getCompanyAvatar() {
		return companyAvatar;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setCompanyAvatar(String companyAvatar) {
		this.companyAvatar = companyAvatar;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getCompanyName() {
		return companyName;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getOtherPhone() {
		return otherPhone;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Set<String> getFocusAreas() {
		return focusAreas;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setFocusAreas(Set<String> focusAreas) {
		this.focusAreas = focusAreas;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getHowWeMet() {
		return howWeMet;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setHowWeMet(String howWeMet) {
		this.howWeMet = howWeMet;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<ContactNote> getNotes() {
		return notes;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setNotes(Set<ContactNote> notes) {
		this.notes = notes;
	}
	
}
