package com.kernel360.modulebatch.reportedproduct.job;

import com.kernel360.brand.entity.Brand;
import com.kernel360.modulebatch.reportedproduct.client.ReportedProductFromBrandClient;
import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDto;
import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductListDto;
import com.kernel360.modulebatch.reportedproduct.service.ReportedProductService;
import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
@RequiredArgsConstructor
public class FetchReportedProductListFromBrandItemProcessor implements ItemProcessor<Brand, List<ReportedProductDto>> {

    private final ReportedProductFromBrandClient client;

    private final ReportedProductService service;

    @Override
    public List<ReportedProductDto> process(@Nonnull Brand brand) throws Exception {
        List<ReportedProductDto> list = new ArrayList<>();
        int nextPage = -1;

        int maxPageNumber;
        do {
            nextPage++;
            String xmlResponse = fetchXmlResponse(brand, nextPage);

            maxPageNumber = service.getTotalPageCount(xmlResponse);
            ReportedProductListDto reportedProductListDto = service.deserializeXml2ListDto(xmlResponse);
            if (reportedProductListDto.count() == 0) {
                break;
            }
            list.addAll(reportedProductListDto.reportedProductDtoList());
        } while (nextPage < maxPageNumber);

        return list;
    }

    private String fetchXmlResponse(Brand brand, int nextPage) {
        log.info("Fetch Page = {}", nextPage);
        String xmlResponse = client.getXmlResponse(brand, nextPage);
        log.info("Response accepted : {}", xmlResponse);
        return xmlResponse;
    }
}
