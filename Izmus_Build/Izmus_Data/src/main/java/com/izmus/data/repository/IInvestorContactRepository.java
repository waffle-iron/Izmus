package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.contacts.InvestorContact;

@Repository
public interface IInvestorContactRepository extends JpaRepository<InvestorContact, Integer> {
	InvestorContact findDistinctInvestorContactByContactId(Integer contactId);
}
