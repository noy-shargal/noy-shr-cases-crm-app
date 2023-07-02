package com.noy.supportaggregationhub.aggregation.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "aggregated_support_case", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"error_code", "provider"})
})
public class AggregatedSupportCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "error_code")
    private Integer errorCode;

    @Column(name = "provider", length = 100)
    private String provider;

    @Column(name = "no_of_support_cases")
    private Long noOfSupportCases;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "products_affected", length = 100)
    @CollectionTable(name = "support_case_products_affected", joinColumns = @JoinColumn(name = "case_id"))
    private Set<String> productsAffected = new LinkedHashSet<>();

    @OneToMany(mappedBy = "aggregatedSupportCase", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CaseDetails> cases = new ArrayList<>();

}