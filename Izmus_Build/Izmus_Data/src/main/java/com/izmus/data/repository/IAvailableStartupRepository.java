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
	
	Page<AvailableStartup> findByFundingStageIgnoreCaseContainingOrderByStartupNameAsc(String fundingStage,
			Pageable pageable);
	
	Page<AvailableStartup> findByProductStageIgnoreCaseContainingOrderByStartupNameAsc(String productStage,
			Pageable pageable);

	Page<AvailableStartup> findByFundingStageIgnoreCaseContainingAndProductStageIgnoreCaseContainingOrderByStartupNameAsc(
			String fundingStage, String productStage, Pageable pageable);

	Page<AvailableStartup> findBySectorIgnoreCaseContainingAndProductStageIgnoreCaseContainingOrderByStartupNameAsc(
			String sector, String productStage, Pageable pageable);

	Page<AvailableStartup> findBySectorIgnoreCaseContainingAndFundingStageIgnoreCaseContainingOrderByStartupNameAsc(
			String sector, String fundingStage, Pageable pageable);

	Page<AvailableStartup> findBySectorIgnoreCaseContainingAndFundingStageIgnoreCaseContainingAndProductStageIgnoreCaseContainingOrderByStartupNameAsc(
			String sector, String fundingStage, String productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndProductStageIgnoreCaseContainingOrderByStartupNameAsc(
			String startupName, String productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseContainingOrderByStartupNameAsc(
			String startupName, String fundingStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseContainingAndProductStageIgnoreCaseContainingOrderByStartupNameAsc(
			String startupName, String fundingStage, String productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseContainingAndProductStageIgnoreCaseContainingOrderByStartupNameAsc(
			String startupName, String sector, String productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseContainingAndFundingStageIgnoreCaseContainingOrderByStartupNameAsc(
			String startupName, String sector, String fundingStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseContainingAndFundingStageIgnoreCaseContainingAndProductStageIgnoreCaseContainingOrderByStartupNameAsc(
			String startupName, String sector, String fundingStage, String productStage, Pageable pageable);
	
}
