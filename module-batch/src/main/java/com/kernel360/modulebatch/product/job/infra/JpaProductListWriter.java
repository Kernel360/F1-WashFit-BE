package com.kernel360.modulebatch.product.job.infra;

import java.util.List;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

public class JpaProductListWriter<T> extends JpaItemWriter<List<T>> {
    private final JpaItemWriter<T> jpaItemWriter;

    public JpaProductListWriter(JpaItemWriter<T> jpaItemWriter) {
        this.jpaItemWriter = jpaItemWriter;
    }

    @Override
    public void write(Chunk<? extends List<T>> items) {
        Chunk<T> totalChunk = new Chunk<>();

        for (List<T> list : items) {
            totalChunk.addAll(list);
        }
        jpaItemWriter.write(totalChunk);
    }
}

