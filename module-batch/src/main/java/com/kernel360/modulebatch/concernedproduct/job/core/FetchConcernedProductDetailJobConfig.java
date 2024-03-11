package com.kernel360.modulebatch.concernedproduct.job.core;

import com.kernel360.ecolife.entity.ConcernedProduct;
import com.kernel360.modulebatch.concernedproduct.job.infra.ConcernedProductDetailItemProcessor;
import com.kernel360.modulebatch.global.BaseJobExecutionListener;
import com.kernel360.modulebatch.global.BaseStepExecutionListener;
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
@ConditionalOnProperty(name = "job.name", havingValue = FetchConcernedProductDetailJobConfig.JOB_NAME)
public class FetchConcernedProductDetailJobConfig {
    public static final String JOB_NAME = "FetchConcernedProductDetailJob";
    private final ConcernedProductDetailItemProcessor concernedProductDetailItemProcessor;
    private final BaseJobExecutionListener baseJobExecutionListener;
    private final BaseStepExecutionListener baseStepExecutionListener;
    private final EntityManagerFactory emf;

    @Bean
    public Job FetchConcernedProductDetailJob(JobRepository jobRepository,
                                              @Qualifier("fetchConcernedProductDetailStep") Step fetchConcernedProductDetailStep) {
        log.info("FetchConcernedProductDetailJobConfig initialized");

        return new JobBuilder(JOB_NAME, jobRepository)
                .start(fetchConcernedProductDetailStep)
                .incrementer(new RunIdIncrementer())
                .listener(baseJobExecutionListener)
                .build();
    }

    @Bean
    public Step fetchConcernedProductDetailStep(JobRepository jobRepository,
                                                PlatformTransactionManager transactionManager) {

        return new StepBuilder("fetchConcernedProductDetailStep", jobRepository)
                .<ConcernedProduct, ConcernedProduct>chunk(100, transactionManager)
                .listener(baseStepExecutionListener)
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
}
