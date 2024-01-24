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
public class ReportedProductScheduler {

    private final Job importProductFromReportedProductJob;
    private final Job fetchReportedProductFromBrandJob;
    private final Job fetchReportedProductDetailJob;
    private final JobLauncher jobLauncher;

    @Autowired
    public ReportedProductScheduler(
            @Qualifier("importProductFromReportedProductJob") Job importProductFromReportedProductJob,
            @Qualifier("fetchReportedProductDetailJob") Job fetchReportedProductDetailJob,
            @Qualifier("fetchReportedProductFromBrandJob") Job fetchReportedProductFromBrandJob,
            JobLauncher jobLauncher) {

        this.importProductFromReportedProductJob = importProductFromReportedProductJob;
        this.fetchReportedProductDetailJob = fetchReportedProductDetailJob;
        this.fetchReportedProductFromBrandJob = fetchReportedProductFromBrandJob;
        this.jobLauncher = jobLauncher;
    }

    /**
     * Brand 테이블의 brand 정보에 매칭되는 신고대상 생활화학제품 목록 정보 읽어오기
     */
    @Scheduled(cron = "0 0 1 * * WED", zone = "Asia/Seoul")
    public void fetchReportedProductFromBrandJob() {
        executeJob(fetchReportedProductFromBrandJob);
    }

    /**
     * 초록누리 API 를 통해 신고대상 생활화학제품 상세 정보 읽어오기
     */
    @Scheduled(cron = "0 0 2,14 * * WED", zone = "Asia/Seoul")
    public void executeFetchReportedProductDetailJob() {
        executeJob(fetchReportedProductDetailJob);
    }

    /**
     * Reported Product 테이블에서 Product 테이블로 필요한 제품 정보 옮기기
     */
    @Scheduled(cron = "0 30 2,14 * * WED", zone = "Asia/Seoul")
    public void executeImportProductJob() {
        executeJob(importProductFromReportedProductJob);
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