
package org.mybatis.dynamic.sql.insert;

import java.util.Collection;

import org.mybatis.dynamic.sql.insert.render.BatchInsert;
import org.mybatis.dynamic.sql.insert.render.BatchInsertRenderer;
import org.mybatis.dynamic.sql.render.RenderingStrategy;

public class BatchInsertModel<T> extends AbstractMultiRowInsertModel<T> {
    
    private BatchInsertModel(Builder<T> builder) {
        super(builder);
    }

    public BatchInsert<T> render(RenderingStrategy renderingStrategy) {
        return BatchInsertRenderer.withBatchInsertModel(this)
                .withRenderingStrategy(renderingStrategy)
                .build()
                .render();
    }
    
    public static <T> Builder<T> withRecords(Collection<T> records) {
        return new Builder<T>().withRecords(records);
    }
    
    public static class Builder<T> extends AbstractBuilder<T, Builder<T>> {
        @Override
        protected Builder<T> getThis() {
            return this;
        }
        
        public BatchInsertModel<T> build() {
            return new BatchInsertModel<>(this);
        }
    }
}
