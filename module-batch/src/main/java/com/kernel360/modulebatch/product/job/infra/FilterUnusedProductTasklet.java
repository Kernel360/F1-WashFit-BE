package com.kernel360.modulebatch.product.job.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
public class FilterUnusedProductTasklet implements Tasklet {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        jdbcTemplate.update("delete from product where upper_item like '%세탁%'");
        jdbcTemplate.update("delete from product where product_name similar to '(방향제|그라스|통풍구|선바이저|살라딘|세탁|체인)%'");

        return RepeatStatus.FINISHED;
    }
}
