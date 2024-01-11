package com.kernel360.modulebatch.scheduler;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"local", "prod"})
public class ReportedProductScheduler {
    private static final String DATETIME = "datetime";
    private static final long FIXED_DELAY_IN_MS = 60 * 60 * 24 * 7 * 1000L;
    private final Job fetchReportedProductJob;
    private final JobLauncher jobLauncher;

    @Autowired
    public ReportedProductScheduler(Job fetchReportedProductJob,
                                    JobLauncher jobLauncher) {
        this.fetchReportedProductJob = fetchReportedProductJob;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(initialDelay = 0, fixedDelay = FIXED_DELAY_IN_MS)
    public void executeFetchReportedProductJob() {
        executeJob(fetchReportedProductJob);
    }

    private void executeJob(Job job) {
        try {
            jobLauncher.run(
                    job,
                    new JobParametersBuilder()
                            .addString(DATETIME, LocalDateTime.now().toString())
                            .toJobParameters()
            );
        } catch (JobExecutionException je) {
            log.error("JobExecutionException Occurred", je);
        }
    }

}