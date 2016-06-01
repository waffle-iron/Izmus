package com.izmus.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.startups.StartupAdditionalDocument;

@Repository
public interface IStartupAdditionalDocumentRepository extends JpaRepository<StartupAdditionalDocument, Integer> {
}
