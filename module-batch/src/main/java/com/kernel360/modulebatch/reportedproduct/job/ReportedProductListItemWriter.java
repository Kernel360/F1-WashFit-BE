package com.kernel360.modulebatch.reportedproduct.job;

import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDto;
import com.kernel360.modulebatch.reportedproduct.service.ReportedProductService;
import java.util.List;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class ReportedProductListItemWriter implements ItemWriter<List<ReportedProductDto>> {

    private final ReportedProductService apiService;

    public ReportedProductListItemWriter(ReportedProductService apiService) {
        this.apiService = apiService;
    }

    /**
     * 각 chunk 마다 포함하고 있는 리스트들을 내부 순회하며 리스트의 원소인 제품을 저장한다.
     *
     * @param chunk 신고대상 제품 리스트의 chunk
     */
    @Override
    public void write(Chunk<? extends List<ReportedProductDto>> chunk) throws Exception {
        for (List<ReportedProductDto> c : chunk) {
            for (ReportedProductDto reportedProductDto : c) {
                apiService.saveReportedProduct(reportedProductDto);
            }
        }
    }
}
