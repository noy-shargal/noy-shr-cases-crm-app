package com.noy.supportaggregationhub.aggregation.service.api;

import com.noy.supportaggregationhub.aggregation.jpa.model.APIAudit;
import com.noy.supportaggregationhub.aggregation.jpa.model.AggregatedSupportCase;
import com.noy.supportaggregationhub.aggregation.jpa.model.CaseDetails;
import com.noy.supportaggregationhub.aggregation.jpa.repository.APIAuditRepository;
import com.noy.supportaggregationhub.aggregation.jpa.repository.AggregatedSupportCaseRepository;
import com.noy.supportaggregationhub.aggregation.jpa.repository.CaseDetailsRepository;
import com.noy.supportaggregationhub.aggregation.service.api.model.APIResponse;
import com.noy.supportaggregationhub.aggregation.service.api.model.AggregationKey;
import com.noy.supportaggregationhub.aggregation.service.api.model.CRMCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class CRMDataService {

    private final WebClient webClient;
    private final AggregatedSupportCaseRepository aggregatedSupportCaseRepository;
    private final CaseDetailsRepository caseDetailsRepository;
    private final APIAuditRepository aPIAuditRepository;

    @Value("${crm.api.banana.url}")
    public String bananaApiUrl;
    @Value("${crm.api.strawberry.url}")
    public String strawberryApiUrl;
    @Value("${crm.api.frequency}")
    public String callFrequency;

    @Transactional
    @Scheduled(fixedDelayString = "${crm.api.frequency}") // Executes with a fixed delay of 4 hours
    public void getDataFromCRMApis() throws ParseException {
        List<CRMCase> caseList = new ArrayList<>();
        APIResponse bananaResponse = webClient.get()
                .uri(bananaApiUrl)
                .retrieve()
                .bodyToMono(APIResponse.class)
                .block();
        if (bananaResponse != null && bananaResponse.data() != null) {
            caseList.addAll(bananaResponse.data());
        }

        APIResponse strawberryResponse = webClient.get()
                .uri(strawberryApiUrl)
                .retrieve()
                .bodyToMono(APIResponse.class)
                .block();

        if (strawberryResponse != null && strawberryResponse.data() != null) {
            caseList.addAll(strawberryResponse.data());
        }

        //delete everything to have a complete refresh
        aggregatedSupportCaseRepository.deleteAll();
        aggregatedSupportCaseRepository.flush();

        List<AggregatedSupportCase> aggregatedSupportCases = aggregateCases(caseList);
        saveAggregatedData(aggregatedSupportCases);

    }

    public void saveAggregatedData(List<AggregatedSupportCase> aggregatedSupportCases) {
        aggregatedSupportCaseRepository.saveAll(aggregatedSupportCases);

        APIAudit audit = new APIAudit();
        audit.setApiInvocationTime(Date.from(Instant.now()));
        aPIAuditRepository.save(audit);
    }

    public List<AggregatedSupportCase> aggregateCases(List<CRMCase> supportCases) throws ParseException {

        Map<AggregationKey, AggregatedSupportCase> groupedData = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        for (CRMCase supportCase : supportCases) {
            int errorCode = supportCase.createdErrorCode();
            String provider = supportCase.provider();
            AggregationKey groupKey = new AggregationKey(errorCode, provider);

            if (!groupedData.containsKey(groupKey)) {

                AggregatedSupportCase aggregatedSupportCase = new AggregatedSupportCase();
                aggregatedSupportCase.setErrorCode(errorCode);
                aggregatedSupportCase.setProvider(provider);

                groupedData.put(groupKey, aggregatedSupportCase);
            }

            AggregatedSupportCase aggregatedSupportCase = groupedData.get(groupKey);
            Set<String> productsAffected = aggregatedSupportCase.getProductsAffected();
            String productName = supportCase.productName();
            productsAffected.add(productName);

            CaseDetails caseDetails = new CaseDetails();
            caseDetails.setCaseId(supportCase.caseId());
            caseDetails.setCustomerId(supportCase.customerId());
            caseDetails.setProductName(supportCase.productName());
            caseDetails.setStatus(supportCase.supportStatus());
            caseDetails.setCreationDate(dateFormat.parse(supportCase.ticketCreationDate()));
            caseDetails.setModifiedDate(dateFormat.parse(supportCase.lastModifiedDate()));
            caseDetails.setAggregatedSupportCase(aggregatedSupportCase);

            List<CaseDetails> cases = aggregatedSupportCase.getCases();
            cases.add(caseDetails);
            aggregatedSupportCase.setNoOfSupportCases((long) cases.size());
        }

        List<AggregatedSupportCase> aggregatedSupportCaseList = new ArrayList<>(groupedData.values());
        return aggregatedSupportCaseList;
    }
}
