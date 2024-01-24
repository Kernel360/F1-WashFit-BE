package com.kernel360.modulebatch.concernedproduct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "row")
public record ConcernedProductDto(
        @JacksonXmlProperty(localName = "productName") String productName,
        @JacksonXmlProperty(localName = "productNumber") String productNumber,
        @JacksonXmlProperty(localName = "reportNumber") String reportNumber,
        @JacksonXmlProperty(localName = "item") String item,
        @JacksonXmlProperty(localName = "comp_nm") String comp_nm) {
}