package com.kernel360.modulebatch.violatedproduct.job.core;

import com.kernel360.ecolife.entity.ViolatedProduct;
import com.kernel360.modulebatch.violatedproduct.job.infra.FetchViolatedProductDetailItemProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FetchViolatedProductDetailJobConfig {

    private final FetchViolatedProductDetailItemProcessor fetchViolatedProductDetailItemProcessor;

    private final EntityManagerFactory emf;
    private final int chunkSize = 100;

    @Bean
    public Job fetchViolatedProductDetailJob(JobRepository jobRepository, Step fetchViolatedProductDetailStep) {

        return new JobBuilder("fetchViolatedProductDetailJob", jobRepository)
                .start(fetchViolatedProductDetailStep)
                .incrementer(new RunIdIncrementer())
                .listener(new FetchViolatedProductDetailJobListener())
                .build();
    }

    @Bean
    public Step fetchViolatedProductDetailStep(JobRepository jobRepository,
                                               PlatformTransactionManager transactionManager) {

        return new StepBuilder("fetchViolatedProductDetailStep", jobRepository)
                .<ViolatedProduct, ViolatedProduct>chunk(chunkSize, transactionManager)
                .reader(fetchViolatedProductDetailJpaCursorItemReader())
                .processor(fetchViolatedProductDetailItemProcessor)
                .writer(fetchViolatedProductDetailJpaItemWriter())
                .listener(new FetchViolatedProductDetailStepListener())
                .faultTolerant()
                .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<ViolatedProduct> fetchViolatedProductDetailJpaCursorItemReader() {
        String jpql = "SELECT vp FROM ViolatedProduct vp WHERE vp.actionCn is null";

        return new JpaCursorItemReaderBuilder<ViolatedProduct>()
                .entityManagerFactory(emf)
                .name("fetchViolatedProductDetailJpaPagingItemReader")
                .queryString(jpql)
                .build();
    }

    @Bean
    @StepScope
    public JpaItemWriter<ViolatedProduct> fetchViolatedProductDetailJpaItemWriter() {

        return new JpaItemWriterBuilder<ViolatedProduct>()
                .entityManagerFactory(emf)
                .build();
    }

    //-- Execution Listener --//
    public static class FetchViolatedProductDetailJobListener implements JobExecutionListener {
        @Override
        public void beforeJob(JobExecution jobExecution) {
            log.info("{} starts", jobExecution.getJobInstance().getJobName());
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            log.info("{} ends", jobExecution.getJobInstance().getJobName());
        }
    }

    public static class FetchViolatedProductDetailStepListener implements StepExecutionListener {
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
