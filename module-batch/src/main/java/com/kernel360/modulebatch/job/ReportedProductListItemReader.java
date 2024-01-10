package com.kernel360.modulebatch.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kernel360.modulebatch.client.ReportedProductClient;
import com.kernel360.modulebatch.dto.ReportedProductDto;
import com.kernel360.modulebatch.service.ReportedProductService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
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

    // FIXME :: 몇 페이지까지 요청을 해서 데이터를 가져왔는지 저장하고 다음 스케쥴에 잡을 실행할 때 진행하지 않은 부분만 작업하도록 변경이 필요
    @BeforeStep
    public void beforeStep() {
        MAX_PAGES_PER_JOB = 100;
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
