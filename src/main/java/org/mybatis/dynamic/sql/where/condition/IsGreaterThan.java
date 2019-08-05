
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;

public class IsGreaterThan<T> extends AbstractSingleValueCondition<T> {

    protected IsGreaterThan(Supplier<T> valueSupplier) {
        super(valueSupplier);
    }
    
    protected IsGreaterThan(Supplier<T> valueSupplier, Predicate<T> predicate) {
        super(valueSupplier, predicate);
    }
    
    @Override
    public String renderCondition(String columnName, String placeholder) {
        return columnName + " > " + placeholder; //$NON-NLS-1$
    }

    public static <T> IsGreaterThan<T> of(Supplier<T> valueSupplier) {
        return new IsGreaterThan<>(valueSupplier);
    }
    
    public IsGreaterThan<T> when(Predicate<T> predicate) {
        return new IsGreaterThan<>(valueSupplier, predicate);
    }

    public IsGreaterThan<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsGreaterThan<>(() -> transformer.apply(value())) : this;
    }
}
