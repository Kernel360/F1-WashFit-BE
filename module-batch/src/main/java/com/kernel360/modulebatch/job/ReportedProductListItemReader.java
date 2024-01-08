package com.kernel360.modulebatch.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kernel360.modulebatch.client.ReportedProductClient;
import com.kernel360.modulebatch.dto.ReportedProductDto;
import com.kernel360.modulebatch.service.ReportedProductService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;

@Slf4j
public class ReportedProductListItemReader implements ItemReader<List<ReportedProductDto>> {
    private final static String PAGE_KEY = "pageKey";
    private final static String START_PAGE_KEY = "startPageKey";
    private static int MAX_PAGES_PER_JOB = 10;
    private final ReportedProductService service;
    private final ReportedProductClient client;
    private int maxPageNumber;
    private int nextPage;


    public ReportedProductListItemReader(ReportedProductService service) {
        this.client = new ReportedProductClient();
        this.service = service;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getExecutionContext();
        if (executionContext.containsKey(PAGE_KEY)) {
            nextPage = executionContext.getInt(PAGE_KEY);
            executionContext.putInt(START_PAGE_KEY, nextPage);
        } else {
            nextPage = -1;
        }
    }

    @Override
    public List<ReportedProductDto> read() throws JsonProcessingException {
        nextPage++;
        if (nextPage == maxPageNumber + 1 || MAX_PAGES_PER_JOB-- < 0) { //
            return null;
        }

        log.info("Fetch Page = {}", nextPage);
        String xmlResponse = client.getXmlResponse(nextPage);
        maxPageNumber = service.getTotalPageCount(xmlResponse);
        log.info("Response accepted : {}", xmlResponse);

        return service.deserializeXml2ListDto(xmlResponse)
                      .reportedProductDtoList();
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getExecutionContext();
        executionContext.putInt(PAGE_KEY, nextPage);
    }

}
