package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izmus.data.domain.users.Administrator;

public interface IAdministratorRepository extends JpaRepository<Administrator, Integer> {
	List<Administrator> findByEntityEmail(String entityEmail);
}
