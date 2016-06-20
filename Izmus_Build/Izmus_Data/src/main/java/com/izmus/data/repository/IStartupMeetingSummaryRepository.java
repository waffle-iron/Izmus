package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.startups.StartupMeetingSummaryReport;

@Repository
public interface IStartupMeetingSummaryRepository extends JpaRepository<StartupMeetingSummaryReport, Integer> {
}
