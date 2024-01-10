package com.kernel360.modulebatch.job;

import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.modulebatch.dto.ReportedProductDto;
import com.kernel360.modulebatch.service.ReportedProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReportedProductApiJobConfig {

    private final ReportedProductService service;

    private final EntityManagerFactory emf;


    @Bean
    public Job fetchReportedProductJob(JobRepository jobRepository,
                                       @Qualifier("fetchReportedProductListStep") Step fetchReportedProductListStep,
                                       @Qualifier("fetchReportedProductDetailStep") Step fetchReportedProductDetailStep) {
        log.info("Fetch ReportedProduct List Job Build Configuration");
        return new JobBuilder("fetchReportedProductListJob", jobRepository)
                .start(fetchReportedProductListStep)
                .next(fetchReportedProductDetailStep)
                .incrementer(new RunIdIncrementer())
                .listener(new FetchReportedProductExecutionListener())
                .build();
    }

    //-- List Step --//
    @Bean
    public Step fetchReportedProductListStep(JobRepository jobRepository,
                                             PlatformTransactionManager transactionManager) {
        log.info("Fetch ReportedProduct List Step Build Configuration");
        return new StepBuilder("fetchReportedProductListStep", jobRepository)
                .<List<ReportedProductDto>, List<ReportedProductDto>>chunk(25, transactionManager)
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

    //-- Detail Step --//

    @Bean
    public Step fetchReportedProductDetailStep(JobRepository jobRepository,
                                               PlatformTransactionManager transactionManager) {
        log.info("Fetch ReportedProduct detail Step Build Configuration");
        return new StepBuilder("fetchReportedProductDetailStep", jobRepository)
                .<ReportedProduct, ReportedProduct>chunk(25, transactionManager)
                .reader(productDetailItemReader()) // reported_product 테이블 읽어서 엔티티를 전달
                .processor(productDetailItemProcessor()) // 전달받은 엔티티로 detail 조회, 엔티티로 변환
                .writer(productDetailJpaItemWriter(emf)) // 엔티티를 테이블에 업데이트
                .build();
    }

    @Bean
    public JpaPagingItemReader<ReportedProduct> productDetailItemReader() {
        String jpql = "SELECT rp FROM ReportedProduct rp ORDER BY rp.id.productMasterId, rp.id.estNumber";

        JpaPagingItemReader<ReportedProduct> reader = new JpaPagingItemReader<>();
        reader.setQueryString(jpql);
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(1000);

        return reader;
    }

    @Bean
    @StepScope
    public ReportedProductDetailItemProcessor productDetailItemProcessor() {
        return new ReportedProductDetailItemProcessor(service);
    }

    @Bean
    @StepScope // 여기를 바꿔서 변경점만 변경하도록 바꿔야 함.
    public JpaItemWriter<ReportedProduct> productDetailJpaItemWriter(EntityManagerFactory emf) {
        JpaItemWriter<ReportedProduct> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(emf);

        return jpaItemWriter;
    }


    //-- Execution Listener --//

    public static class FetchReportedProductExecutionListener implements JobExecutionListener {
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