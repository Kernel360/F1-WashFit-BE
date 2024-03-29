package com.kernel360.modulebatch.reportedproduct.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.ecolife.repository.ReportedProductRepository;
import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDetailListDto;
import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDto;
import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductListDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@ComponentScan("com.kernel360.ecolife")
@RequiredArgsConstructor
public class ReportedProductService {

    private final ReportedProductRepository reportedProductRepository;
    private final XmlMapper xmlMapper = new XmlMapper();

    /**
     * @param xml 신고대상 생활화학제품 목록 요청에 대한 응답
     * @return 응답 xml 을 파싱한 ReportedProductListDto
     */
    public ReportedProductListDto deserializeXml2ListDto(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, ReportedProductListDto.class);
    }

    public ReportedProductDetailListDto deserializeXml2DetailDto(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, ReportedProductDetailListDto.class);
    }

    public int getTotalPageCount(String response) throws JsonProcessingException {
        return deserializeXml2ListDto(response)
                .count() / 20 + 1;
    }


    /**
     * Dto 를 입력으로 받아 ReportedProduct 테이블에 동일한 제품이 존재하는지 확인한 후 저장.
     */
    @Transactional
    public void saveReportedProduct(ReportedProductDto dto) throws JobExecutionException {
        ReportedProduct reportedProduct = ReportedProductDto.toEntity(dto);
        Optional<ReportedProduct> findReportedProduct = reportedProductRepository
                .findById(reportedProduct.getId());

        if (findReportedProduct.isEmpty()) {
            ReportedProduct newReportedProduct = reportedProductRepository.save(reportedProduct);
            log.info("Saved entity : {} ", newReportedProduct.getProductName());
            return;
        }
        log.info("Existing entity found: {}. Continuing without save", reportedProduct.getProductName());

    }

}
