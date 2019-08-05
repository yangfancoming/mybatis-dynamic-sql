
package org.mybatis.dynamic.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public abstract class AbstractListValueCondition<T> implements VisitableCondition<T> {
    protected Collection<T> values;
    protected UnaryOperator<Stream<T>> valueStreamTransformer;

    protected AbstractListValueCondition(Collection<T> values) {
        this(values, UnaryOperator.identity());
    }

    protected AbstractListValueCondition(Collection<T> values, UnaryOperator<Stream<T>> valueStreamTransformer) {
        this.values = new ArrayList<>(Objects.requireNonNull(values));
        this.valueStreamTransformer = Objects.requireNonNull(valueStreamTransformer);
    }
    
    public final <R> Stream<R> mapValues(Function<T, R> mapper) {
        return valueStreamTransformer.apply(values.stream()).map(mapper);
    }
    
    @Override
    public <R> R accept(ConditionVisitor<T, R> visitor) {
        return visitor.visit(this);
    }

    public abstract String renderCondition(String columnName, Stream<String> placeholders);
}
