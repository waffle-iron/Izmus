package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.contacts.IzmusContact;

@Repository
public interface IIzmusContactRepository extends JpaRepository<IzmusContact, Integer> {
	IzmusContact findDistinctIzmusContactByContactId(Integer contactId);
}
