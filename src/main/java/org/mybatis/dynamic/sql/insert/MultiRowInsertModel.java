
package org.mybatis.dynamic.sql.insert;

import java.util.Collection;

import org.mybatis.dynamic.sql.insert.render.MultiRowInsertRenderer;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;

public class MultiRowInsertModel<T> extends AbstractMultiRowInsertModel<T> {
    
    private MultiRowInsertModel(Builder<T> builder) {
        super(builder);
    }
    
    public MultiRowInsertStatementProvider<T> render(RenderingStrategy renderingStrategy) {
        return MultiRowInsertRenderer.withMultiRowInsertModel(this)
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

        public MultiRowInsertModel<T> build() {
            return new MultiRowInsertModel<>(this);
        }
    }
}
