package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.log.SystemLog;

@Repository
public interface ISystemLogRepository extends JpaRepository<SystemLog, Integer> {
	List<SystemLog> findByMessageIgnoreCaseContainingAndLogClassIgnoreCaseContainingOrderByLogTimeDesc(String message, String className);
}
