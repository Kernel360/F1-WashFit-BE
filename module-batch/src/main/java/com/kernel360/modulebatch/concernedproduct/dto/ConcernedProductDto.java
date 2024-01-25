package com.kernel360.modulebatch.concernedproduct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kernel360.ecolife.entity.ConcernedProduct;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "row")
public record ConcernedProductDto(
        @JacksonXmlProperty(localName = "prdt_no") String productNo,
        @JacksonXmlProperty(localName = "prdt_nm") String productName,
        @JacksonXmlProperty(localName = "slfsfcfst_no") String reportNumber,
        @JacksonXmlProperty(localName = "item") String item,
        @JacksonXmlProperty(localName = "comp_nm") String companyName) {

    public static ConcernedProduct toEntity(ConcernedProductDto dto) {
        return ConcernedProduct.of(dto.productNo, dto.productName(),
                dto.reportNumber(), dto.item(), dto.companyName());
    }
}