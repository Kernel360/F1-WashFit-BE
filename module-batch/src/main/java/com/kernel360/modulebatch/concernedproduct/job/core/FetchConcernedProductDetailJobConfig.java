package com.kernel360.modulebatch.concernedproduct.job.core;

import com.kernel360.ecolife.entity.ConcernedProduct;
import com.kernel360.modulebatch.concernedproduct.job.infra.ConcernedProductDetailItemProcessor;
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
public class FetchConcernedProductDetailJobConfig {

    private final ConcernedProductDetailItemProcessor concernedProductDetailItemProcessor;

    private final EntityManagerFactory emf;

    @Bean
    public Job fetchConcernedProductDetailJob(JobRepository jobRepository,
                                              @Qualifier("fetchConcernedProductDetailStep") Step fetchConcernedProductDetailStep) {
        log.info("FetchConcernedProductDetailJobConfig initialized");

        return new JobBuilder("fetchConcernedProductDetailJobConfig", jobRepository)
                .start(fetchConcernedProductDetailStep)
                .incrementer(new RunIdIncrementer())
                .listener(new FetchConcernedProductDetailJobListener())
                .build();
    }

    @Bean
    public Step fetchConcernedProductDetailStep(JobRepository jobRepository,
                                                PlatformTransactionManager transactionManager) {

        return new StepBuilder("fetchConcernedProductDetailStep", jobRepository)
                .<ConcernedProduct, ConcernedProduct>chunk(100, transactionManager)
                .listener(new FetchConcernedProductDetailStepListener())
                .reader(concernedProductDetailItemReader())
                .processor(concernedProductDetailItemProcessor)
                .writer(concernedProductItemWriter(emf))
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<ConcernedProduct> concernedProductDetailItemReader() {
        String jpql = "SELECT cp FROM ConcernedProduct cp";

        return new JpaPagingItemReaderBuilder<ConcernedProduct>()
                .queryString(jpql)
                .entityManagerFactory(emf)
                .name("concernedProductDetailJpaItemReader")
                // FIXME :: 트랜잭션 관리 개선, 작업의 병렬성 조정, 사용자 정의 Reader 를 만드는 식으로 페이지 크기에 따른 읽기 문제를 해결해야 함.
                .pageSize(1000)
                .build();
    }

    @Bean
    @StepScope
    public JpaItemWriter<ConcernedProduct> concernedProductItemWriter(EntityManagerFactory emf) {
        JpaItemWriter<ConcernedProduct> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(emf);

        return jpaItemWriter;
    }

    //-- Execution Listener --//

    public static class FetchConcernedProductDetailJobListener implements JobExecutionListener {
        @Override
        public void beforeJob(JobExecution jobExecution) {
            log.info("{} starts", jobExecution.getJobInstance().getJobName());
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            log.info("{} ends", jobExecution.getJobInstance().getJobName());
        }
    }

    public static class FetchConcernedProductDetailStepListener implements StepExecutionListener{
        @Override
        public void beforeStep(StepExecution stepExecution) {
            log.info("{} starts", stepExecution.getStepName());
        }

        @Override
        public ExitStatus afterStep(StepExecution stepExecution) {
            log.info("StepExecutionListener - afterStep, step name: {}, status: {}", stepExecution.getStepName(), stepExecution.getStatus());
            log.info("StepExecutionListener - ReadCount: {}, WriteCount: {}, FilterCount: {}, ReadSkipCount: {}, ProcessSkipCount: {}, WriteSkipCount: {}",
                    stepExecution.getReadCount(), stepExecution.getWriteCount(), stepExecution.getFilterCount(),
                    stepExecution.getReadSkipCount(), stepExecution.getProcessSkipCount(), stepExecution.getWriteSkipCount());
            return StepExecutionListener.super.afterStep(stepExecution);
        }
    }
}
