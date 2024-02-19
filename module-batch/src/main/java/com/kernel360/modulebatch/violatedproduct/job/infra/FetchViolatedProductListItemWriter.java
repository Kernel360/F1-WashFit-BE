package com.kernel360.modulebatch.violatedproduct.job.infra;

import com.kernel360.modulebatch.violatedproduct.dto.ViolatedProductDto;
import com.kernel360.modulebatch.violatedproduct.service.ViolatedProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
public class FetchViolatedProductListItemWriter implements ItemWriter<List<ViolatedProductDto>> {

    private final ViolatedProductService service;

    @Override
    public void write(Chunk<? extends List<ViolatedProductDto>> chunk) throws Exception {
        for (List<ViolatedProductDto> list : chunk) {
            for (ViolatedProductDto violatedProductDto : list) {
                service.saveViolatedProduct(violatedProductDto);
            }
        }
    }
}
