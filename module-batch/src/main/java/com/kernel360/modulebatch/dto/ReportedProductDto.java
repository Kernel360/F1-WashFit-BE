package com.kernel360.modulebatch.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public record ReportedProductDto(
        @JacksonXmlProperty(localName = "mst_id")
        String mstId,

        @JacksonXmlProperty(localName = "prdt_nm")
        String prdtNm,

        @JacksonXmlProperty(localName = "slfsfcfst_no")
        String slfsfcfstNo,

        @JacksonXmlProperty(localName = "item")
        String item,

        @JacksonXmlProperty(localName = "est_no")
        int estNo,

        @JacksonXmlProperty(localName = "reg_date")
        String regDate,

        @JacksonXmlProperty(localName = "comp_nm")
        String compNm
) {
}