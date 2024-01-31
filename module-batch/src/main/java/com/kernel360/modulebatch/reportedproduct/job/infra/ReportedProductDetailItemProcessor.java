package com.kernel360.modulebatch.reportedproduct.job.infra;

import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.modulebatch.reportedproduct.client.ReportedProductDetailClient;
import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDetailDto;
import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDto;
import com.kernel360.modulebatch.reportedproduct.service.ReportedProductService;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReportedProductDetailItemProcessor implements ItemProcessor<ReportedProduct, ReportedProduct> {
    private final ReportedProductService service;
    private final ReportedProductDetailClient client;

    public ReportedProductDetailItemProcessor(ReportedProductService service) {
        this.service = service;
        this.client = new ReportedProductDetailClient();
    }

    /**
     * @param item ItemReader 에서 넘겨받은 엔티티
     * @return 업데이트할 엔티티 정보로 변경해서 반환
     */
    @Override
    public ReportedProduct process(@NonNull ReportedProduct item) throws Exception {
        if (item.getInspectedOrganization() != null) {
            return null;
        }
        String response = fetchXmlResponse(item);
        ReportedProductDetailDto detailDto = service.deserializeXml2DetailDto(response)
                                                    .reportedProductDetailDtoList().get(0);

        ReportedProductDto productDto = ReportedProductDto.of(item.getId().getProductMasterId(),
                item.getProductName(),
                item.getSafetyReportNumber(),
                item.getItem(),
                item.getId().getEstNumber(),
                item.getRegisteredDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                item.getCompanyName());

        return ReportedProductDetailDto.toEntity(detailDto, productDto);
    }

    private String fetchXmlResponse(ReportedProduct reportedProduct) {
        log.info("Fetch item Id = {}", reportedProduct);
        String xmlResponse = client.getXmlResponse(reportedProduct);
        log.info("Response accepted : {}", xmlResponse);

        return removeInvalidXmlCharacters(xmlResponse);
    }

    public String removeInvalidXmlCharacters(String xmlString) { // XML 1.0 Specification 을 준수하는 ASCII printable characters (REPLACEMENT_CHARACTER)
        String pattern = "[^\t\r\n -\uD7FF\uE000-\uFFFD\ud800\udc00-\udbff\udfff]";

        return xmlString.replaceAll(pattern, "");
    }
}
