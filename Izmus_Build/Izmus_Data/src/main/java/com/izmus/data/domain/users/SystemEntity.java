package com.izmus.data.domain.users;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SystemEntity implements Serializable, Comparable<SystemEntity> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ENTITY_ID")
	private Integer entityId;
	@Column(name = "ENTITY_EMAIL")
	private String entityEmail;
	@Column(name = "IS_ENTITY_MALE")
	private Boolean isEntityMale;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="USER_ID")
	@Fetch(value = FetchMode.JOIN)
	private User user;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof SystemEntity))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(SystemEntity otherEntity) {
		return otherEntity.toString().compareTo(toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "Entity ID: " + getEntityId() + " User Name: " + getUser().getUserName();
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public User getUser() {
		return user;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setUser(User user) {
		this.user = user;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getEntityId() {
		return entityId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getEntityEmail() {
		return entityEmail;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setEntityEmail(String entityEmail) {
		this.entityEmail = entityEmail;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Boolean getIsEntityMale() {
		return isEntityMale;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setIsEntityMale(Boolean isEntityMale) {
		this.isEntityMale = isEntityMale;
	}
	
}
