package com.kernel360.modulebatch.violatedproduct.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kernel360.ecolife.entity.ViolatedProduct;
import com.kernel360.ecolife.repository.ViolatedProductRepository;
import com.kernel360.modulebatch.violatedproduct.dto.ViolatedProductDetailDto;
import com.kernel360.modulebatch.violatedproduct.dto.ViolatedProductDetailListDto;
import com.kernel360.modulebatch.violatedproduct.dto.ViolatedProductDto;
import com.kernel360.modulebatch.violatedproduct.dto.ViolatedProductListDto;
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
public class ViolatedProductService {

    private final ViolatedProductRepository violatedProductRepository;

    private final XmlMapper xmlMapper = new XmlMapper();

    public ViolatedProductListDto deserializeXml2ListDto(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, ViolatedProductListDto.class);
    }

    public ViolatedProductDetailListDto deserializeXml2DetailDto(String xml) throws JsonProcessingException {
        return xmlMapper.readValue(xml, ViolatedProductDetailListDto.class);
    }

    public int getTotalPageCount(String response) throws JsonProcessingException {
        return deserializeXml2ListDto(response).count() / 20 + 1;
    }

    @Transactional
    public void saveViolatedProduct(ViolatedProductDto dto) throws JobExecutionException {
        ViolatedProduct violatedProduct = ViolatedProductDto.toEntity(dto);
        Optional<ViolatedProduct> foundViolatedProduct = violatedProductRepository.findById(violatedProduct.getId());

        if (foundViolatedProduct.isEmpty()) {
            ViolatedProduct newViolatedProduct = violatedProductRepository.save(violatedProduct);
            log.info("Saved entity : {} ", newViolatedProduct.getProductName());
            return;
        }
        log.info("Existing entity found: {}. Continuing without save", violatedProduct.getProductName());

    }
}
