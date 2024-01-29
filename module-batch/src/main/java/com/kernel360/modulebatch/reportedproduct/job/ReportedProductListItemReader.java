package com.kernel360.modulebatch.reportedproduct.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kernel360.modulebatch.reportedproduct.client.ReportedProductClient;
import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDto;
import com.kernel360.modulebatch.reportedproduct.service.ReportedProductService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;

@Slf4j
public class ReportedProductListItemReader implements ItemReader<List<ReportedProductDto>> {

    private static int MAX_PAGES_PER_JOB;
    private final ReportedProductService service;
    private final ReportedProductClient client;
    private int maxPageNumber;
    private int nextPage = -1;

    public ReportedProductListItemReader(ReportedProductService service) {
        this.client = new ReportedProductClient();
        this.service = service;
    }

    @BeforeStep
    public void beforeStep() {
        MAX_PAGES_PER_JOB = 100 ;
    }

    @Override
    public List<ReportedProductDto> read() throws JsonProcessingException {
        nextPage++;
        if (nextPage == maxPageNumber + 1 || MAX_PAGES_PER_JOB-- <= 0) {
            return null;
        }

        String xmlResponse = fetchXmlResponse();

        maxPageNumber = service.getTotalPageCount(xmlResponse);

        return service.deserializeXml2ListDto(xmlResponse)
                      .reportedProductDtoList();
    }

    private String fetchXmlResponse() {
        log.info("Fetch Page = {}", nextPage);
        String xmlResponse = client.getXmlResponse(nextPage);
        log.info("Response accepted : {}", xmlResponse);
        return xmlResponse;
    }
}
