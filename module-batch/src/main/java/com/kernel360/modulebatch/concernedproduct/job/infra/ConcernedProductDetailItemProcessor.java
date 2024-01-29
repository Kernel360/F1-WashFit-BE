package com.kernel360.modulebatch.concernedproduct.job.infra;

import com.kernel360.ecolife.entity.ConcernedProduct;
import com.kernel360.modulebatch.concernedproduct.client.ConcernedProductDetailClient;
import com.kernel360.modulebatch.concernedproduct.dto.ConcernedProductDetailDto;
import com.kernel360.modulebatch.concernedproduct.dto.ConcernedProductDto;
import com.kernel360.modulebatch.concernedproduct.service.ConcernedProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcernedProductDetailItemProcessor implements ItemProcessor<ConcernedProduct, ConcernedProduct> {

    private final ConcernedProductService service;

    private final ConcernedProductDetailClient client;

    /**
     * @param item ItemReader 에서 넘겨받은 엔티티
     * @return 업데이트할 엔티티 정보로 변경해서 반환
     */
    @Override
    public ConcernedProduct process(@NonNull ConcernedProduct item) throws Exception {
        log.debug("Processing {}", item.getProductName());

        if (item.getInspectedOrganization() != null) {
            return null;
        }

        String response = fetchXmlResponse(item);
        ConcernedProductDetailDto detailDto = service.deserializeXml2DetailDto(response)
                                                     .concernedProductDetailDtoList()
                                                     .get(0);

        ConcernedProductDto productDto = ConcernedProductDto.of(
                item.getProductNo(),
                item.getProductName(),
                item.getReportNumber(),
                item.getItem(),
                item.getCompanyName()
        );

        return ConcernedProductDetailDto.toEntity(detailDto, productDto);
    }

    private String fetchXmlResponse(ConcernedProduct concernedProduct) {
        log.info("Fetch item Id = {}", concernedProduct);
        String xmlResponse = client.getXmlResponse(concernedProduct);
        log.info("Response accepted : {}", xmlResponse);

        return removeInvalidXmlCharacters(xmlResponse);
    }

    /**
     * 유효하지 않은 문자열 패턴을 필터링하는 메서드
     */
    public String removeInvalidXmlCharacters(String xmlString) {
        // XML 1.0 Specification 을 준수하는 ASCII printable characters (REPLACEMENT_CHARACTER)
        String pattern = "[^\t\r\n -\uD7FF\uE000-\uFFFD\ud800\udc00-\udbff\udfff]";

        return xmlString.replaceAll(pattern, "");
    }
}

