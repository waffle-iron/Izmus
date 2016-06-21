package com.izmus.data.domain.users;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="SECURITY_USERS")
public class User implements Serializable, Comparable<User> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "USER_NAME", unique = true, nullable = false)
	private String userName;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "ENABLED", nullable=false)
	private Boolean enabled;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_TIME", nullable=false)
	private Date creationTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PASSWORD_CHANGE_DATE")
	private Date passwordChangeDate;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<UserRole> userRoles;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JsonBackReference
	private SystemEntity entity;
	@Column(name = "AVATAR", columnDefinition = "VARCHAR(5242880)")
	private String userAvatar;
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @return the userRoles
	 */
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param userRoles
	 *            the userRoles to set
	 */
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @return the id
	 */
	public Integer getUserId() {
		return userId;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param id
	 *            the id to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Date getCreationTime() {
		return creationTime;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(User otherUser) {
		return otherUser.toString().compareTo(toString());
	}

	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof User))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public SystemEntity getEntity() {
		return entity;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setEntity(SystemEntity entity) {
		this.entity = entity;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getUserAvatar() {
		return userAvatar;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"userId\": " + getUserId() + ","
				+ " \"userName\": \"" + getUserName() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Date getPasswordChangeDate() {
		return passwordChangeDate;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setPasswordChangeDate(Date passwordChangeDate) {
		this.passwordChangeDate = passwordChangeDate;
	}
	
}