
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;

public class IsLessThan<T> extends AbstractSingleValueCondition<T> {

    protected IsLessThan(Supplier<T> valueSupplier) {
        super(valueSupplier);
    }
    
    protected IsLessThan(Supplier<T> valueSupplier, Predicate<T> predicate) {
        super(valueSupplier, predicate);
    }
    
    @Override
    public String renderCondition(String columnName, String placeholder) {
        return columnName + " < " + placeholder; //$NON-NLS-1$
    }

    public static <T> IsLessThan<T> of(Supplier<T> valueSupplier) {
        return new IsLessThan<>(valueSupplier);
    }
    
    public IsLessThan<T> when(Predicate<T> predicate) {
        return new IsLessThan<>(valueSupplier, predicate);
    }

    public IsLessThan<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsLessThan<>(() -> transformer.apply(value())) : this;
    }
}
