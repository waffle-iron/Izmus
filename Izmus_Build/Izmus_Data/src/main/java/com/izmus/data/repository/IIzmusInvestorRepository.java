package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izmus.data.domain.users.IzmusInvestor;

public interface IIzmusInvestorRepository extends JpaRepository<IzmusInvestor, Integer> {
	List<IzmusInvestor> findByEntityEmail(String entityEmail);
	IzmusInvestor findDistinctIzmusInvestorByEntityId(Integer entityId);
}
