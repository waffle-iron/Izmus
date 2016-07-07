package com.izmus.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.startups.AvailableStartup;

@Repository
public interface IAvailableStartupRepository extends JpaRepository<AvailableStartup, Integer> {
	AvailableStartup findDistinctAvailableStartupByStartupName(String startupName);

	AvailableStartup findDistinctAvailableStartupByStartupId(Integer startupId);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingOrderByStartupNameAsc(String startupName,
			Pageable pageable);
	
	Page<AvailableStartup> findBySectorIgnoreCaseContainingOrderByStartupNameAsc(String sector,
			Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseContainingOrderByStartupNameAsc(
			String startupName, String sector, Pageable pageable);

	Page<AvailableStartup> findAllByOrderByStartupNameAsc(Pageable pageable);
}
