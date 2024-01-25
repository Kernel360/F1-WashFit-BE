package com.kernel360.modulebatch.scheduler;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"local", "prod"})
public class ConcernedProductScheduler {
    private final Job fetchConcernedProductListFromBrandJob;
    private final JobLauncher jobLauncher;

    @Autowired
    public ConcernedProductScheduler(
            @Qualifier("fetchConcernedProductListFromBrandJob") Job fetchConcernedProductListJob,
            JobLauncher jobLauncher) {

        this.fetchConcernedProductListFromBrandJob = fetchConcernedProductListJob;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(cron = "0 30 11,13 * * TUE", zone = "Asia/Seoul")
    public void executeFetchConcernedProductListJob() {
        executeJob(fetchConcernedProductListFromBrandJob);
    }

    private synchronized void executeJob(Job job) {

        try {
            jobLauncher.run(
                    job,
                    new JobParametersBuilder()
                            .addString("DATETIME", LocalDateTime.now().toString())
                            .toJobParameters()
            );
        } catch (JobExecutionException je) {
            log.error("JobExecutionException Occurred", je);
        }
    }
}
