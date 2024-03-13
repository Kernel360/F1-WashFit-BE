package com.kernel360.modulebatch.reportedproduct.job.infra;

import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDto;
import com.kernel360.modulebatch.reportedproduct.service.ReportedProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
public class ReportedProductListItemWriter implements ItemWriter<List<ReportedProductDto>> {
    private final ReportedProductService service;

    /**
     * 각 chunk 마다 포함하고 있는 리스트들을 내부 순회하며 리스트의 원소인 제품을 저장한다.
     *
     * @param chunk 신고대상 제품 리스트의 chunk
     */
    @Override
    public void write(Chunk<? extends List<ReportedProductDto>> chunk) throws Exception {
        for (List<ReportedProductDto> c : chunk) {
            for (ReportedProductDto reportedProductDto : c) {
                service.saveReportedProduct(reportedProductDto);
            }
        }
    }
}
