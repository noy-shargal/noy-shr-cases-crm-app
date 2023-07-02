package com.noy.supportaggregationhub.aggregation.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "case_details")
public class CaseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "case_id")
    private Long caseId;

    @Column(name = "customer_id", length = 100)
    private String customerId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "product_name", length = 100)
    private String productName;

    @Column(name = "status", length = 100)
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aggregated_support_case_id")
    @JsonIgnore
    private AggregatedSupportCase aggregatedSupportCase;

}