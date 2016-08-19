package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.startups.StartupAbstract;

@Repository
public interface IStartupAbstractRepository extends JpaRepository<StartupAbstract, Integer> {
	StartupAbstract findDistinctStartupAbstractByStartupId(Integer startupId);
	StartupAbstract findDistinctStartupAbstractByStartupName(String startupName);


	Page<StartupAbstract> findByStartupNameIgnoreCaseContainingOrderByStartupNameAsc(String startupName,
			Pageable pageable);
	
	Page<StartupAbstract> findBySectorIgnoreCaseInOrderByStartupNameAsc(List<String> sector,
			Pageable pageable);

	Page<StartupAbstract> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> sector, Pageable pageable);

	Page<StartupAbstract> findAllByOrderByStartupNameAsc(Pageable pageable);
	
	Page<StartupAbstract> findByFundingStageIgnoreCaseInOrderByStartupNameAsc(List<String> fundingStage,
			Pageable pageable);
	
	Page<StartupAbstract> findByProductStageIgnoreCaseInOrderByStartupNameAsc(List<String> productStage,
			Pageable pageable);

	Page<StartupAbstract> findByFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			List<String> fundingStage, List<String> productStage, Pageable pageable);

	Page<StartupAbstract> findBySectorIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			List<String> sector, List<String> productStage, Pageable pageable);

	Page<StartupAbstract> findBySectorIgnoreCaseInAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
			List<String> sector, List<String> fundingStage, Pageable pageable);

	Page<StartupAbstract> findBySectorIgnoreCaseInAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			List<String> sector, List<String> fundingStage, List<String> productStage, Pageable pageable);

	Page<StartupAbstract> findByStartupNameIgnoreCaseContainingAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> productStage, Pageable pageable);

	Page<StartupAbstract> findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> fundingStage, Pageable pageable);

	Page<StartupAbstract> findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> fundingStage, List<String> productStage, Pageable pageable);

	Page<StartupAbstract> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> sector, List<String> productStage, Pageable pageable);

	Page<StartupAbstract> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> sector, List<String> fundingStage, Pageable pageable);

	Page<StartupAbstract> findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
			String startupName, List<String> sector, List<String> fundingStage, List<String> productStage, Pageable pageable);
}
