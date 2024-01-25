package com.kernel360.modulebatch.concernedproduct.job.infra;

import com.kernel360.brand.entity.Brand;
import com.kernel360.modulebatch.concernedproduct.client.ConcernedProductListClient;
import com.kernel360.modulebatch.concernedproduct.dto.ConcernedProductDto;
import com.kernel360.modulebatch.concernedproduct.dto.ConcernedProductListDto;
import com.kernel360.modulebatch.concernedproduct.service.ConcernedProductService;
import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
@RequiredArgsConstructor
public class ConcernedProductListItemProcessor implements ItemProcessor<Brand, List<ConcernedProductDto>> {
    private final ConcernedProductListClient client;
    private final ConcernedProductService service;

    @Override
    public List<ConcernedProductDto> process(@Nonnull Brand brand) throws Exception {
        List<ConcernedProductDto> list = new ArrayList<>();
        long nextPage = -1;

        int maxPageNumber;
        do {
            nextPage++;
            String xmlResponse = fetchXmlResponse(brand, nextPage);

            maxPageNumber = service.getTotalPageCount(xmlResponse);
            ConcernedProductListDto concernedProductListDto = service.deserializeXml2ListDto(xmlResponse);
            if (concernedProductListDto.count() == 0 || concernedProductListDto.concernedProductDtoList() == null) {
                break;
            }
            list.addAll(concernedProductListDto.concernedProductDtoList());
        } while (nextPage < maxPageNumber);

        return list;
    }

    private String fetchXmlResponse(Brand brand, long nextPage) {
        log.info("Fetch Page = {}", nextPage);
        String xmlResponse = client.getXmlResponse(brand, nextPage);
        log.info("Response accepted : {}", xmlResponse);
        return xmlResponse;
    }
}
