package com.kernel360.modulebatch.concernedproduct.job.core;

import com.kernel360.brand.entity.Brand;
import com.kernel360.modulebatch.global.BaseJobExecutionListener;
import com.kernel360.modulebatch.concernedproduct.dto.ConcernedProductDto;
import com.kernel360.modulebatch.concernedproduct.job.infra.ConcernedProductListItemProcessor;
import com.kernel360.modulebatch.concernedproduct.job.infra.ConcernedProductListItemWriter;
import com.kernel360.modulebatch.global.BaseStepExecutionListener;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = FetchConcernedProductListFromBrandJobConfig.JOB_NAME)
public class FetchConcernedProductListFromBrandJobConfig {
    public static final String JOB_NAME = "FetchConcernedProductListFromBrandJob";
    private final ConcernedProductListItemProcessor concernedProductListItemProcessor;
    private final ConcernedProductListItemWriter concernedProductListItemWriter;
    private final BaseJobExecutionListener baseJobExecutionListener;
    private final BaseStepExecutionListener baseStepExecutionListener;
    private final EntityManagerFactory emf;

    @Bean
    public Job FetchConcernedProductListFromBrandJob(JobRepository jobRepository,
                                                     @Qualifier("fetchConcernedProductListFromBrandStep") Step fetchConcernedProductListStep) {
        log.info("FetchConcernedProductListFromBrandJobConfig initialized");

        return new JobBuilder(JOB_NAME, jobRepository)
                .start(fetchConcernedProductListStep)
                .listener(baseJobExecutionListener)
                .build();
    }


    //-- List Step --//
    @Bean
    public Step fetchConcernedProductListFromBrandStep(JobRepository jobRepository,
                                                       PlatformTransactionManager transactionManager) throws Exception {

        return new StepBuilder("fetchConcernedProductListFromBrandStep", jobRepository)
                .<Brand, List<ConcernedProductDto>>chunk(1, transactionManager)
                .listener(baseStepExecutionListener)
                .reader(readBrandForConcernedProduct())
                .processor(concernedProductListItemProcessor)
                .writer(concernedProductListItemWriter)
                .build();
    }

    @Bean
    @StepScope
    @Qualifier("readBrandForConcernedProduct")
    public JpaPagingItemReader<Brand> readBrandForConcernedProduct() throws Exception {
        JpaPagingItemReader<Brand> jpaPagingItemReader = new JpaPagingItemReader<>();
        jpaPagingItemReader.setEntityManagerFactory(emf);
        jpaPagingItemReader.setQueryString("SELECT b FROM Brand b");
        jpaPagingItemReader.afterPropertiesSet();

        return jpaPagingItemReader;
    }

}
