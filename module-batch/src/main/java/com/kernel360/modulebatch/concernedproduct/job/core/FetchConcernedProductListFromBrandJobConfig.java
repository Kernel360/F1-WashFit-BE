package com.kernel360.modulebatch.concernedproduct.job.core;

import com.kernel360.brand.entity.Brand;
import com.kernel360.modulebatch.concernedproduct.client.ConcernedProductListClient;
import com.kernel360.modulebatch.concernedproduct.dto.ConcernedProductDto;
import com.kernel360.modulebatch.concernedproduct.job.infra.ConcernedProductListItemProcessor;
import com.kernel360.modulebatch.concernedproduct.job.infra.ConcernedProductListItemWriter;
import com.kernel360.modulebatch.concernedproduct.service.ConcernedProductService;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class FetchConcernedProductListFromBrandJobConfig {
    private final ConcernedProductListClient client;
    private final ConcernedProductService service;
    private final EntityManagerFactory emf;


    @Bean
    public Job fetchConcernedProductListFromBrandJob(JobRepository jobRepository,
                                            @Qualifier("fetchConcernedProductListFromBrandStep") Step fetchConcernedProductListStep) {

        return new JobBuilder("fetchConcernedProductListFromBrandJob", jobRepository)
                .start(fetchConcernedProductListStep)
                .build();
    }


    //-- List Step --//
    @Bean
    public Step fetchConcernedProductListFromBrandStep(JobRepository jobRepository,
                                              PlatformTransactionManager transactionManager) throws Exception {

        return new StepBuilder("fetchConcernedProductListFromBrandStep", jobRepository)
                .<Brand, List<ConcernedProductDto>>chunk(1, transactionManager)
                .reader(readBrandForConcernedProduct())
                .processor(concernedProductListItemProcessor())
                .writer(concernedProductListItemWriter())
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

    @Bean
    @StepScope
    public ConcernedProductListItemProcessor concernedProductListItemProcessor() {
        return new ConcernedProductListItemProcessor(client, service);
    }

    @Bean
    @StepScope
    public ConcernedProductListItemWriter concernedProductListItemWriter() {
        return new ConcernedProductListItemWriter(service);
    }
}
