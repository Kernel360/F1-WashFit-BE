package com.kernel360.modulebatch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

public record ReportedProductDetailListDto(@JsonProperty("count") int count,
                                           @JsonProperty("resultcode") String resultCode,
                                           @JsonProperty("pagenum") int pageNum,
                                           @JsonProperty("pagesize") int pageSize,
                                           @JacksonXmlElementWrapper(useWrapping = false)
                                           @JacksonXmlProperty(localName = "row")
                                           List<ReportedProductDetailDto> reportedProductDetailDtoList) {

}
