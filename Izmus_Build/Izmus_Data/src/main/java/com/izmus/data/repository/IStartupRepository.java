package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.startups.Startup;

@Repository
public interface IStartupRepository extends JpaRepository<Startup, Integer> {
	Startup findDistinctStartupByStartupName(String startupName);
	Startup findDistinctStartupByStartupId(Integer startupId);
	List<Startup> findByStartupNameIgnoreCaseContaining(String starupName);
}
