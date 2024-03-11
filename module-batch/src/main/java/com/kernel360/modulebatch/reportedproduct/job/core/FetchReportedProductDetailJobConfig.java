package com.kernel360.modulebatch.reportedproduct.job.core;

import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.modulebatch.global.BaseJobExecutionListener;
import com.kernel360.modulebatch.global.BaseStepExecutionListener;
import com.kernel360.modulebatch.reportedproduct.job.infra.ReportedProductDetailItemProcessor;
import jakarta.persistence.EntityManagerFactory;
import java.net.ConnectException;
import java.nio.channels.ClosedChannelException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = FetchReportedProductDetailJobConfig.JOB_NAME)
public class FetchReportedProductDetailJobConfig {

    public static final String JOB_NAME = "FetchReportedProductDetailJob";
    private final ReportedProductDetailItemProcessor reportedProductDetailItemProcessor;
    private final BaseJobExecutionListener baseJobExecutionListener;
    private final BaseStepExecutionListener baseStepExecutionListener;
    private final EntityManagerFactory emf;

    @Bean
    public Job FetchReportedProductDetailJob(JobRepository jobRepository,
                                             @Qualifier("fetchReportedProductDetailStep") Step fetchReportedProductDetailStep) {
        log.info("Fetch ReportedProduct detail Job Build Configuration");
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(fetchReportedProductDetailStep)
                .incrementer(new RunIdIncrementer())
                .listener(baseJobExecutionListener)
                .build();
    }

    @Bean
    public Step fetchReportedProductDetailStep(JobRepository jobRepository,
                                               PlatformTransactionManager transactionManager) {
        log.info("Fetch ReportedProduct detail Step Build Configuration");
        return new StepBuilder("fetchReportedProductDetailStep", jobRepository)
                .listener(baseStepExecutionListener)
                .<ReportedProduct, ReportedProduct>chunk(10, transactionManager)
                .reader(productDetailItemReader()) // reported_product 테이블 읽어서 엔티티를 전달
                .processor(reportedProductDetailItemProcessor) // 전달받은 엔티티로 detail 조회, 엔티티로 변환
                .writer(productDetailJpaItemWriter(emf)) // 엔티티를 테이블에 업데이트
                .faultTolerant()
                .retryLimit(2)
                .retry(ResourceAccessException.class)
                .retry(ConnectException.class)
                .retry(ClosedChannelException.class)
                .skipLimit(10)
                .skip(DataIntegrityViolationException.class)
                .build();
    }

    @Bean
    public JpaPagingItemReader<ReportedProduct> productDetailItemReader() {
        String jpql = "SELECT rp FROM ReportedProduct rp WHERE rp.inspectedOrganization IS null";
        JpaPagingItemReader<ReportedProduct> reader = new JpaPagingItemReader<>();
        reader.setQueryString(jpql);
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(1000);

        return reader;
    }

    @Bean
    @StepScope
    public JpaItemWriter<ReportedProduct> productDetailJpaItemWriter(EntityManagerFactory emf) {
        JpaItemWriter<ReportedProduct> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(emf);

        return jpaItemWriter;
    }
}
