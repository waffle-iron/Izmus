package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izmus.data.domain.startups.StartupContact;

public interface IStartupContactRepository extends JpaRepository<StartupContact, Integer> {

}
