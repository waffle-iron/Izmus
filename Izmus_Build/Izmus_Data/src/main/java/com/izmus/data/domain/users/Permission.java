package com.izmus.data.domain.users;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="SECURITY_PERMISSION")
public class Permission implements Serializable, Comparable<Permission>{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "PERMISSION_ID")
	private Integer permissionId;
	@Column(name = "PERMISSION_NAME", unique=true)
	private String permissionName;
	@Column(name = "USER_ROLES")
	@ManyToMany(mappedBy = "permissions")
	private Set<UserRole> userRoles;
	/*----------------------------------------------------------------------------------------------------*/
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
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
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(Permission otherPermission) {
		return otherPermission.toString().compareTo(toString());
	}

	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof Permission))) {
			return obj.toString().equals(this.toString());
		}
		return false;
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
}
