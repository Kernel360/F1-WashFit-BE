package com.kernel360.modulebatch.product.job.core;

import com.kernel360.brand.entity.Brand;
import com.kernel360.modulebatch.global.BaseJobExecutionListener;
import com.kernel360.modulebatch.global.BaseStepExecutionListener;
import com.kernel360.modulebatch.product.job.infra.ConcernedProductToProductListItemProcessor;
import com.kernel360.modulebatch.product.job.infra.JpaProductListWriter;
import com.kernel360.product.entity.Product;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = ImportProductFromConcernedProductJobConfig.JOB_NAME)
public class ImportProductFromConcernedProductJobConfig {
    public static final String JOB_NAME = "ImportProductFromConcernedProductJob";
    private final ConcernedProductToProductListItemProcessor concernedProductToProductListItemProcessor;
    private final BaseJobExecutionListener baseJobExecutionListener;
    private final BaseStepExecutionListener baseStepExecutionListener;
    private final EntityManagerFactory emf;

    @Bean
    public Job ImportProductFromConcernedProductJob(JobRepository jobRepository,
                                                    @Qualifier("importProductFromConcernedProductStep") Step importProductFromConcernedProductStep) {

        return new JobBuilder(JOB_NAME, jobRepository)
                .start(importProductFromConcernedProductStep)
                .listener(baseJobExecutionListener)
                .build();
    }

    @Bean
    @JobScope
    public Step importProductFromConcernedProductStep(JobRepository jobRepository,
                                                      PlatformTransactionManager transactionManager) {

        return new StepBuilder("importProductFromConcernedProductStep", jobRepository)
                .<Brand, List<Product>>chunk(10, transactionManager)
                .listener(baseStepExecutionListener)
                .reader(importProductFromConcernedProductJpaPagingItemReader())
                .processor(concernedProductToProductListItemProcessor)
                .writer(productListWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Brand> importProductFromConcernedProductJpaPagingItemReader() {
        String jpql = "SELECT b FROM Brand b";

        return new JpaPagingItemReaderBuilder<Brand>()
                .pageSize(100)
                .queryString(jpql)
                .entityManagerFactory(emf)
                .name("importProductFromConcernedProductJpaPagingItemReader")
                .build();
    }

    private JpaProductListWriter<Product> productListWriter() {
        JpaItemWriter<Product> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);

        return new JpaProductListWriter<>(writer);
    }
}
