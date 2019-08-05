
package org.mybatis.dynamic.sql.insert.render;

import static org.mybatis.dynamic.sql.util.StringUtilities.spaceBefore;

import java.util.Objects;

import org.mybatis.dynamic.sql.insert.MultiRowInsertModel;
import org.mybatis.dynamic.sql.render.RenderingStrategy;

public class MultiRowInsertRenderer<T> {

    private MultiRowInsertModel<T> model;
    private RenderingStrategy renderingStrategy;
    
    private MultiRowInsertRenderer(Builder<T> builder) {
        model = Objects.requireNonNull(builder.model);
        renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
    }
    
    public MultiRowInsertStatementProvider<T> render() {
        ValuePhraseVisitor visitor = new MultiRowValuePhraseVisitor(renderingStrategy);
        FieldAndValueCollector collector = model.mapColumnMappings(MultiRowRenderingUtilities.toFieldAndValue(visitor))
                .collect(FieldAndValueCollector.collect());
        
        return new DefaultMultiRowInsertStatementProvider.Builder<T>().withRecords(model.records())
                .withInsertStatement(calculateInsertStatement(collector))
                .build();
    }
    
    private String calculateInsertStatement(FieldAndValueCollector collector) {
        return "insert into" //$NON-NLS-1$
                + spaceBefore(model.table().tableNameAtRuntime())
                + spaceBefore(collector.columnsPhrase())
                + spaceBefore(collector.multiRowInsertValuesPhrase(model.recordCount()));
    }
    
    public static <T> Builder<T> withMultiRowInsertModel(MultiRowInsertModel<T> model) {
        return new Builder<T>().withMultiRowInsertModel(model);
    }
    
    public static class Builder<T> {
        private MultiRowInsertModel<T> model;
        private RenderingStrategy renderingStrategy;
        
        public Builder<T> withMultiRowInsertModel(MultiRowInsertModel<T> model) {
            this.model = model;
            return this;
        }
        
        public Builder<T> withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }
        
        public MultiRowInsertRenderer<T> build() {
            return new MultiRowInsertRenderer<>(this);
        }
    }
}
