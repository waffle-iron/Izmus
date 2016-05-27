package com.izmus.data.api.usermanagement;

import java.io.Serializable;
import java.util.Set;

public class UserData implements Serializable, Comparable<UserData>{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String userName;
	private String userType;
	private String lastLogin;
	private String userEmail;
	private Boolean enabled;
	private Boolean isUserMale;
	private String creationTime;
	private String userAvatar;
	private Set<UserRoleData> userRoles;
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(UserData otherUserData) {
		return otherUserData.toString().compareTo(toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "Title: " + getUserName() + " User Id: " + getUserId();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof UserData))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Set<UserRoleData> getUserRoles() {
		return userRoles;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getUserType() {
		return userType;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setUserRoles(Set<UserRoleData> userRoles) {
		this.userRoles = userRoles;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getUserId() {
		return userId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getUserName() {
		return userName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getLastLogin() {
		return lastLogin;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getUserEmail() {
		return userEmail;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Boolean getEnabled() {
		return enabled;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getCreationTime() {
		return creationTime;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Boolean getIsUserMale() {
		return isUserMale;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setIsUserMale(Boolean isUserMale) {
		this.isUserMale = isUserMale;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getUserAvatar() {
		return userAvatar;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	
}
