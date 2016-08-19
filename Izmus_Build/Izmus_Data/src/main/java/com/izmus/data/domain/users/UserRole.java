package com.izmus.data.domain.users;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="SECURITY_USER_ROLES")
public class UserRole implements Serializable, Comparable<UserRole> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ROLE_ID")
	private Integer roleId;
	@Column(name = "ROLE_NAME", unique=true)
	private String roleName;
	@Column(name = "PERMISSIONS")
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<Permission> permissions;
	@Column(name = "USERS")
	@ManyToMany(mappedBy = "userRoles",fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<User> users;
	/*----------------------------------------------------------------------------------------------------*/
	public Set<User> getUsers() {
		return users;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<Permission> getPermissions() {
		return permissions;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @return the id
	 */
	public Integer getRoleId() {
		return roleId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param id the id to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @return the role
	 */
	public String getRoleName() {
		return roleName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * @param role the role to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(UserRole otherUserRole) {
		return otherUserRole.toString().compareTo(toString());
	}

	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof UserRole))) {
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
		return "Role ID: " + getRoleId() + " Role Name: " + getRoleName();
	}
}
