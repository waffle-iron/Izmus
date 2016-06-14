package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.startups.FinancialIndicator;

@Repository
public interface IFinancialIndicatorRepository extends JpaRepository<FinancialIndicator, Integer> {
	List<FinancialIndicator> findFinancialIndicatorByStartupId(Integer startupId);
}
