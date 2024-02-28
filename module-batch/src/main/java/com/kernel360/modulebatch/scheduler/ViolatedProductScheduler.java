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
@Profile({"local", "dev", "prod"})
public class ViolatedProductScheduler {
    private final Job fetchViolatedProductListJob;
    private final Job fetchViolatedProductDetailJob;

    private final Job updateProductFromViolatedProductJob;

    private final JobLauncher jobLauncher;

    @Autowired
    public ViolatedProductScheduler(
            @Qualifier("fetchViolatedProductListJob") Job fetchViolatedProductListJob,
            @Qualifier("fetchViolatedProductDetailJob") Job fetchViolatedProductDetailJob,
            @Qualifier("updateProductFromViolatedProductJob") Job updateProductFromViolatedProductJob,
            JobLauncher jobLauncher) {
        this.fetchViolatedProductListJob = fetchViolatedProductListJob;
        this.fetchViolatedProductDetailJob = fetchViolatedProductDetailJob;
        this.updateProductFromViolatedProductJob = updateProductFromViolatedProductJob;
        this.jobLauncher = jobLauncher;
    }


    /**
     * 매주 목요일 새벽 1시 실행
     */
    @Scheduled(cron = "0 0 1 * * THU", zone = "Asia/Seoul")
    public void executeFetchViolatedProductListJob() {
        executeJob(fetchViolatedProductListJob);
    }

    /**
     * 매주 목요일 2시, 14시 실행
     */
    @Scheduled(cron = "0 0 2,14 * * THU", zone = "Asia/Seoul")
    public void executeFetchViolatedProductDetailJob() {
        executeJob(fetchViolatedProductDetailJob);
    }

    /**
     * 매주 목요일 2시 30분, 14시 30분 실행
     */
    @Scheduled(cron = "0 30 2,14 * * THU", zone = "Asia/Seoul")
    public void executeUpdateProductFromViolatedProductJob() {
        executeJob(updateProductFromViolatedProductJob);
    }

    private synchronized void executeJob(Job job) {

        try {
            jobLauncher.run(
                    job,
                    new JobParametersBuilder()
                            .addString("DATETIME", LocalDateTime.now().toString())
                            .addString("PRODUCT_ARM_CODE", "07")
                            .toJobParameters()
            );
        } catch (JobExecutionException je) {
            log.error("JobExecutionException Occurred", je);
        }
    }
}
