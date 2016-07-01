package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.startups.AvailableStartup;

@Repository
public interface IAvailableStartupRepository extends JpaRepository<AvailableStartup, Integer> {
	AvailableStartup findDistinctAvailableStartupByStartupName(String startupName);
	AvailableStartup findDistinctAvailableStartupByStartupId(Integer startupId);
}
