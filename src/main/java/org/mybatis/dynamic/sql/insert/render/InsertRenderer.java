
package org.mybatis.dynamic.sql.insert.render;

import static org.mybatis.dynamic.sql.util.StringUtilities.spaceBefore;

import java.util.Objects;
import java.util.function.Function;

import org.mybatis.dynamic.sql.insert.InsertModel;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.util.InsertMapping;

public class InsertRenderer<T> {

    private InsertModel<T> model;
    private RenderingStrategy renderingStrategy;
    
    private InsertRenderer(Builder<T> builder) {
        model = Objects.requireNonNull(builder.model);
        renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
    }
    
    public InsertStatementProvider<T> render() {
        ValuePhraseVisitor visitor = new ValuePhraseVisitor(renderingStrategy);
        FieldAndValueCollector collector = model.mapColumnMappings(toFieldAndValue(visitor))
                .collect(FieldAndValueCollector.collect());
        
        return DefaultInsertStatementProvider.withRecord(model.record())
                .withInsertStatement(calculateInsertStatement(collector))
                .build();
    }

    private String calculateInsertStatement(FieldAndValueCollector collector) {
        return "insert into" //$NON-NLS-1$
                + spaceBefore(model.table().tableNameAtRuntime())
                + spaceBefore(collector.columnsPhrase())
                + spaceBefore(collector.valuesPhrase());
    }

    private Function<InsertMapping, FieldAndValue> toFieldAndValue(ValuePhraseVisitor visitor) {
        return insertMapping -> toFieldAndValue(visitor, insertMapping);
    }
    
    private FieldAndValue toFieldAndValue(ValuePhraseVisitor visitor, InsertMapping insertMapping) {
        return insertMapping.accept(visitor);
    }
    
    public static <T> Builder<T> withInsertModel(InsertModel<T> model) {
        return new Builder<T>().withInsertModel(model);
    }
    
    public static class Builder<T> {
        private InsertModel<T> model;
        private RenderingStrategy renderingStrategy;
        
        public Builder<T> withInsertModel(InsertModel<T> model) {
            this.model = model;
            return this;
        }
        
        public Builder<T> withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }
        
        public InsertRenderer<T> build() {
            return new InsertRenderer<>(this);
        }
    }
}
