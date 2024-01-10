package com.kernel360.ecolife.entity;

import com.kernel360.base.BaseRawEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportedProductDetails extends BaseRawEntity {
    @Column(name = "inspct_org")
    private String inspectedOrganization;
    @Column(name = "prdt_type")
    private String productType;
    @Column(name = "issu_date")
    private LocalDateTime issuedDate;
    @Column(name = "renew_yn")
    private String isRenewed;
    @Column(name = "safe_sd")
    private String safeStandard;
    @Column(name = "expr_date")
    private LocalDateTime expireDate;
    @Column(name = "upper_item")
    private String upperItem;
    @Column(name = "kid_prt_pkg")
    private String kidProtectPackage; // 명명 변경 필요
    @Column(name = "df")
    private String productDefinition;
    @Column(name = "propos")
    private String productPropose;
    @Column(name = "dtrb_lmt")
    private String distributeLimit;
    @Column(name = "wt_bulk")
    private String weightAndBulk;
    @Column(name = "icepnt")
    private String icePoint;
    @Column(name = "stddusqy")
    private String standardUseQuantity;
    @Column(name = "usmtd")
    private String useMethod;
    @Column(name = "us_atnrpt")
    private String usageAttentionReport;
    @Column(name = "frsaid")
    private String firstAid;
    @Column(name = "sum1")
    private String mainSubstance;
    @Column(name = "sum2")
    private String preservative;
    @Column(name = "sum3")
    private String allergicSubstance;
    @Column(name = "sum4")
    private String Surfactant;
    @Column(name = "sum5")
    private String otherSubstance;
    @Column(name = "sum6")
    private String FluorescentWhiteningAgent;
    @Column(name = "mf_icm")
    private String manufacture;
    @Column(name = "mf_mthd")
    private String manufactureMethod;
    @Column(name = "mf_nation")
    private String manufactureNation;
    // companyName은 이미 있으므로 매핑할 때 빼고 하기.
    @Column(name = "comp_addr")
    private String companyAddress;
    @Column(name = "comp_tel")
    private String companyTel;
    @Column(name = "in_comp_nm")
    private String importedCompanyName;
    @Column(name = "in_comp_addr")
    private String importedCompanyAddress;
    @Column(name = "in_comp_tel")
    private String importedCompanyTel;
    @Column(name = "odm_comp_nm")
    private String vendorName;
    @Column(name = "odm_comp_addr")
    private String vendorCompanyAddress;
    @Column(name = "odm_comp_tel")
    private String vendorCompanyTel;
}
