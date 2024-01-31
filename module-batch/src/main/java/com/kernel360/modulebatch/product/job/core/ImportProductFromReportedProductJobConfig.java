package com.kernel360.modulebatch.product.job.core;

import com.kernel360.brand.entity.Brand;
import com.kernel360.modulebatch.product.job.infra.JpaProductListWriter;
import com.kernel360.modulebatch.product.job.infra.ReportedProductToProductListProcessor;
import com.kernel360.product.entity.Product;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
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
public class ImportProductFromReportedProductJobConfig {

    private final ReportedProductToProductListProcessor reportedProductToProductListProcessor;

    private final EntityManagerFactory emf;

    @Bean
    public Job importProductFromReportedProductJob(JobRepository jobRepository,
                                                   @Qualifier("importProductFromReportedProductStep") Step importProductFromReportedProductStep) {
        log.info("Import Product from ReportedProduct by Brand Job Build Configuration");

        return new JobBuilder("ImportProductFromReportedProductJob", jobRepository)
                .start(importProductFromReportedProductStep)
                .incrementer(new RunIdIncrementer())
                .listener(new ImportProductFromReportedProductListener())
                .build();
    }

    @Bean
    @JobScope
    public Step importProductFromReportedProductStep(JobRepository jobRepository,
                                                     PlatformTransactionManager transactionManager) throws Exception {
        log.info("Import Product from ReportedProduct by Brand Step Build Configuration");

        return new StepBuilder("ImportProductFromReportedProductStep", jobRepository)
                .<Brand, List<Product>>chunk(100, transactionManager)
                .reader(importProductFromReportedProductBrandReader())
                .processor(reportedProductToProductListProcessor)
                .writer(productListWriter())
                .faultTolerant()
                .retryLimit(2)
                .retry(ResourceAccessException.class)
                .skipLimit(10)
                .skip(DataIntegrityViolationException.class)
                .build();
    }

    /**
     * @return Brand 를 읽어오는 JpaPagingReader
     */
    @Bean
    @StepScope
    public JpaPagingItemReader<Brand> importProductFromReportedProductBrandReader() throws Exception {
        JpaPagingItemReader<Brand> itemReader = new JpaPagingItemReader<>();
        itemReader.setPageSize(50);
        itemReader.setEntityManagerFactory(emf);
        itemReader.setQueryString("select b from Brand b");
        itemReader.setName("jpaPagingBrandReader");
        itemReader.afterPropertiesSet();

        return itemReader;
    }


    /**
     * List 를 저장하기 위한 JpaListWriter. 컴포지트 패턴과 유사하게 구현함.
     */
    private JpaProductListWriter<Product> productListWriter() {
        JpaItemWriter<Product> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);

        return new JpaProductListWriter<>(writer);
    }

//-- Execution Listener --//

    public static class ImportProductFromReportedProductListener implements JobExecutionListener {
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

