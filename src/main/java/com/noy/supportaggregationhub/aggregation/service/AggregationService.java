package com.noy.supportaggregationhub.aggregation.service;

import com.noy.supportaggregationhub.aggregation.jpa.model.APIAudit;
import com.noy.supportaggregationhub.aggregation.jpa.model.AggregatedSupportCase;
import com.noy.supportaggregationhub.aggregation.jpa.repository.APIAuditRepository;
import com.noy.supportaggregationhub.aggregation.jpa.repository.AggregatedSupportCaseRepository;
import com.noy.supportaggregationhub.aggregation.service.api.CRMDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AggregationService {

    private final CRMDataService crmDataService;
    private final AggregatedSupportCaseRepository aggregatedSupportCaseRepository;
    private final APIAuditRepository aPIAuditRepository;

    @Value("${crm.api.refresh.time}")
    public int refreshTime;

    public List<AggregatedSupportCase> getAllAggregatedCases() {
        return aggregatedSupportCaseRepository.findAll();
    }

    public List<AggregatedSupportCase> searchAllAggregatedCases(String provider, Integer errorCode, String caseStatus) {
        return aggregatedSupportCaseRepository.findByProviderAndErrorCodeAndStatus(provider, errorCode, caseStatus);
    }

    public List<AggregatedSupportCase> refresh() {

        APIAudit lastAudit = aPIAuditRepository.findFirstByOrderByApiInvocationTimeDesc();

        if (lastAudit != null) {
            LocalDateTime lastInvocationTime = lastAudit.getApiInvocationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime currentTime = LocalDateTime.now();

            Duration duration = Duration.between(lastInvocationTime, currentTime);
            if (duration.toMinutes() > refreshTime) {
                try {
                    crmDataService.getDataFromCRMApis();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return aggregatedSupportCaseRepository.findAll();
    }
}
