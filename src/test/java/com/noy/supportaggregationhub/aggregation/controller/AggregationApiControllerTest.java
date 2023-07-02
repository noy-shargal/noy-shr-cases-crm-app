package com.noy.supportaggregationhub.aggregation.controller;

import com.noy.supportaggregationhub.aggregation.jpa.model.AggregatedSupportCase;
import com.noy.supportaggregationhub.aggregation.service.AggregationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AggregationApiControllerTest {

    @Mock
    private AggregationService aggregationService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        AggregationApiController aggregationApiController = new AggregationApiController(aggregationService);
        mockMvc = MockMvcBuilders.standaloneSetup(aggregationApiController).build();
    }

    @Test
    public void testGetAllSupportCases() throws Exception {
        // Arrange
        List<AggregatedSupportCase> expectedCases = new ArrayList<>();
        when(aggregationService.getAllAggregatedCases()).thenReturn(expectedCases);

        // Act & Assert
        mockMvc.perform(get("/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testSearch() throws Exception {
        // Arrange
        String provider = "Test Provider";
        Integer errorCode = 123;
        String status = "Open";
        List<AggregatedSupportCase> expectedCases = new ArrayList<>();
        when(aggregationService.searchAllAggregatedCases(provider, errorCode, status)).thenReturn(expectedCases);

        // Act & Assert
        mockMvc.perform(get("/search")
                        .param("provider", provider)
                        .param("errorCode", String.valueOf(errorCode))
                        .param("status", status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testRefreshData() throws Exception {
        // Arrange
        List<AggregatedSupportCase> expectedCases = new ArrayList<>();
        when(aggregationService.refresh()).thenReturn(expectedCases);

        // Act & Assert
        mockMvc.perform(get("/refresh"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(content().json("[]"));
    }

}