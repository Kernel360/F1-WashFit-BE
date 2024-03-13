package com.kernel360.modulebatch.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaseJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("{} starts", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("{} ends", jobExecution.getJobInstance().getJobName());

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            System.exit(1);
        }
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.exit(0);
        }
    }
}
