package com.kernel360.modulebatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing
@EnableScheduling
@EntityScan("com.kernel360.ecolife.entity")
@EnableJpaRepositories("com.kernel360.ecolife.repository")
@SpringBootApplication(scanBasePackages = {"com.kernel360"})
public class ModuleBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleBatchApplication.class, args);
    }

}
