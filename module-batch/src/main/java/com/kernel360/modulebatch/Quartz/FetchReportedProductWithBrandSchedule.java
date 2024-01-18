package com.kernel360.modulebatch.Quartz;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FetchReportedProductWithBrandSchedule implements org.quartz.Job {
    public FetchReportedProductWithBrandSchedule() {}

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) jobDataMap.get("applicationContext");

        JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
        Job fetchReportedProductFromBrandJob = (Job)applicationContext.getBean("fetchReportedProductFromBrandJob");
        Job fetchReportedProductDetailJob = (Job)applicationContext.getBean("fetchReportedProductDetailJob");

        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("DATETIME", LocalDateTime.now().toString())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(fetchReportedProductFromBrandJob, jobParameters);

            // 첫 번째 작업이 완료되면 두 번째 작업 실행
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("{} by Quartz Scheduler Finished", fetchReportedProductFromBrandJob.getName());
                log.info("Starting {} by Quartz Scheduler", fetchReportedProductDetailJob.getName());
                jobLauncher.run(fetchReportedProductDetailJob, jobParameters);
            }
        } catch (org.springframework.batch.core.JobExecutionException e) {
            log.error("JobExecutionException 예외 발생 ", e);
        }finally {
            log.info("{} by Quartz Scheduler Finished", fetchReportedProductDetailJob.getName());
        }
    }
}
