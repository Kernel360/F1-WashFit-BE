package com.kernel360.modulebatch.concernedproduct.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "row")
public record ConcernedProductDetailDto(
        @JacksonXmlProperty(localName = "prdt_no")
        String productNo,

        @JacksonXmlProperty(localName = "prdt_nm")
        String productName,

        @JacksonXmlProperty(localName = "inspct_org")
        String inspectedOrganization,

        @JacksonXmlProperty(localName = "issu_date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate issuedDate,

        @JacksonXmlProperty(localName = "upper_item")
        String upperItem,

        @JacksonXmlProperty(localName = "prdt_type")
        String productType,

        @JacksonXmlProperty(localName = "renew_yn")
        String renewedType,

        @JacksonXmlProperty(localName = "slfsfcfst_no")
        String reportNumber,

        @JacksonXmlProperty(localName = "safe_sd")
        String safetyInspectionStandard,

        @JacksonXmlProperty(localName = "kid_prt_pkg")
        String kidProtectPackage,

        @JacksonXmlProperty(localName = "mf_cpy")
        String manufactureNation,

        @JacksonXmlProperty(localName = "df")
        String productDefinition,

        @JacksonXmlProperty(localName = "item")
        String item,

        @JacksonXmlProperty(localName = "mf_icm")
        String manufacture,

        @JacksonXmlProperty(localName = "comp_nm")
        String companyName
) {
    public static ConcernedProductDetailDto of(
            String productName,
            String productMasterId,
            String inspectedOrganization,
            LocalDate issuedDate,
            String upperItem,
            String productType,
            String renewedType,
            String reportNumber,
            String safetyInspectionStandard,
            String kidProtectPackage,
            String manufactureNation,
            String productDefinition,
            String item,
            String manufacture,
            String companyName) {
        return new ConcernedProductDetailDto(productName, productMasterId, inspectedOrganization, issuedDate,
                upperItem, productType, renewedType, reportNumber, safetyInspectionStandard, kidProtectPackage,
                manufactureNation, productDefinition, item, manufacture, companyName);
    }

}