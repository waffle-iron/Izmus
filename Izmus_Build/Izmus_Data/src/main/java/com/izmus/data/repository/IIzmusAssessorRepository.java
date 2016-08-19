package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izmus.data.domain.users.IzmusAssessor;

public interface IIzmusAssessorRepository extends JpaRepository<IzmusAssessor, Integer> {
	List<IzmusAssessor> findByEntityEmail(String entityEmail);
}
