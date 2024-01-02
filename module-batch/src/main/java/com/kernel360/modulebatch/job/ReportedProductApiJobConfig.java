package com.kernel360.modulebatch.job;

import com.kernel360.modulebatch.JobCompletionNotificationListener;
import com.kernel360.modulebatch.controller.ReportedProductApiClient;
import com.kernel360.product.entity.ReportedProduct;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Slf4j
@Configuration
public class ReportedProductApiJobConfig {

    private final ReportedProductApiClient client;

    public ReportedProductApiJobConfig(ReportedProductApiClient client) {
        this.client = client;
    }


    @Bean
    public Job fetchReportedProductListJob(JobRepository jobRepository, JobCompletionNotificationListener listener,
                                           Step step) {
        return new JobBuilder("fetchReportedProductListJob", jobRepository)
                .listener(listener)
                .start(step)
                .build();
    }

    @Bean
    public Step fetchReportedProductListStep(JobRepository jobRepository, DataSourceTransactionManager manager,
                                             Tasklet tasklet) {
        return new StepBuilder("fetchReportedProductListStep", jobRepository)
                .tasklet(tasklet, manager)
                .build();
    }

    @Bean
    public Tasklet fetchReportedProductListTasklet(JobRepository jobRepository, EntityManager entityManager) {
        return (contribution, chunkContext) -> {
            ResponseEntity<List<ReportedProduct>> result = client.getRawData(1);
            persistReportedProducts(entityManager, Objects.requireNonNull(result.getBody()));
            return RepeatStatus.FINISHED;
        };
    }

    private void persistReportedProducts(EntityManager entityManager, List<ReportedProduct> products) {
        for (ReportedProduct product : products) {
            entityManager.persist(product);
        }
    }
}