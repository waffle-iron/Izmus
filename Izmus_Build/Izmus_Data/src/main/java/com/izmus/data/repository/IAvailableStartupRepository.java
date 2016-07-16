package com.izmus.data.repository;

import java.util.List;

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
	
	Page<AvailableStartup> findBySectorIgnoreCaseInOrderByStartupNameAsc(List<String> sector,
			Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> sector, Pageable pageable);

	Page<AvailableStartup> findAllByOrderByStartupNameAsc(Pageable pageable);
	
	Page<AvailableStartup> findByFundingStageIgnoreCaseInOrderByStartupNameAsc(List<String> fundingStage,
			Pageable pageable);
	
	Page<AvailableStartup> findByProductStageIgnoreCaseInOrderByStartupNameAsc(List<String> productStage,
			Pageable pageable);

	Page<AvailableStartup> findByFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			List<String> fundingStage, List<String> productStage, Pageable pageable);

	Page<AvailableStartup> findBySectorIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			List<String> sector, List<String> productStage, Pageable pageable);

	Page<AvailableStartup> findBySectorIgnoreCaseInAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
			List<String> sector, List<String> fundingStage, Pageable pageable);

	Page<AvailableStartup> findBySectorIgnoreCaseInAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			List<String> sector, List<String> fundingStage, List<String> productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> fundingStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> fundingStage, List<String> productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> sector, List<String> productStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> sector, List<String> fundingStage, Pageable pageable);

	Page<AvailableStartup> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> sector, List<String> fundingStage, List<String> productStage, Pageable pageable);
	
}
