package com.kernel360.modulebatch.product.job.core;

import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.modulebatch.global.BaseJobExecutionListener;
import com.kernel360.modulebatch.global.BaseStepExecutionListener;
import com.kernel360.modulebatch.product.job.infra.FilterUnusedProductTasklet;
import com.kernel360.modulebatch.product.job.infra.ReportedProductToProductItemProcessor;
import com.kernel360.product.entity.Product;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
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
@ConditionalOnProperty(name = "job.name", havingValue = ImportProductFromReportedProductJobConfig.JOB_NAME)
public class ImportProductFromReportedProductJobConfig {

    public static final String JOB_NAME = "ImportProductFromReportedProductJob";
    private final ReportedProductToProductItemProcessor reportedProductToProductItemProcessor;
    private final FilterUnusedProductTasklet filterUnusedProductTasklet;
    private final BaseJobExecutionListener baseJobExecutionListener;
    private final BaseStepExecutionListener baseStepExecutionListener;
    private final EntityManagerFactory emf;

    @Bean
    public Job ImportProductFromReportedProductJob(JobRepository jobRepository,
                                                   @Qualifier("importProductFromReportedProductStep") Step importProductFromReportedProductStep,
                                                   @Qualifier("filterUnusedProductStep") Step filterUnusedProductStep) {
        log.info("Import Product from ReportedProduct by Brand Job Build Configuration");

        return new JobBuilder(JOB_NAME, jobRepository)
                .start(importProductFromReportedProductStep)
                .next(filterUnusedProductStep)
                .incrementer(new RunIdIncrementer())
                .listener(baseJobExecutionListener)
                .build();
    }

    @Bean
    @JobScope
    public Step importProductFromReportedProductStep(JobRepository jobRepository,
                                                     PlatformTransactionManager transactionManager) throws Exception {
        log.info("Import Product from ReportedProduct by Brand Step Build Configuration");

        return new StepBuilder("ImportProductFromReportedProductStep", jobRepository)
                .<ReportedProduct, Product>chunk(100, transactionManager)
                .listener(baseStepExecutionListener)
                .reader(importProductFromReportedProductItemReader())
                .processor(reportedProductToProductItemProcessor)
                .writer(productJpaItemWriter())
                .faultTolerant()
                .retryLimit(2)
                .retry(ResourceAccessException.class)
                .skipLimit(10)
                .skip(DataIntegrityViolationException.class)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<ReportedProduct> importProductFromReportedProductItemReader() throws Exception {
        String sqlQuery = "SELECT rp.* FROM reported_product rp "
                + "JOIN (SELECT prdt_nm, MAX(issu_date) AS max_issu_date "
                + "FROM reported_product GROUP BY prdt_nm) max_dates "
                + "ON rp.prdt_nm = max_dates.prdt_nm AND rp.issu_date = max_dates.max_issu_date "
                + "ORDER BY max_dates.max_issu_date DESC";
        JpaNativeQueryProvider<ReportedProduct> queryProvider = new JpaNativeQueryProvider<>();
        queryProvider.setSqlQuery(sqlQuery);
        queryProvider.setEntityClass(ReportedProduct.class);
        queryProvider.afterPropertiesSet();

        return new JpaPagingItemReaderBuilder<ReportedProduct>()
                .name("importProductFromReportedProductItemReader")
                .pageSize(100)
                .queryProvider(queryProvider)
                .entityManagerFactory(emf)
                .build();
    }

    @Bean
    @StepScope
    public JpaItemWriter<Product> productJpaItemWriter() {
        return new JpaItemWriterBuilder<Product>()
                .entityManagerFactory(emf)
                .build();
    }

    @Bean
    public Step filterUnusedProductStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        return new StepBuilder("filterUnusedProductStep", jobRepository)
                .tasklet(filterUnusedProductTasklet, transactionManager)
                .listener(baseStepExecutionListener)
                .build();
    }
}

