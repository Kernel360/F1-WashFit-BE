package com.kernel360.modulebatch.violatedproduct.job.core;

import com.kernel360.modulebatch.global.BaseJobExecutionListener;
import com.kernel360.modulebatch.violatedproduct.dto.ViolatedProductDto;
import com.kernel360.modulebatch.violatedproduct.job.infra.FetchViolatedProductListItemReader;
import com.kernel360.modulebatch.violatedproduct.job.infra.FetchViolatedProductListItemWriter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = FetchViolatedProductListJobConfig.JOB_NAME)
public class FetchViolatedProductListJobConfig {

    public static final String JOB_NAME = "FetchViolatedProductListJob";
    private final FetchViolatedProductListItemReader fetchViolatedProductListItemReader;
    private final FetchViolatedProductListItemWriter fetchViolatedProductListItemWriter;
    private final BaseJobExecutionListener baseJobExecutionListener;

    @Bean
    public Job FetchViolatedProductListJob(JobRepository jobRepository,
                                           @Qualifier("fetchViolatedProductListStep") Step fetchViolatedProductListStep) {
        return new JobBuilder("FetchViolatedProductListJob", jobRepository)
                .start(fetchViolatedProductListStep)
                .listener(baseJobExecutionListener)
                .build();
    }

    @Bean
    public Step fetchViolatedProductListStep(JobRepository jobRepository,
                                             PlatformTransactionManager transactionManager) {

        return new StepBuilder(JOB_NAME, jobRepository)
                .<List<ViolatedProductDto>, List<ViolatedProductDto>>chunk(10, transactionManager)
                .reader(fetchViolatedProductListItemReader)
                .writer(fetchViolatedProductListItemWriter)
                .listener(new FetchViolatedProductListStepListener())
                .faultTolerant()
                .build();
    }

    //-- Execution Listener --//
    public static class FetchViolatedProductListStepListener implements StepExecutionListener {
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
