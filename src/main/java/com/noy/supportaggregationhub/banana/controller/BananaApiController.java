package com.noy.supportaggregationhub.banana.controller;

import com.noy.supportaggregationhub.banana.service.BananaService;
import com.noy.supportaggregationhub.aggregation.service.api.model.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BananaApiController {

    private final BananaService bananaService;

    @GetMapping("/banana")
    public APIResponse getDataFromBananaAPI() {
        return bananaService.getBananaAPIData();
    }
}
