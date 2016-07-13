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
	
	Page<AvailableStartup> findBySectorIgnoreCaseOrderByStartupNameAsc(String sector,
			Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseOrderByStartupNameAsc(
			String startupName, String sector, Pageable pageable);

	Page<AvailableStartup> findAllByOrderByStartupNameAsc(Pageable pageable);
	
	Page<AvailableStartup> findByFundingStageIgnoreCaseOrderByStartupNameAsc(String fundingStage,
			Pageable pageable);
	
	Page<AvailableStartup> findByProductStageIgnoreCaseOrderByStartupNameAsc(String productStage,
			Pageable pageable);

	Page<AvailableStartup> findByFundingStageIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
			String fundingStage, String productStage, Pageable pageable);

	Page<AvailableStartup> findBySectorIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
			String sector, String productStage, Pageable pageable);

	Page<AvailableStartup> findBySectorIgnoreCaseAndFundingStageIgnoreCaseOrderByStartupNameAsc(
			String sector, String fundingStage, Pageable pageable);

	Page<AvailableStartup> findBySectorIgnoreCaseAndFundingStageIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
			String sector, String fundingStage, String productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndProductStageIgnoreCaseOrderByStartupNameAsc(
			String startupName, String productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseOrderByStartupNameAsc(
			String startupName, String fundingStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
			String startupName, String fundingStage, String productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
			String startupName, String sector, String productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseAndFundingStageIgnoreCaseOrderByStartupNameAsc(
			String startupName, String sector, String fundingStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseAndFundingStageIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
			String startupName, String sector, String fundingStage, String productStage, Pageable pageable);
	
}
