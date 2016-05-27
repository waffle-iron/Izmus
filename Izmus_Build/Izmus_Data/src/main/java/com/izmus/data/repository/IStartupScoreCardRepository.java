package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.startups.StartupScoreCard;

@Repository
public interface IStartupScoreCardRepository extends JpaRepository<StartupScoreCard, Integer> {
}
