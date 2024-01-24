package com.kernel360.ecolife.entity;

import com.kernel360.base.BaseRawEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcernedProductDetail extends BaseRawEntity {

    @Column(name = "inspected_organization")
    private String inspectedOrganization;

    @Column(name = "issued_date")
    private LocalDate issuedDate;

    @Column(name = "upper_item")
    private String upperItem;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "renewed_type")
    private String renewedType;

    @Column(name = "safety_inspection_standard", length = Integer.MAX_VALUE)
    private String safetyInspectionStandard;

    @Column(name = "kid_protect_package")
    private String kidProtectPackage;

    @Column(name = "manufacture_nation")
    private String manufactureNation;

    @Column(name = "product_definition", length = Integer.MAX_VALUE)
    private String productDefinition;

    @Column(name = "manufacture")
    private String manufacture;
}
