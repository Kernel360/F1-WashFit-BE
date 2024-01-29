package com.kernel360.modulebatch.reportedproduct.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.ecolife.entity.ReportedProductId;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ReportedProductDto(

        @JacksonXmlProperty(localName = "mst_id")
        String productMasterId,
        @JacksonXmlProperty(localName = "prdt_nm")
        String productName,
        @JacksonXmlProperty(localName = "slfsfcfst_no")
        String safetyReportNumber,
        @JacksonXmlProperty(localName = "item")
        String item,
        @JacksonXmlProperty(localName = "est_no")
        int estNumber,
        @JacksonXmlProperty(localName = "reg_date")
        String registeredDate,
        @JacksonXmlProperty(localName = "comp_nm")
        String companyName
) {
    public static ReportedProductDto of(String productMasterId,
                                        String productName,
                                        String safetyReportNumber,
                                        String item,
                                        int estNumber,
                                        String registeredDate,
                                        String companyName
    ) {
        return new ReportedProductDto(productMasterId, productName, safetyReportNumber,
                item, estNumber, registeredDate, companyName);
    }

    public static ReportedProduct toEntity(ReportedProductDto reportedProductDto) {
        return ReportedProduct.of(
                new ReportedProductId(reportedProductDto.productMasterId, reportedProductDto.estNumber),
                reportedProductDto.productName,
                reportedProductDto.safetyReportNumber,
                reportedProductDto.item,
                LocalDate.parse(reportedProductDto.registeredDate, DateTimeFormatter.ofPattern("yyyyMMdd"))
                         .atStartOfDay(),
                reportedProductDto.companyName);
    }
}