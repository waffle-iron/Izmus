package com.izmus.data.domain.startups;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="STARTUP_ADDITIONAL_DOCUMENTS")
public class StartupAdditionalDocument implements Serializable, Comparable<StartupAdditionalDocument> {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "DOCUMENT_ID")
	private Integer documentId;
	@Column(name = "DOCUMENT_NAME")
	private String documentName;
	@Column(name = "DOCUMENT")
	@JsonIgnore
	private byte[] document;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STARTUP_ID", nullable = false)
	@Fetch(value = FetchMode.JOIN)
	@JsonBackReference
	private Startup startup;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof StartupAdditionalDocument))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(StartupAdditionalDocument otherDocument) {
		return toString().compareTo(otherDocument.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"documentId\": " + getDocumentId() + ", "
				+ "\"documentName: \"" + getDocumentName() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getDocumentId() {
		return documentId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getDocumentName() {
		return documentName;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public byte[] getDocument() {
		return document;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setDocument(byte[] document) {
		this.document = document;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public Startup getStartup() {
		return startup;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setStartup(Startup startup) {
		this.startup = startup;
	}
	
}
