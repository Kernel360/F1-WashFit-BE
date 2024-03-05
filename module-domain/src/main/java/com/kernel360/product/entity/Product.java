package com.kernel360.product.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_gen")
    @SequenceGenerator(name = "product_id_gen", sequenceName = "product_product_no_seq", allocationSize = 50)
    @Column(name = "product_no", nullable = false)
    private Long productNo;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "report_no")
    private String reportNumber;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "manufacture_nation")
    private String manufactureNation;

    @Column(name = "safety_status", nullable = false)
    private SafetyStatus safetyStatus = SafetyStatus.SAFE;

    @Column(name = "issued_date", nullable = false)
    private LocalDate issuedDate;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "img_src")
    private String imageSource;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @Column(name = "safety_inspection_standard", length = Integer.MAX_VALUE)
    private String safetyInspectionStandard;

    @Column(name = "upper_item")
    private String upperItem;

    @Column(name = "item")
    private String item;

    @Column(name = "propose", length = Integer.MAX_VALUE)
    private String propose;

    @Column(name = "weight")
    private String weight;

    @Column(name = "usage", length = Integer.MAX_VALUE)
    private String usage;

    @Column(name = "usage_precaution", length = Integer.MAX_VALUE)
    private String usagePrecaution;

    @Column(name = "first_aid", length = Integer.MAX_VALUE)
    private String firstAid;

    @Column(name = "main_substance", length = Integer.MAX_VALUE)
    private String mainSubstance;

    @Column(name = "allergic_substance", length = Integer.MAX_VALUE)
    private String allergicSubstance;

    @Column(name = "other_substance", length = Integer.MAX_VALUE)
    private String otherSubstance;

    @Column(name = "preservative", length = Integer.MAX_VALUE)
    private String preservative;
    /**
     * 계면활성제
     */
    @Column(name = "surfactant", length = Integer.MAX_VALUE)
    private String surfactant;
    /**
     * 형광증백제
     */
    @Column(name = "fluorescent_whitening", length = Integer.MAX_VALUE)
    private String fluorescentWhitening;

    @Column(name = "manufacture_type")
    private String manufactureType;

    @Column(name = "manufacture_method")
    private String manufactureMethod;

    @Column(name = "violation_info")
    private String violationInfo;


    private Product(
            String productName,
            String barcode,
            String imageSource,
            String reportNumber,
            String safetyStatus,
            Integer viewCount,
            String companyName,
            String productType,
            LocalDate issuedDate,
            String safetyInspectionStandard,
            String upperItem,
            String item,
            String propose,
            String weight,
            String usage,
            String usagePrecaution,
            String firstAid,
            String mainSubstance,
            String allergicSubstance,
            String otherSubstance,
            String preservative,
            String surfactant,
            String fluorescentWhitening,
            String manufactureType,
            String manufactureMethod,
            String manufactureNation,
            String violationInfo
    ) {
        this.productName = productName;
        this.barcode = barcode;
        this.imageSource = imageSource;
        this.reportNumber = reportNumber;
        this.safetyStatus = SafetyStatus.valueOf(safetyStatus);
        this.viewCount = viewCount;
        this.companyName = companyName;
        this.productType = productType;
        this.issuedDate = issuedDate;
        this.safetyInspectionStandard = safetyInspectionStandard;
        this.upperItem = upperItem;
        this.item = item;
        this.propose = propose;
        this.weight = weight;
        this.usage = usage;
        this.usagePrecaution = usagePrecaution;
        this.firstAid = firstAid;
        this.mainSubstance = mainSubstance;
        this.allergicSubstance = allergicSubstance;
        this.otherSubstance = otherSubstance;
        this.preservative = preservative;
        this.surfactant = surfactant;
        this.fluorescentWhitening = fluorescentWhitening;
        this.manufactureType = manufactureType;
        this.manufactureMethod = manufactureMethod;
        this.manufactureNation = manufactureNation;
        this.violationInfo = violationInfo;
    }

    private Product(Long productNo) {
        this.productNo = productNo;
    }

    public static Product of(String productName,
                             String barcode,
                             String imageSource,
                             String reportNumber,
                             String safetyStatus,
                             Integer viewCount,
                             String companyName,
                             String productType,
                             LocalDate issuedDate,
                             String safetyInspectionStandard,
                             String upperItem,
                             String item,
                             String propose,
                             String weight,
                             String usage,
                             String usagePrecaution,
                             String firstAid,
                             String mainSubstance,
                             String allergicSubstance,
                             String otherSubstance,
                             String preservative,
                             String surfactant,
                             String fluorescentWhitening,
                             String manufactureType,
                             String manufactureMethod,
                             String manufactureNation,
                             String violation_info
    ) {
        return new Product(productName, barcode, imageSource, reportNumber, safetyStatus, viewCount, companyName,
                productType, issuedDate, safetyInspectionStandard, upperItem,
                item, propose, weight, usage, usagePrecaution, firstAid, mainSubstance, allergicSubstance,
                otherSubstance, preservative, surfactant,
                fluorescentWhitening, manufactureType, manufactureMethod, manufactureNation, violation_info);
    }

    public static Product of(Long productNo) {
        return new Product(productNo);
    }

    public void updateDetail(
            String barcode,
            String imageSource,
            String reportNumber,
            String safetyStatus,
            LocalDate issuedDate,
            String safetyInspectionStandard,
            String upperItem,
            String item,
            String propose,
            String weight,
            String usage,
            String usagePrecaution,
            String firstAid,
            String mainSubstance,
            String allergicSubstance,
            String otherSubstance,
            String preservative,
            String surfactant,
            String fluorescentWhitening,
            String manufactureType,
            String manufactureMethod,
            String manufactureNation,
            String violationInfo
    ) {
        this.barcode = barcode;
        this.imageSource = imageSource;
        this.reportNumber = reportNumber;
        this.safetyStatus = SafetyStatus.valueOf(safetyStatus);
        this.issuedDate = issuedDate;
        this.safetyInspectionStandard = safetyInspectionStandard;
        this.upperItem = upperItem;
        this.item = item;
        this.propose = propose;
        this.weight = weight;
        this.usage = usage;
        this.usagePrecaution = usagePrecaution;
        this.firstAid = firstAid;
        this.mainSubstance = mainSubstance;
        this.allergicSubstance = allergicSubstance;
        this.otherSubstance = otherSubstance;
        this.preservative = preservative;
        this.surfactant = surfactant;
        this.fluorescentWhitening = fluorescentWhitening;
        this.manufactureType = manufactureType;
        this.manufactureMethod = manufactureMethod;
        this.manufactureNation = manufactureNation;
        this.violationInfo = violationInfo;
    }

    public void updateViolatedInfo(String violationInfo) {
        this.violationInfo = violationInfo;
        this.safetyStatus = SafetyStatus.DANGER;
    }

}