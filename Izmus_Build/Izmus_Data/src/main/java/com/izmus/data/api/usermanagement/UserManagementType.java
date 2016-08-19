package com.izmus.data.api.usermanagement;

import java.io.Serializable;
import java.util.TreeSet;

public class UserManagementType implements Serializable, Comparable<UserManagementType>{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private Boolean active;
	private Boolean disabled;
	private String title;
	private TreeSet<UserData> users;
	/*----------------------------------------------------------------------------------------------------*/
	public TreeSet<UserData> getUsers() {
		return users;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setUsers(TreeSet<UserData> users) {
		this.users = users;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Boolean getActive() {
		return active;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setActive(Boolean active) {
		this.active = active;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Boolean getDisabled() {
		return disabled;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getTitle() {
		return title;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setTitle(String title) {
		this.title = title;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "Title: " + getTitle();
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(UserManagementType otherManagementType) {
		return otherManagementType.toString().compareTo(toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof UserManagementType))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
}
