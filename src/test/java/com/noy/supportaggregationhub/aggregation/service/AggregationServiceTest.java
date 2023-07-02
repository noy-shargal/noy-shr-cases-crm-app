package com.noy.supportaggregationhub.aggregation.service;

import com.noy.supportaggregationhub.aggregation.jpa.model.APIAudit;
import com.noy.supportaggregationhub.aggregation.jpa.model.AggregatedSupportCase;
import com.noy.supportaggregationhub.aggregation.jpa.repository.APIAuditRepository;
import com.noy.supportaggregationhub.aggregation.jpa.repository.AggregatedSupportCaseRepository;
import com.noy.supportaggregationhub.aggregation.service.api.CRMDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class AggregationServiceTest {

    @Mock
    private CRMDataService crmDataService;

    @Mock
    private AggregatedSupportCaseRepository aggregatedSupportCaseRepository;

    @Mock
    private APIAuditRepository aPIAuditRepository;

    private AggregationService aggregationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        aggregationService = new AggregationService(crmDataService, aggregatedSupportCaseRepository, aPIAuditRepository);
        ReflectionTestUtils.setField(aggregationService, "refreshTime", 15);
    }

    @Test
    public void testGetAllAggregatedCases() {
        // Arrange
        List<AggregatedSupportCase> expectedCases = new ArrayList<>();
        when(aggregatedSupportCaseRepository.findAll()).thenReturn(expectedCases);

        // Act
        List<AggregatedSupportCase> actualCases = aggregationService.getAllAggregatedCases();

        // Assert
        assertEquals(expectedCases, actualCases);
        verify(aggregatedSupportCaseRepository).findAll();
    }

    @Test
    public void testSearchAllAggregatedCases() {
        // Arrange
        String provider = "Test Provider";
        Integer errorCode = 123;
        String caseStatus = "Open";
        List<AggregatedSupportCase> expectedCases = new ArrayList<>();
        when(aggregatedSupportCaseRepository.findByProviderAndErrorCodeAndStatus(provider, errorCode, caseStatus)).thenReturn(expectedCases);

        // Act
        List<AggregatedSupportCase> actualCases = aggregationService.searchAllAggregatedCases(provider, errorCode, caseStatus);

        // Assert
        assertEquals(expectedCases, actualCases);
        verify(aggregatedSupportCaseRepository).findByProviderAndErrorCodeAndStatus(provider, errorCode, caseStatus);
    }

    @Test
    public void testRefreshWithNoData() throws ParseException {
        // Arrange
        when(aPIAuditRepository.findFirstByOrderByApiInvocationTimeDesc()).thenReturn(null);

        // Act
        List<AggregatedSupportCase> actualCases = aggregationService.refresh();

        // Assert
        assertEquals(0, actualCases.size());
        verify(aPIAuditRepository).findFirstByOrderByApiInvocationTimeDesc();
        verify(aggregatedSupportCaseRepository, atMostOnce()).findAll();
        verify(crmDataService, never()).getDataFromCRMApis();
    }

    @Test
    public void testRefreshWithDataOlderThan15Minutes() throws ParseException {
        // Arrange
        APIAudit lastAudit = new APIAudit();
        LocalDateTime lastInvocationTime = LocalDateTime.now().minusMinutes(20);
        lastAudit.setApiInvocationTime(Date.from(lastInvocationTime.atZone(ZoneId.systemDefault()).toInstant()));
        when(aPIAuditRepository.findFirstByOrderByApiInvocationTimeDesc()).thenReturn(lastAudit);
        when(aggregatedSupportCaseRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<AggregatedSupportCase> actualCases = aggregationService.refresh();

        // Assert
        assertEquals(0, actualCases.size());
        verify(aPIAuditRepository).findFirstByOrderByApiInvocationTimeDesc();
        verify(aggregatedSupportCaseRepository).findAll();
        verify(crmDataService).getDataFromCRMApis();
    }

    @Test
    public void testRefreshWithDataWithin15Minutes() throws ParseException {
        // Arrange
        APIAudit lastAudit = new APIAudit();
        LocalDateTime lastInvocationTime = LocalDateTime.now().minusMinutes(10);
        lastAudit.setApiInvocationTime(Date.from(lastInvocationTime.atZone(ZoneId.systemDefault()).toInstant()));
        when(aPIAuditRepository.findFirstByOrderByApiInvocationTimeDesc()).thenReturn(lastAudit);
        when(aggregatedSupportCaseRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<AggregatedSupportCase> actualCases = aggregationService.refresh();

        // Assert
        assertEquals(0, actualCases.size());
        verify(aPIAuditRepository).findFirstByOrderByApiInvocationTimeDesc();
        verify(aggregatedSupportCaseRepository).findAll();
        verify(crmDataService, never()).getDataFromCRMApis();
    }
}