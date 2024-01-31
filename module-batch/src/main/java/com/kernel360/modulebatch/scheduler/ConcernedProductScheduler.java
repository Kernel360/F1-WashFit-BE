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
@Profile({"local", "dev"})
public class ConcernedProductScheduler {
    private final Job fetchConcernedProductListFromBrandJob;
    private final Job fetchConcernedProductDetailJob;
    private final Job importProductFromConcernedProductJob;
    private final JobLauncher jobLauncher;

    @Autowired
    public ConcernedProductScheduler(
            @Qualifier("fetchConcernedProductListFromBrandJob") Job fetchConcernedProductListJob,
            @Qualifier("fetchConcernedProductDetailJob") Job fetchConcernedProductDetailJob,
            @Qualifier("importProductFromConcernedProductJob") Job importProductFromConcernedProductJob,
            JobLauncher jobLauncher) {

        this.fetchConcernedProductListFromBrandJob = fetchConcernedProductListJob;
        this.fetchConcernedProductDetailJob = fetchConcernedProductDetailJob;
        this.importProductFromConcernedProductJob = importProductFromConcernedProductJob;
        this.jobLauncher = jobLauncher;
    }

    /**
     * 매주 목요일 19시 00분 실행
     */
    @Scheduled(cron = "0 0 19 * * THU", zone = "Asia/Seoul")
    public void executeFetchConcernedProductListJob() {
        executeJob(fetchConcernedProductListFromBrandJob);
    }

    /**
     * 매주 목요일 19시 30분 실행
     */
    @Scheduled(cron = "0 30 19 * * THU", zone = "Asia/Seoul")
    public void executeFetchConcernedProductDetailJob() {
        executeJob(fetchConcernedProductDetailJob);
    }

    /**
     * 매주 목요일 20시 00분 실행
     */
    @Scheduled(cron = "0 0 20 * * THU", zone = "Asia/Seoul")
    public void executeImportProductFromConcernedProductJob() {
        executeJob(importProductFromConcernedProductJob);
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
