package com.noy.supportaggregationhub.aggregation.service.api.model;

import java.util.List;

public record APIResponse(
        List<CRMCase> data
) {
}
