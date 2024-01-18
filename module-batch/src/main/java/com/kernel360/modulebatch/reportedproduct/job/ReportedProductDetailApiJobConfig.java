package com.kernel360.modulebatch.reportedproduct.job;

import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.modulebatch.reportedproduct.service.ReportedProductService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReportedProductDetailApiJobConfig {
    private final ReportedProductService service;

    private final EntityManagerFactory emf;

    @Bean
    public Job fetchReportedProductDetailJob(JobRepository jobRepository,
                                             @Qualifier("fetchReportedProductDetailStep") Step fetchReportedProductDetailStep) {
        log.info("Fetch ReportedProduct detail Job Build Configuration");
        return new JobBuilder("fetchReportedProductDetailJob", jobRepository)
                .start(fetchReportedProductDetailStep)
                .incrementer(new RunIdIncrementer())
                .listener(new FetchReportedProductDetailExecutionListener())
                .build();
    }

    @Bean
    public Step fetchReportedProductDetailStep(JobRepository jobRepository,
                                               PlatformTransactionManager transactionManager) {
        log.info("Fetch ReportedProduct detail Step Build Configuration");
        return new StepBuilder("fetchReportedProductDetailStep", jobRepository)
                .<ReportedProduct, ReportedProduct>chunk(10, transactionManager)
                .reader(productDetailItemReader()) // reported_product 테이블 읽어서 엔티티를 전달
                .processor(productDetailItemProcessor()) // 전달받은 엔티티로 detail 조회, 엔티티로 변환
                .writer(productDetailJpaItemWriter(emf)) // 엔티티를 테이블에 업데이트
                .faultTolerant()
                .retryLimit(2)
                .retry(ResourceAccessException.class)
                .skipLimit(10)
                .skip(DataIntegrityViolationException.class)
                .build();
    }

    @Bean
    public JpaPagingItemReader<ReportedProduct> productDetailItemReader() {
        String jpql = "SELECT rp FROM ReportedProduct rp";

        JpaPagingItemReader<ReportedProduct> reader = new JpaPagingItemReader<>();
        reader.setQueryString(jpql);
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(3000);

        return reader;
    }

    @Bean
    @StepScope
    public ReportedProductDetailItemProcessor productDetailItemProcessor() {
        return new ReportedProductDetailItemProcessor(service);
    }

    @Bean
    @StepScope
    public JpaItemWriter<ReportedProduct> productDetailJpaItemWriter(EntityManagerFactory emf) {
        JpaItemWriter<ReportedProduct> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(emf);

        return jpaItemWriter;
    }

    //-- Execution Listener --//

    public static class FetchReportedProductDetailExecutionListener implements JobExecutionListener {
        @Override
        public void beforeJob(JobExecution jobExecution) {
            log.info("{} starts", jobExecution.getJobInstance().getJobName());
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            log.info("{} ends", jobExecution.getJobInstance().getJobName());
        }
    }

}
