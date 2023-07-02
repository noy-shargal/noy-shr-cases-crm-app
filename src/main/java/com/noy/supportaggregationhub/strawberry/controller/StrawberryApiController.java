package com.noy.supportaggregationhub.strawberry.controller;

import com.noy.supportaggregationhub.aggregation.service.api.model.APIResponse;
import com.noy.supportaggregationhub.strawberry.service.StrawberryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StrawberryApiController {

    private final StrawberryService strawberryService;

    @GetMapping("/strawberry")
    public APIResponse getDataFromStrawberryAPI() {
        return strawberryService.getStrawberryAPIData();
    }

}
