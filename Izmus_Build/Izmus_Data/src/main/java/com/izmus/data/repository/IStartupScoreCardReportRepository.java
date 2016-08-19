package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.startups.ScoreCardReport;

@Repository
public interface IStartupScoreCardReportRepository extends JpaRepository<ScoreCardReport, Integer> {
}
