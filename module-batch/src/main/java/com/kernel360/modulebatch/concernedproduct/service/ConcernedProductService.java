package com.kernel360.modulebatch.concernedproduct.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kernel360.ecolife.entity.ConcernedProduct;
import com.kernel360.ecolife.repository.ConcernedProductRepository;
import com.kernel360.modulebatch.concernedproduct.dto.ConcernedProductDetailListDto;
import com.kernel360.modulebatch.concernedproduct.dto.ConcernedProductDto;
import com.kernel360.modulebatch.concernedproduct.dto.ConcernedProductListDto;
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
public class ConcernedProductService {
    private final ConcernedProductRepository concernedProductRepository;
    private final XmlMapper xmlMapper = new XmlMapper();

    public ConcernedProductListDto deserializeXml2ListDto(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, ConcernedProductListDto.class);
    }

    public ConcernedProductDetailListDto deserializeXml2DetailDto(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, ConcernedProductDetailListDto.class);
    }

    public int getTotalPageCount(String response) throws JsonProcessingException {
        return deserializeXml2ListDto(response).count() / 20 + 1;
    }

    @Transactional
    public void saveConcernedProduct(ConcernedProductDto dto) throws JobExecutionException {
        ConcernedProduct concernedProduct = ConcernedProductDto.toEntity(dto);
        Optional<ConcernedProduct> foundConcernedProduct = concernedProductRepository.findById(dto.productNo());

        if (foundConcernedProduct.isEmpty()) {
            ConcernedProduct newConcernedProduct = concernedProductRepository.save(concernedProduct);
            log.info("Saved entity : {} ", newConcernedProduct.getProductName());
            return;
        }
        log.info("Existing entity found: {}. Continuing without save", concernedProduct.getProductName());

    }
}
