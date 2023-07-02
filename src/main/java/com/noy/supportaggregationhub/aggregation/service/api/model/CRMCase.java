package com.noy.supportaggregationhub.aggregation.service.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CRMCase(

        @JsonProperty("Case ID")
        Long caseId,

        @JsonProperty("Customer ID")
        String customerId,

        @JsonProperty("Provider")
        String provider,

        @JsonProperty("CREATED_ERROR_CODE")
        Integer createdErrorCode,

        @JsonProperty("STATUS")
        String supportStatus,

        @JsonProperty("TICKET_CREATION_DATE")
        String ticketCreationDate,

        @JsonProperty("LAST_MODIFIED_DATE")
        String lastModifiedDate,

        @JsonProperty("PRODUCT_NAME")
        String productName
) {

}
