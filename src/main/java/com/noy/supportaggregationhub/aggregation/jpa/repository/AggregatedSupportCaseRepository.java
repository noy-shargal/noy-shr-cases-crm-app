package com.noy.supportaggregationhub.aggregation.jpa.repository;

import com.noy.supportaggregationhub.aggregation.jpa.model.AggregatedSupportCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface AggregatedSupportCaseRepository extends JpaRepository<AggregatedSupportCase, Long> {

    AggregatedSupportCase findByErrorCodeAndProvider(Integer errorCode, String provider);

    @Query("SELECT asc FROM AggregatedSupportCase asc " +
           "LEFT JOIN asc.cases cd " +
           "WHERE (:provider IS NULL OR asc.provider LIKE %:provider%) " +
           "AND (:errorCode IS NULL OR asc.errorCode = :errorCode) " +
           "AND (:status IS NULL OR cd.status LIKE %:status%)")
    List<AggregatedSupportCase> findByProviderAndErrorCodeAndStatus(
            String provider, Integer errorCode, String status);

}