package com.noy.supportaggregationhub.aggregation.jpa.repository;

import com.noy.supportaggregationhub.aggregation.jpa.model.CaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaseDetailsRepository extends JpaRepository<CaseDetails, Long> {

    Optional<CaseDetails> findByCaseIdAndAggregatedSupportCase_Provider(Long caseId, String provider);

}