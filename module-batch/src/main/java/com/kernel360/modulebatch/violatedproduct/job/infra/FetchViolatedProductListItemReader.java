package com.kernel360.modulebatch.violatedproduct.job.infra;

import com.kernel360.modulebatch.violatedproduct.client.ViolatedProductListClient;
import com.kernel360.modulebatch.violatedproduct.dto.ViolatedProductDto;
import com.kernel360.modulebatch.violatedproduct.service.ViolatedProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class FetchViolatedProductListItemReader implements ItemReader<List<ViolatedProductDto>> {
    private static int MAX_PAGES_PER_JOB;

    private final ViolatedProductListClient client;

    private final ViolatedProductService service;

    @Value("#{jobParameters['PRODUCT_ARM_CODE']}")
    private String productArmCode;
    private int maxPageNumber;
    private int nextPage = -1;

    @BeforeStep
    public void beforeStep() {
        MAX_PAGES_PER_JOB = 100;
    }

    @Override
    public List<ViolatedProductDto> read()
            throws Exception {
        nextPage++;
        if (nextPage == maxPageNumber + 1 || MAX_PAGES_PER_JOB-- <= 0) {
            return null;
        }

        String xmlResponse = fetchXmlResponse();

        maxPageNumber = service.getTotalPageCount(xmlResponse);

        return service.deserializeXml2ListDto(xmlResponse)
                      .violatedProductDtoList();
    }

    private String fetchXmlResponse() {
        log.info("Fetch Page = {}", nextPage);
        String xmlResponse = client.getXmlResponse(productArmCode, nextPage);
        log.info("Response accepted : {}", xmlResponse);
        return xmlResponse;
    }
}
