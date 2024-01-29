package com.kernel360.modulebatch.reportedproduct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "rowList")
@JsonIgnoreProperties(ignoreUnknown = true)
public record ReportedProductListDto(
        @JacksonXmlProperty(localName = "count")
        int count,

        @JacksonXmlProperty(localName = "resultcode")
        String resultCode,

        @JacksonXmlProperty(localName = "pagenum")
        int pageNum,

        @JacksonXmlProperty(localName = "pagesize")
        int pageSize,

        @JacksonXmlProperty(localName = "row")
        @JacksonXmlElementWrapper(useWrapping = false)
        List<ReportedProductDto> reportedProductDtoList
) {

}

