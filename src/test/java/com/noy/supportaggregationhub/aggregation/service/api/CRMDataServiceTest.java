package com.noy.supportaggregationhub.aggregation.service.api;

import com.noy.supportaggregationhub.aggregation.jpa.model.APIAudit;
import com.noy.supportaggregationhub.aggregation.jpa.model.AggregatedSupportCase;
import com.noy.supportaggregationhub.aggregation.jpa.model.CaseDetails;
import com.noy.supportaggregationhub.aggregation.jpa.repository.APIAuditRepository;
import com.noy.supportaggregationhub.aggregation.jpa.repository.AggregatedSupportCaseRepository;
import com.noy.supportaggregationhub.aggregation.jpa.repository.CaseDetailsRepository;
import com.noy.supportaggregationhub.aggregation.service.api.model.CRMCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.util.*;

import static org.mockito.Mockito.*;

public class CRMDataServiceTest {

    @Mock
    WebClient webClient;

    @Mock
    private AggregatedSupportCaseRepository aggregatedSupportCaseRepository;

    @Mock
    private CaseDetailsRepository caseDetailsRepository;

    @Mock
    private APIAuditRepository aPIAuditRepository;

    private CRMDataService crmDataService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        crmDataService = new CRMDataService(webClient, aggregatedSupportCaseRepository, caseDetailsRepository, aPIAuditRepository);

    }

    @Test
    public void testAggregateCases() throws ParseException {
        // Prepare test data
        List<CRMCase> supportCases = new ArrayList<>();
        supportCases.add(new CRMCase(1L, "customer1", "1234", 100, "Open", "01/01/2023 10:00", "01/01/2023 11:00", "RED"));
        supportCases.add(new CRMCase(2L, "customer2", "11111", 200, "Closed", "01/02/2023 10:00", "01/02/2023 11:00", "GREEN"));
        supportCases.add(new CRMCase(3L, "customer3", "1234", 100, "Open", "01/03/2023 10:00", "01/03/2023 11:00", "BLUE"));

        // Call the method under test
        List<AggregatedSupportCase> result = crmDataService.aggregateCases(supportCases);

        // Assertions
        Assertions.assertEquals(2, result.size()); // Two unique aggregated support cases expected

        // Assert aggregated support case 1
        AggregatedSupportCase aggregatedSupportCase2 = result.get(0);
        Assertions.assertEquals(200, aggregatedSupportCase2.getErrorCode());
        Assertions.assertEquals("11111", aggregatedSupportCase2.getProvider());
        Assertions.assertEquals(1, aggregatedSupportCase2.getNoOfSupportCases());
        Assertions.assertEquals(new HashSet<>(Collections.singletonList("GREEN")), aggregatedSupportCase2.getProductsAffected());

        // Assert aggregated support case 2
        AggregatedSupportCase aggregatedSupportCase1 = result.get(1);
        Assertions.assertEquals(100, aggregatedSupportCase1.getErrorCode());
        Assertions.assertEquals("1234", aggregatedSupportCase1.getProvider());
        Assertions.assertEquals(2, aggregatedSupportCase1.getNoOfSupportCases());
        Assertions.assertEquals(new HashSet<>(Arrays.asList("RED", "BLUE")), aggregatedSupportCase1.getProductsAffected());

    }
}