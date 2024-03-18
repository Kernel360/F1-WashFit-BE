package com.kernel360.modulebatch.reportedproduct.job.core;

import com.kernel360.brand.entity.Brand;
import com.kernel360.modulebatch.global.BaseJobExecutionListener;
import com.kernel360.modulebatch.global.BaseStepExecutionListener;
import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDto;
import com.kernel360.modulebatch.reportedproduct.job.infra.FetchReportedProductListFromBrandItemProcessor;
import com.kernel360.modulebatch.reportedproduct.job.infra.ReportedProductListItemWriter;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = ReportedProductFromBrandJobConfig.JOB_NAME)
public class ReportedProductFromBrandJobConfig {

    public static final String JOB_NAME = "FetchReportedProductFromBrandJob";
    private final ReportedProductListItemWriter reportedProductListItemWriter;
    private final FetchReportedProductListFromBrandItemProcessor fetchReportedProductItemProcessor;
    private final EntityManagerFactory emf;
    private final BaseJobExecutionListener baseJobExecutionListener;
    private final BaseStepExecutionListener baseStepExecutionListener;


    @Bean
    public Job FetchReportedProductFromBrandJob(JobRepository jobRepository,
                                                @Qualifier("fetchReportedProductFromBrandStep") Step fetchReportedProductFromBrandStep) {

        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(fetchReportedProductFromBrandStep)
                .listener(baseJobExecutionListener)
                .build();
    }


    @Bean
    public Step fetchReportedProductFromBrandStep(JobRepository jobRepository,
                                                  PlatformTransactionManager transactionManager) throws Exception {

        return new StepBuilder("fetchReportedProductFromBrandStep", jobRepository)
                .<Brand, List<ReportedProductDto>>chunk(1, transactionManager)
                .listener(baseStepExecutionListener)
                .reader(readBrand()) // brand 목록을 읽어와서 전달
                .processor(fetchReportedProductItemProcessor) // 브랜드 정보를 통해서 API 요청, reportedProductDto 리스트 반환
                .writer(reportedProductListItemWriter)
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
}
