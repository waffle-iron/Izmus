package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izmus.data.domain.users.IzmusFinder;

public interface IIzmusFinderRepository extends JpaRepository<IzmusFinder, Integer> {
	List<IzmusFinder> findByEntityEmail(String entityEmail);
	IzmusFinder findDistinctIzmusFinderByEntityId(Integer entityId);
}
