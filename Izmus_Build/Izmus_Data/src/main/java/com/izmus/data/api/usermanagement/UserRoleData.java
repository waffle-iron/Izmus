package com.izmus.data.api.usermanagement;

import java.io.Serializable;
import java.util.Set;

public class UserRoleData implements Serializable, Comparable<UserRoleData>{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private String roleName;
	private Integer roleId;
	private Set<PermissionData> permissions;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof UserRoleData))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(UserRoleData otherUserRoleData) {
		return toString().compareTo(otherUserRoleData.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "Role ID: " + getRoleId() + " Role Name: " + getRoleName();
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public String getRoleName() {
		return roleName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getRoleId() {
		return roleId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<PermissionData> getPermissions() {
		return permissions;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setPermissions(Set<PermissionData> permissions) {
		this.permissions = permissions;
	}
	
}
