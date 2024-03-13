package com.kernel360.modulebatch.violatedproduct.job.core;

import com.kernel360.ecolife.entity.ViolatedProduct;
import com.kernel360.modulebatch.global.BaseJobExecutionListener;
import com.kernel360.modulebatch.global.BaseStepExecutionListener;
import com.kernel360.modulebatch.violatedproduct.job.infra.FetchViolatedProductDetailItemProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = FetchViolatedProductDetailJobConfig.JOB_NAME)
public class FetchViolatedProductDetailJobConfig {

    public static final String JOB_NAME = "FetchViolatedProductDetailJob";
    private final FetchViolatedProductDetailItemProcessor fetchViolatedProductDetailItemProcessor;
    private final BaseJobExecutionListener baseJobExecutionListener;
    private final BaseStepExecutionListener baseStepExecutionListener;
    private final EntityManagerFactory emf;

    @Bean
    public Job FetchViolatedProductDetailJob(JobRepository jobRepository,
                                             @Qualifier("fetchViolatedProductDetailStep") Step fetchViolatedProductDetailStep) {

        return new JobBuilder(JOB_NAME, jobRepository)
                .start(fetchViolatedProductDetailStep)
                .incrementer(new RunIdIncrementer())
                .listener(baseJobExecutionListener)
                .build();
    }

    @Bean
    public Step fetchViolatedProductDetailStep(JobRepository jobRepository,
                                               PlatformTransactionManager transactionManager) {

        return new StepBuilder("fetchViolatedProductDetailStep", jobRepository)
                .<ViolatedProduct, ViolatedProduct>chunk(100, transactionManager)
                .reader(fetchViolatedProductDetailJpaCursorItemReader())
                .processor(fetchViolatedProductDetailItemProcessor)
                .writer(fetchViolatedProductDetailJpaItemWriter())
                .listener(baseStepExecutionListener)
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
}
