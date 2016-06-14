package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.log.SystemLog;

@Repository
public interface ISystemLogRepository extends JpaRepository<SystemLog, Integer> {
	/**
	 * Find where Message is like message and LogClass is like className
	 * @param message
	 * @param className
	 * @return
	 */
	List<SystemLog> findByMessageIgnoreCaseContainingAndLogClassIgnoreCaseContainingOrderByLogTimeDesc(String message, String className);
	/**
	 * Find where Message is like message
	 * @param message
	 * @param className
	 * @return
	 */
	List<SystemLog> findByMessageIgnoreCaseContainingOrderByLogTimeDesc(String message);
	/**
	 * Find where userName like userName
	 * @param userName
	 * @return
	 */
	List<SystemLog> findByUserNameIgnoreCaseContainingOrderByLogTimeDesc(String userName);
}
