package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.contacts.FinderContact;

@Repository
public interface IFinderContactRepository extends JpaRepository<FinderContact, Integer> {
	FinderContact findDistinctFinderContactByContactId(Integer contactId);
}
