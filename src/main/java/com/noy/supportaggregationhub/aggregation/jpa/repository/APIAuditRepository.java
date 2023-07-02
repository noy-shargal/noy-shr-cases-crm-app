package com.noy.supportaggregationhub.aggregation.jpa.repository;

import com.noy.supportaggregationhub.aggregation.jpa.model.APIAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIAuditRepository extends JpaRepository<APIAudit, Long> {

    APIAudit findFirstByOrderByApiInvocationTimeDesc();
}