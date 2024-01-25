package com.kernel360.modulebatch.concernedproduct.job.infra;

import com.kernel360.modulebatch.concernedproduct.dto.ConcernedProductDto;
import com.kernel360.modulebatch.concernedproduct.service.ConcernedProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class ConcernedProductListItemWriter implements ItemWriter<List<ConcernedProductDto>> {

    private final ConcernedProductService service;

    @Override
    public void write(Chunk<? extends List<ConcernedProductDto>> chunk) throws Exception {
        for (List<ConcernedProductDto> list : chunk) {
            for (ConcernedProductDto dto : list) {
                service.saveConcernedProduct(dto);
            }
        }
    }
}
