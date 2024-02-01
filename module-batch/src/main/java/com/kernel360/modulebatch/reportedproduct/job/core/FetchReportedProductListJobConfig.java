package com.kernel360.modulebatch.reportedproduct.job.core;

import com.kernel360.modulebatch.reportedproduct.dto.ReportedProductDto;
import com.kernel360.modulebatch.reportedproduct.job.infra.ReportedProductListItemReader;
import com.kernel360.modulebatch.reportedproduct.job.infra.ReportedProductListItemWriter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FetchReportedProductListJobConfig {

    private final ReportedProductListItemReader reportedProductListItemReader;

    private final ReportedProductListItemWriter reportedProductListItemWriter;


    @Bean
    public Job fetchReportedProductJob(JobRepository jobRepository,
                                       @Qualifier("fetchReportedProductListStep") Step fetchReportedProductListStep) {
        log.info("Fetch ReportedProduct List Job Build Configuration");
        return new JobBuilder("fetchReportedProductJob", jobRepository)
                .start(fetchReportedProductListStep)
                .incrementer(new RunIdIncrementer())
                .listener(new FetchReportedProductExecutionListener())
                .build();
    }


    //-- List Step --//
    @Bean
    public Step fetchReportedProductListStep(JobRepository jobRepository,
                                             PlatformTransactionManager transactionManager) {
        log.info("Fetch ReportedProduct List Step Build Configuration");

        return new StepBuilder("fetchReportedProductListStep", jobRepository)
                .<List<ReportedProductDto>, List<ReportedProductDto>>chunk(10, transactionManager)
                .reader(reportedProductListItemReader) // API 요청, 응답을 DTO 리스트로 반환
                .writer(reportedProductListItemWriter) // DTO 리스트 입력, 저장
                .faultTolerant()
                .retryLimit(2)
                .retry(ResourceAccessException.class)
                .skipLimit(10)
                .skip(DataIntegrityViolationException.class)
                .build();
    }

    //-- Execution Listener --//

    public static class FetchReportedProductExecutionListener implements JobExecutionListener {
        @Override
        public void beforeJob(JobExecution jobExecution) {
            log.info("{} starts", jobExecution.getJobInstance().getJobName());
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            log.info("{} ends", jobExecution.getJobInstance().getJobName());
        }
    }

}