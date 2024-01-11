package com.kernel360.ecolife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportedProduct extends ReportedProductDetails {

    @EmbeddedId
    @Column(name = "composite_id")
    private ReportedProductId id;

    @Column(name = "prdt_nm")
    private String productName;

    @Column(name = "slfsfcfst_no")
    private String safetyReportNumber;

    @Column(name = "item")
    private String item;

    @Column(name = "reg_date")
    private LocalDateTime registeredDate;

    @Column(name = "comp_nm")
    private String companyName;


    private ReportedProduct(ReportedProductId reportedProductId,
                            String productName,
                            String safetyReportNumber,
                            String item,
                            LocalDateTime registeredDate,
                            String companyName
    ) {
        this.id = reportedProductId;
        this.productName = productName;
        this.safetyReportNumber = safetyReportNumber;
        this.item = item;
        this.registeredDate = registeredDate;
        this.companyName = companyName;
    }

    private ReportedProduct(ReportedProductId reportedProductId,
                            String productName,
                            String safetyReportNumber,
                            String item,
                            LocalDateTime registeredDate,
                            String companyName,
                            String inspectedOrganization,
                            String productType,
                            LocalDateTime issuedDate,
                            String isRenewed,
                            String safeStandard,
                            LocalDateTime expireDate,
                            String upperItem,
                            String kidProtectPackage,
                            String productDefinition,
                            String productPropose,
                            String distributeLimit,
                            String weightAndBulk,
                            String icePoint,
                            String standardUseQuantity,
                            String useMethod,
                            String usageAttentionReport,
                            String firstAid,
                            String mainSubstance,
                            String preservative,
                            String allergicSubstance,
                            String Surfactant,
                            String otherSubstance,
                            String FluorescentWhiteningAgent,
                            String manufacture,
                            String manufactureMethod,
                            String manufactureNation,
                            String companyAddress,
                            String companyTel,
                            String importedCompanyName,
                            String importedCompanyAddress,
                            String importedCompanyTel,
                            String vendorName,
                            String vendorCompanyAddress,
                            String vendorCompanyTel) {
        super(inspectedOrganization, productType, issuedDate, isRenewed, safeStandard,
                expireDate, upperItem, kidProtectPackage, productDefinition, productPropose,
                distributeLimit, weightAndBulk, icePoint, standardUseQuantity, useMethod,
                usageAttentionReport, firstAid, mainSubstance, preservative, allergicSubstance,
                Surfactant, otherSubstance, FluorescentWhiteningAgent, manufacture, manufactureMethod,
                manufactureNation, companyAddress, companyTel, importedCompanyName, importedCompanyAddress,
                importedCompanyTel, vendorName, vendorCompanyAddress, vendorCompanyTel);
        this.id = reportedProductId;
        this.productName = productName;
        this.safetyReportNumber = safetyReportNumber;
        this.item = item;
        this.registeredDate = registeredDate;
        this.companyName = companyName;
    }


    public static ReportedProduct of(ReportedProductId reportedProductId,
                                     String productName,
                                     String safetyReportNumber,
                                     String item,
                                     LocalDateTime registeredDate,
                                     String companyName) {
        return new ReportedProduct(
                reportedProductId,
                productName,
                safetyReportNumber,
                item,
                registeredDate,
                companyName
        );
    }

    public static ReportedProduct of(ReportedProductId reportedProductId,
                                     String productName,
                                     String safetyReportNumber,
                                     String item,
                                     LocalDateTime registeredDate,
                                     String companyName,
                                     String inspectedOrganization,
                                     String productType,
                                     LocalDateTime issuedDate,
                                     String isRenewed,
                                     String safeStandard,
                                     LocalDateTime expireDate,
                                     String upperItem,
                                     String kidProtectPackage,
                                     String productDefinition,
                                     String productPropose,
                                     String distributeLimit,
                                     String weightAndBulk,
                                     String icePoint,
                                     String standardUseQuantity,
                                     String useMethod,
                                     String usageAttentionReport,
                                     String firstAid,
                                     String mainSubstance,
                                     String preservative,
                                     String allergicSubstance,
                                     String Surfactant,
                                     String otherSubstance,
                                     String FluorescentWhiteningAgent,
                                     String manufacture,
                                     String manufactureMethod,
                                     String manufactureNation,
                                     String companyAddress,
                                     String companyTel,
                                     String importedCompanyName,
                                     String importedCompanyAddress,
                                     String importedCompanyTel,
                                     String vendorName,
                                     String vendorCompanyAddress,
                                     String vendorCompanyTel) {
        return new ReportedProduct(reportedProductId, productName, safetyReportNumber, item, registeredDate,
                companyName, inspectedOrganization, productType, issuedDate, isRenewed, safeStandard,
                expireDate, upperItem, kidProtectPackage, productDefinition, productPropose,
                distributeLimit, weightAndBulk, icePoint, standardUseQuantity, useMethod,
                usageAttentionReport, firstAid, mainSubstance, preservative, allergicSubstance,
                Surfactant, otherSubstance, FluorescentWhiteningAgent, manufacture, manufactureMethod,
                manufactureNation, companyAddress, companyTel, importedCompanyName, importedCompanyAddress,
                importedCompanyTel, vendorName, vendorCompanyAddress, vendorCompanyTel);
    }

}
