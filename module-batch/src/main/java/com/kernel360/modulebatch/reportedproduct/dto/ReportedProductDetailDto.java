package com.kernel360.modulebatch.reportedproduct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.ecolife.entity.ReportedProductId;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "row")
public record ReportedProductDetailDto(
        @JacksonXmlProperty(localName = "mst_id")
        String productMasterId,
        @JacksonXmlProperty(localName = "est_no")
        int estNumber,
        @JacksonXmlProperty(localName = "slfsfcfst_no")
        String safetyReportNumber,
        @JacksonXmlProperty(localName = "inspct_org")
        String inspectedOrganization,
        @JacksonXmlProperty(localName = "prdt_type")
        String productType,
        @JacksonXmlProperty(localName = "issu_date")
        String issuedDate,
        @JacksonXmlProperty(localName = "renew_yn")
        String isRenewed,
        @JacksonXmlProperty(localName = "safe_sd")
        String safeStandard,
        @JacksonXmlProperty(localName = "expr_date")
        String expireDate,
        @JacksonXmlProperty(localName = "upper_item")
        String upperItem,
        @JacksonXmlProperty(localName = "kid_prt_pkg")
        String kidProtectPackage,
        @JacksonXmlProperty(localName = "df")
        String productDefinition,
        @JacksonXmlProperty(localName = "propos")
        String productPropose,
        @JacksonXmlProperty(localName = "dtrb_lmt")
        String distributeLimit,
        @JacksonXmlProperty(localName = "wt_bulk")
        String weightAndBulk,
        @JacksonXmlProperty(localName = "icepnt")
        String icePoint,
        @JacksonXmlProperty(localName = "stddusqy")
        String standardUseQuantity,
        @JacksonXmlProperty(localName = "usmtd")
        String useMethod,
        @JacksonXmlProperty(localName = "us_atnrpt")
        String usageAttentionReport,
        @JacksonXmlProperty(localName = "frsaid")
        String firstAid,
        @JacksonXmlProperty(localName = "sum1")
        String mainSubstance,
        @JacksonXmlProperty(localName = "sum2")
        String preservative,
        @JacksonXmlProperty(localName = "sum3")
        String allergicSubstance,
        @JacksonXmlProperty(localName = "sum4")
        String surfactant,
        @JacksonXmlProperty(localName = "sum5")
        String otherSubstance,
        @JacksonXmlProperty(localName = "sum6")
        String FluorescentWhiteningAgent,
        @JacksonXmlProperty(localName = "mf_icm")
        String manufacture,
        @JacksonXmlProperty(localName = "mf_mthd")
        String manufactureMethod,
        @JacksonXmlProperty(localName = "mf_nation")
        String manufactureNation,
        @JacksonXmlProperty(localName = "comp_addr")
        String companyAddress,
        @JacksonXmlProperty(localName = "comp_tel")
        String companyTel,
        @JacksonXmlProperty(localName = "in_comp_nm")
        String importedCompanyName,
        @JacksonXmlProperty(localName = "in_comp_addr")
        String importedCompanyAddress,
        @JacksonXmlProperty(localName = "in_comp_tel")
        String importedCompanyTel,
        @JacksonXmlProperty(localName = "odm_comp_nm")
        String vendorName,
        @JacksonXmlProperty(localName = "odm_comp_addr")
        String vendorCompanyAddress,
        @JacksonXmlProperty(localName = "odm_comp_tel")
        String vendorCompanyTel
) {

    public static ReportedProduct toEntity(ReportedProductDetailDto detailDto, ReportedProductDto dto) {

        return ReportedProduct.of(new ReportedProductId(detailDto.productMasterId, detailDto.estNumber),
                dto.productName(), dto.safetyReportNumber(), dto.item(),
                LocalDate.parse(dto.registeredDate(), DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay(),
                dto.companyName(),
                detailDto.inspectedOrganization, detailDto.productType,
                LocalDate.parse(detailDto.issuedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(),
                detailDto.isRenewed, detailDto.safeStandard,
                LocalDate.parse(detailDto.expireDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(),
                detailDto.upperItem, detailDto.kidProtectPackage, detailDto.productDefinition,
                detailDto.productPropose,
                detailDto.distributeLimit,
                detailDto.weightAndBulk, detailDto.icePoint, detailDto.standardUseQuantity,
                detailDto.useMethod,
                detailDto.usageAttentionReport, detailDto.firstAid, detailDto.mainSubstance, detailDto.preservative,
                detailDto.allergicSubstance,
                detailDto.surfactant, detailDto.otherSubstance, detailDto.FluorescentWhiteningAgent,
                detailDto.manufacture, detailDto.manufactureMethod,
                detailDto.manufactureNation, detailDto.companyAddress, detailDto.companyTel,
                detailDto.importedCompanyName, detailDto.importedCompanyAddress,
                detailDto.importedCompanyTel, detailDto.vendorName, detailDto.vendorCompanyAddress,
                detailDto.vendorCompanyTel);
    }

}
