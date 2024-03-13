package com.kernel360.modulebatch.product.job.core;

import com.kernel360.modulebatch.global.BaseJobExecutionListener;
import com.kernel360.modulebatch.global.BaseStepExecutionListener;
import com.kernel360.modulebatch.product.dto.ProductJoinDto;
import com.kernel360.modulebatch.product.job.infra.UpdateProductFromViolatedProductItemProcessor;
import com.kernel360.product.entity.Product;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = UpdateProductFromViolatedProductJobConfig.JOB_NAME)
public class UpdateProductFromViolatedProductJobConfig {
    public static final String JOB_NAME = "UpdateProductFromViolatedProductJob";

    private final EntityManagerFactory emf;
    private final UpdateProductFromViolatedProductItemProcessor updateProductItemProcessor;
    private final BaseJobExecutionListener baseJobExecutionListener;
    private final BaseStepExecutionListener baseStepExecutionListener;

    @Bean
    public Job UpdateProductFromViolatedProductJob(JobRepository jobRepository,
                                                   @Qualifier("updateProductFromViolatedProductStep") Step updateProductFromViolatedProductStep) {

        return new JobBuilder(JOB_NAME, jobRepository)
                .start(updateProductFromViolatedProductStep)
                .listener(baseJobExecutionListener)
                .build();
    }

    @Bean
    public Step updateProductFromViolatedProductStep(JobRepository jobRepository,
                                                     PlatformTransactionManager transactionManager) throws Exception {

        return new StepBuilder("updateProductFromViolatedProductStep", jobRepository)
                .<ProductJoinDto, Product>chunk(100, transactionManager)
                .reader(updateProductFromViolatedProductJpaPagingItemReader())
                .processor(updateProductItemProcessor)
                .writer(updateProductFromViolatedProductJpaItemWriter())
                .listener(baseStepExecutionListener)
                .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<ProductJoinDto> updateProductFromViolatedProductJpaPagingItemReader() throws Exception {
        String query =
                "SELECT p.product_name, p.company_name, vp.actioned_date, vp.violated_cn, vp.action_cn, vp.etc_info "
                        + "FROM product p "
                        + "INNER JOIN violated_product vp ON REGEXP_REPLACE(vp.company_name, ' ', '', 'g') LIKE"
                        + "'%' || REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(p.company_name, '주식회사', '', 'g'),"
                        + " '\\([^)]*\\)', '','g'), '\\[[^]]*\\]', '','g'), ' ', '', 'g') || '%'"
                        + "AND vp.product_name = p.product_name;";

        JpaNativeQueryProvider<ProductJoinDto> queryProvider = new JpaNativeQueryProvider<>();
        queryProvider.setSqlQuery(query);
        queryProvider.setEntityClass(ProductJoinDto.class);
        queryProvider.afterPropertiesSet();

        return new JpaCursorItemReaderBuilder<ProductJoinDto>()
                .name("updateProductFromViolatedProductJpaPagingItemReader")
                .queryProvider(queryProvider)
                .entityManagerFactory(emf)
                .build();
    }

    @Bean
    @StepScope
    public JpaItemWriter<Product> updateProductFromViolatedProductJpaItemWriter() {

        return new JpaItemWriterBuilder<Product>()
                .entityManagerFactory(emf)
                .build();
    }
}
