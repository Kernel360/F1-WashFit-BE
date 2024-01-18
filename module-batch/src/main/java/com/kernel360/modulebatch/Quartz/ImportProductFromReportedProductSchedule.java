package com.kernel360.modulebatch.Quartz;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImportProductFromReportedProductSchedule implements org.quartz.Job {
    public ImportProductFromReportedProductSchedule() {
    }

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) jobDataMap.get("applicationContext");

        JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
        Job importProductFromReportedProductJob = (Job) applicationContext.getBean(
                "importProductFromReportedProductJob");

        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("DATETIME", LocalDateTime.now().toString())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(importProductFromReportedProductJob, jobParameters);

            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("{} by Quartz Scheduler Finished", importProductFromReportedProductJob.getName());
            }

        } catch (org.springframework.batch.core.JobExecutionException e) {
            log.error("JobExecutionException by Quartz Scheduler Exception occurred ", e);
        }
    }
}
