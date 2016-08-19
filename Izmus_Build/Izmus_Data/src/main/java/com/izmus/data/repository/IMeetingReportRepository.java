package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.meetings.MeetingReport;

@Repository
public interface IMeetingReportRepository extends JpaRepository<MeetingReport, Integer> {
}
