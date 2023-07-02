package com.noy.supportaggregationhub.banana.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noy.supportaggregationhub.aggregation.service.api.model.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class BananaService {

    private final ResourceLoader resourceLoader;

    private final ObjectMapper mapper;

    public APIResponse getBananaAPIData() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data/banana.json");
            try (InputStream inputStream = resource.getInputStream()) {

                APIResponse bananaAPIResponse = mapper.readValue(inputStream, APIResponse.class);
                // Process the deserialized BananaAPIResponse object or return it as needed
                return bananaAPIResponse;
            }
        } catch (IOException e) {
            // Handle any exceptions that occur during file reading
            e.printStackTrace();
            return null;
        }
    }

}
