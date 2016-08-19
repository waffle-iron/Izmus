package com.izmus.data.api.usermanagement;

import java.io.Serializable;

public class PermissionData implements Serializable, Comparable<PermissionData>{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private Integer permissionId;
	private String permissionName;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof PermissionData))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(PermissionData otherPermissionData) {
		return toString().compareTo(otherPermissionData.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "Permission ID: " + getPermissionId() + " Permission Name: " + getPermissionName();
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getPermissionId() {
		return permissionId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getPermissionName() {
		return permissionName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
}
