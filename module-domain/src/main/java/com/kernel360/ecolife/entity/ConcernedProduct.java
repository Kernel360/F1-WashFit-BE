package com.kernel360.ecolife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "concerned_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcernedProduct extends ConcernedProductDetail {
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

    private ConcernedProduct(String productNo, String productName, String reportNumber, String item,
                             String companyName) {

        this.productNo = productNo;
        this.productName = productName;
        this.reportNumber = reportNumber;
        this.item = item;
        this.companyName = companyName;
    }

    public ConcernedProduct(String productNo, String productName, String reportNumber, String item, String companyName,
                            String inspectedOrganization, LocalDate issuedDate, String upperItem, String productType,
                            String renewedType,
                            String safetyInspectionStandard, String kidProtectPackage, String manufactureNation,
                            String productDefinition, String manufacture) {

        super(inspectedOrganization, issuedDate, upperItem, productType, renewedType, safetyInspectionStandard,
                kidProtectPackage, manufactureNation, productDefinition, manufacture);
        this.productNo = productNo;
        this.productName = productName;
        this.reportNumber = reportNumber;
        this.item = item;
        this.companyName = companyName;
    }


    public static ConcernedProduct of(String productNo, String productName, String reportNumber, String item,
                                      String companyName) {

        return new ConcernedProduct(productNo, productName, reportNumber, item, companyName);
    }

    public static ConcernedProduct of(String productNo, String productName, String reportNumber, String item,
                                      String companyName, String inspectedOrganization, LocalDate issuedDate,
                                      String upperItem, String productType, String renewedType,
                                      String safetyInspectionStandard, String kidProtectPackage,
                                      String manufactureNation, String productDefinition, String manufacture) {

        return new ConcernedProduct(productNo, productName, reportNumber, item, companyName, inspectedOrganization,
                issuedDate, upperItem, productType, renewedType, safetyInspectionStandard, kidProtectPackage,
                manufactureNation, productDefinition, manufacture);

    }

}