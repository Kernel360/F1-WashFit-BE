package com.kernel360.modulebatch.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kernel360.modulebatch.client.ReportedProductClient;
import com.kernel360.modulebatch.dto.ReportedProductDto;
import com.kernel360.modulebatch.service.ReportedProductService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

@Slf4j
public class ReportedProductListItemReader implements ItemReader<List<ReportedProductDto>> {
    private final ReportedProductService service;
    private final ReportedProductClient client;
    private int maxPageNumber;
    private int pageNumber = 0;


    public ReportedProductListItemReader(ReportedProductService service) throws JsonProcessingException {
        this.client = new ReportedProductClient();
        this.service = service;
    }

    @Override
    public List<ReportedProductDto> read() throws JsonProcessingException {
        pageNumber++;
        if (pageNumber == maxPageNumber + 1) {
            return null;
        }

        log.info("Fetch Page = {}", pageNumber);
        String xmlResponse = client.getXmlResponse(pageNumber);
        maxPageNumber = service.getTotalPageCount(xmlResponse);
        log.info("Response accepted : {}", xmlResponse);

        return service.deserializeXml2ListDto(xmlResponse)
                      .reportedProductDtoList();
    }

}
