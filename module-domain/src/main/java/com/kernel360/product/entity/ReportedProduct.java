package com.kernel360.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class ReportedProduct {
    @Id
    @Column(name = "est_no")
    private Long estNo;

    @Column(name = "mst_id")
    private Long productId;

    @Column(name = "prdt_nm")
    private String productName;

    @Column(name = "item")
    private String item;

    @Column(name = "comp_nm")
    private String companyName;

    @Column(name = "slfsfcfst_no")
    private String safetyReportNumber;

    @Column(name = "reg_date")
    private LocalDateTime registeredNumber;
}
