package com.izmus.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.izmus.data.domain.startups.StartupAdditionalDocument;

@Repository
public interface IStartupAdditionalDocumentRepository extends JpaRepository<StartupAdditionalDocument, Integer> {
	StartupAdditionalDocument findDistinctStartupAdditionalDocumentByDocumentId(Integer documentId);
	@Query("SELECT d FROM StartupAdditionalDocument d WHERE d.documentId = :documentId")
    public List<StartupAdditionalDocument> selectDocumentsWithId(@Param("documentId") Integer documentId);
}
