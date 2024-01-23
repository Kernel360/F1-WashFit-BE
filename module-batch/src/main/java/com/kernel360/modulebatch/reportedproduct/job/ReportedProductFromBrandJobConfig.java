package com.kernel360.modulebatch.reportedproduct.job;

import com.kernel360.brand.entity.Brand;
import com.kernel360.modulebatch.reportedproduct.client.ReportedProductFromBrandClient;
import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDto;
import com.kernel360.modulebatch.reportedproduct.service.ReportedProductService;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReportedProductFromBrandJobConfig {

    private final ReportedProductFromBrandClient client;

    private final ReportedProductService service;

    private final EntityManagerFactory emf;


    @Bean
    public Job fetchReportedProductFromBrandJob(JobRepository jobRepository,
                                                @Qualifier("fetchReportedProductFromBrandStep") Step fetchReportedProductFromBrandStep) {

        return new JobBuilder("fetchReportedProductFromBrandJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(fetchReportedProductFromBrandStep)
                .build();
    }


    @Bean
    public Step fetchReportedProductFromBrandStep(JobRepository jobRepository,
                                                  PlatformTransactionManager transactionManager) throws Exception {

        return new StepBuilder("fetchReportedProductFromBrandStep", jobRepository)
                .<Brand, List<ReportedProductDto>>chunk(10, transactionManager)
                .reader(readBrand()) // brand 목록을 읽어와서 전달
                .processor(itemProcessor()) // 브랜드 정보를 통해서 API 요청, reportedProductDto 리스트 반환
                .writer(reportedProductListItemWriter())
                .faultTolerant()
                .retryLimit(2)
                .retry(ResourceAccessException.class)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Brand> readBrand() throws Exception {
        JpaPagingItemReader<Brand> jpaPagingItemReader = new JpaPagingItemReader<>();
        jpaPagingItemReader.setEntityManagerFactory(emf);
        jpaPagingItemReader.setQueryString("SELECT b FROM Brand b");
        jpaPagingItemReader.afterPropertiesSet();

        return jpaPagingItemReader;
    }


    @Bean
    @StepScope
    public FetchReportedProductListFromBrandItemProcessor itemProcessor() {
        return new FetchReportedProductListFromBrandItemProcessor(client, service);
    }

    @Bean
    @StepScope
    public ReportedProductListItemWriter reportedProductListItemWriter() {
        return new ReportedProductListItemWriter(service);
    }
}
