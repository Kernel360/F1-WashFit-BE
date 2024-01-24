package com.kernel360.ecolife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "concerned_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcernedProduct {
    @Id
    @Column(name = "prdt_no", nullable = false)
    private String productNo;

    @Column(name = "prdt_name", nullable = false)
    private String productName;

    @Column(name = "slfsfcfst_no")
    private String reportNumber;

    @Column(name = "item")
    private String item;

    @Column(name = "comp_nm")
    private String companyName;

}