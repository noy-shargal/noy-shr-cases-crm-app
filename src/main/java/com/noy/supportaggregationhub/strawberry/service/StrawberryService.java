package com.noy.supportaggregationhub.strawberry.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noy.supportaggregationhub.aggregation.service.api.model.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class StrawberryService {

    private final ResourceLoader resourceLoader;

    private final ObjectMapper mapper;

    public APIResponse getStrawberryAPIData() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data/strawberry.json");
            try (InputStream inputStream = resource.getInputStream()) {

                APIResponse strawberryResponse = mapper.readValue(inputStream, APIResponse.class);
                // Process the deserialized BananaAPIResponse object or return it as needed
                return strawberryResponse;
            }
        } catch (IOException e) {
            // Handle any exceptions that occur during file reading
            e.printStackTrace();
            return null;
        }
    }
}
