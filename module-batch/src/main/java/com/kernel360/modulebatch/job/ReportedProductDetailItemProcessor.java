package com.kernel360.modulebatch.job;

import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.modulebatch.client.ReportedProductDetailClient;
import com.kernel360.modulebatch.dto.ReportedProductDetailDto;
import com.kernel360.modulebatch.dto.ReportedProductDto;
import com.kernel360.modulebatch.service.ReportedProductService;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;

@Slf4j
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
        String response = fetchXmlResponse(item);
        ReportedProductDetailDto detailDto = service.deserializeXml2DetailDto(response)
                                                    .reportedProductDetailDtoList().get(0); // 리팩터링 필요함
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
        return xmlResponse;
    }
}
