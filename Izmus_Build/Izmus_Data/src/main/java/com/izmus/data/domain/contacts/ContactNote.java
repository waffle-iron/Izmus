package com.izmus.data.domain.contacts;

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

@Entity
@Table(name="CONTACT_NOTES")
public class ContactNote implements Serializable, Comparable<ContactNote>{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "NOTE_ID")
	private Integer noteId;
	@Column(name = "NOTE", columnDefinition = "VARCHAR(5242880)")
	private String note;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CONTACT_ID", nullable = false)
	@Fetch(value = FetchMode.SELECT)
	@JsonBackReference
	private IzmusContact izmusContact;
	/*----------------------------------------------------------------------------------------------------*/
	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof IzmusContact))) {
			return obj.toString().equals(this.toString());
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@Override
	public int compareTo(ContactNote otherNote) {
		return toString().compareTo(otherNote.toString());
	}
	/*----------------------------------------------------------------------------------------------------*/
	public int hashCode() {
		String thisToString = toString();
		return thisToString.hashCode();
	}
	/*----------------------------------------------------------------------------------------------------*/
	public String toString() {
		return "{\"noteId\": " + getNoteId() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------*/
	public Integer getNoteId() {
		return noteId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public String getNote() {
		return note;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setNote(String note) {
		this.note = note;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public IzmusContact getIzmusContact() {
		return izmusContact;
	}
	/*----------------------------------------------------------------------------------------------------*/

	public void setIzmusContact(IzmusContact izmusContact) {
		this.izmusContact = izmusContact;
	}
	
}
