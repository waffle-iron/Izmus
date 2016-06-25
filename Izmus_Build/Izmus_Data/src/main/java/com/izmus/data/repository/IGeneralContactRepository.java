package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.contacts.GeneralContact;

@Repository
public interface IGeneralContactRepository extends JpaRepository<GeneralContact, Integer> {
	GeneralContact findDistinctGeneralContactByContactId(Integer contactId);
}
