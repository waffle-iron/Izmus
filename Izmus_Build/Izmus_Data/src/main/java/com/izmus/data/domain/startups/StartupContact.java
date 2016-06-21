package com.izmus.data.domain.startups;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.izmus.data.domain.contacts.IzmusContact;

@Entity
@Table(name="STARTUP_CONTACTS")
public class StartupContact extends IzmusContact {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STARTUP_ID", nullable = true)
	@Fetch(value = FetchMode.JOIN)
	@JsonBackReference
	private Startup startup;
	/*----------------------------------------------------------------------------------------------------*/

	public Startup getStartup() {
		return startup;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setStartup(Startup startup) {
		this.startup = startup;
	}
}
