
package org.mybatis.dynamic.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class SqlCriterion<T> {
    
    private BindableColumn<T> column;
    private VisitableCondition<T> condition;
    private String connector;
    private List<SqlCriterion<?>> subCriteria;
    
    private SqlCriterion(Builder<T> builder) {
        connector = builder.connector;
        column = Objects.requireNonNull(builder.column);
        condition = Objects.requireNonNull(builder.condition);
        subCriteria = Objects.requireNonNull(builder.subCriteria);
    }
    
    public Optional<String> connector() {
        return Optional.ofNullable(connector);
    }
    
    public BindableColumn<T> column() {
        return column;
    }
    
    public VisitableCondition<T> condition() {
        return condition;
    }
    
    public <R> Stream<R> mapSubCriteria(Function<SqlCriterion<?>, R> mapper) {
        return subCriteria.stream().map(mapper);
    }
    
    public static <T> Builder<T> withColumn(BindableColumn<T> column) {
        return new Builder<T>().withColumn(column);
    }

    public static class Builder<T> {
        private String connector;
        private BindableColumn<T> column;
        private VisitableCondition<T> condition;
        private List<SqlCriterion<?>> subCriteria = new ArrayList<>();
        
        public Builder<T> withConnector(String connector) {
            this.connector = connector;
            return this;
        }
        
        public Builder<T> withColumn(BindableColumn<T> column) {
            this.column = column;
            return this;
        }
        
        public Builder<T> withCondition(VisitableCondition<T> condition) {
            this.condition = condition;
            return this;
        }
        
        public Builder<T> withSubCriteria(List<SqlCriterion<?>> subCriteria) {
            this.subCriteria.addAll(subCriteria);
            return this;
        }
        
        public SqlCriterion<T> build() {
            return new SqlCriterion<>(this);
        }
    }
}
