package com.kernel360.modulebatch.product.job.core;

import com.kernel360.brand.entity.Brand;
import com.kernel360.modulebatch.product.job.JpaProductListWriter;
import com.kernel360.modulebatch.product.job.infra.ConcernedProductToProductListItemProcessor;
import com.kernel360.product.entity.Product;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
//@ComponentScan("com.kernel360.product")
public class ImportProductFromConcernedProductJobConfig {

    private final ConcernedProductToProductListItemProcessor concernedProductToProductListItemProcessor;

    private final EntityManagerFactory emf;

    @Bean
    public Job importProductFromConcernedProductJob(JobRepository jobRepository,
                                                    @Qualifier("importProductFromConcernedProductStep") Step importProductFromConcernedProductStep) {

        return new JobBuilder("importProductFromConcernedProductJob", jobRepository)
                .start(importProductFromConcernedProductStep)
                .listener(new importProductFromConcernedProductJobListener())
                .build();
    }

    @Bean
    @JobScope
    public Step importProductFromConcernedProductStep(JobRepository jobRepository,
                                                      PlatformTransactionManager transactionManager) {

        return new StepBuilder("importProductFromConcernedProductStep", jobRepository)
                .<Brand, List<Product>>chunk(10, transactionManager)
                .listener(new importProductFromConcernedProductStepListener())
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

    //-- Execution Listener --//

    public static class importProductFromConcernedProductJobListener implements JobExecutionListener {
        @Override
        public void beforeJob(JobExecution jobExecution) {
            log.info("{} starts", jobExecution.getJobInstance().getJobName());
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            log.info("{} ends", jobExecution.getJobInstance().getJobName());
        }
    }


    public static class importProductFromConcernedProductStepListener implements StepExecutionListener {
        @Override
        public void beforeStep(StepExecution stepExecution) {
            log.info("{} starts", stepExecution.getStepName());
        }

        @Override
        public ExitStatus afterStep(StepExecution stepExecution) {
            log.info("StepExecutionListener - afterStep, step name: {}, status: {}", stepExecution.getStepName(),
                    stepExecution.getStatus());
            log.info(
                    "StepExecutionListener - ReadCount: {}, WriteCount: {}, FilterCount: {}, ReadSkipCount: {}, ProcessSkipCount: {}, WriteSkipCount: {}",
                    stepExecution.getReadCount(), stepExecution.getWriteCount(), stepExecution.getFilterCount(),
                    stepExecution.getReadSkipCount(), stepExecution.getProcessSkipCount(),
                    stepExecution.getWriteSkipCount());
            return StepExecutionListener.super.afterStep(stepExecution);
        }
    }
}
