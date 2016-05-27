package com.izmus.data.domain.startups;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="STARTUP_GROUPS")
public class StartupGroup implements Serializable, Comparable<StartupGroup> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "GROUP_ID")
	private Integer groupId;
	@Column(name = "GROUPS_NAME")
	private String groupName;
	@OneToMany(mappedBy = "startupGroup", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.JOIN)
	private Set<Startup> startups;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof StartupGroup))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(StartupGroup otherStartup) {
		return toString().compareTo(otherStartup.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"groupId\": " + getGroupId() + ", "
				+ "\"groupName: \"" + getGroupName() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getGroupId() {
		return groupId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String getGroupName() {
		return groupName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public Set<Startup> getStartups() {
		return startups;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setStartups(Set<Startup> startups) {
		this.startups = startups;
	}
}
