package com.noy.supportaggregationhub.aggregation.controller;

import com.noy.supportaggregationhub.aggregation.jpa.model.AggregatedSupportCase;
import com.noy.supportaggregationhub.aggregation.service.AggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AggregationApiController {

    private final AggregationService aggregationService;

    @GetMapping("get-all")
    public List<AggregatedSupportCase> getAllSupportCases() {
        return aggregationService.getAllAggregatedCases();
    }

    @GetMapping("/search")
    public List<AggregatedSupportCase> search(
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) Integer errorCode,
            @RequestParam(required = false) String status
    ) {
        return aggregationService.searchAllAggregatedCases(provider, errorCode, status);
    }

    @GetMapping("/refresh")
    public List<AggregatedSupportCase> refreshData() {
        return aggregationService.refresh();
    }
}
