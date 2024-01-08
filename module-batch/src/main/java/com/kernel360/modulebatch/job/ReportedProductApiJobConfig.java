package com.kernel360.modulebatch.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kernel360.modulebatch.dto.ReportedProductDto;
import com.kernel360.modulebatch.service.ReportedProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReportedProductApiJobConfig {

    private final ReportedProductService apiService;


    @Bean
    public Job fetchReportedProductListJob(JobRepository jobRepository, Step fetchProduct) {
        return new JobBuilder("fetchReportedProductListJob", jobRepository)
                .start(fetchProduct)
                .build();
    }

    @Bean
    public Step fetchProduct(JobRepository jobRepository, PlatformTransactionManager transactionManager)
            throws JsonProcessingException {

        return new StepBuilder("fetchReportedProductStep", jobRepository)
                .<List<ReportedProductDto>, List<ReportedProductDto>>chunk(20, transactionManager)
                .reader(productListItemReader()) // API 요청, 응답을 DTO 리스트로 반환
                .writer(productListItemWriter()) // DTO 리스트 입력, 저장
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<List<ReportedProductDto>> productListItemReader() throws JsonProcessingException {
        return new ReportedProductListItemReader(apiService);
    }

    @Bean
    @StepScope
    public ItemWriter<List<ReportedProductDto>> productListItemWriter() {
        return new ReportedProductListItemWriter(apiService);
    }


}