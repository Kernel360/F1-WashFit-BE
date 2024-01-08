package com.kernel360.modulebatch.job;

import com.kernel360.modulebatch.dto.ReportedProductDto;
import com.kernel360.modulebatch.service.ReportedProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReportedProductApiJobConfig {

    private final ReportedProductService service;

    @Bean
    public Job fetchReportedProductListJob(JobRepository jobRepository, Step fetchProduct) {
        log.info("Job Build Configuration");
        return new JobBuilder("fetchReportedProductListJob", jobRepository)
                .start(fetchProduct)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step fetchProduct(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("Step Build Configuration");
        return new StepBuilder("fetchReportedProductStep", jobRepository)
                .<List<ReportedProductDto>, List<ReportedProductDto>>chunk(10, transactionManager)
                .reader(productListItemReader()) // API 요청, 응답을 DTO 리스트로 반환
                .writer(productListItemWriter()) // DTO 리스트 입력, 저장
                .build();
    }

    @Bean
    @StepScope
    public ReportedProductListItemReader productListItemReader() {
        return new ReportedProductListItemReader(service);
    }

    @Bean
    @StepScope
    public ReportedProductListItemWriter productListItemWriter() {
        return new ReportedProductListItemWriter(service);
    }


}