
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;

public class IsGreaterThanOrEqualTo<T> extends AbstractSingleValueCondition<T> {

    protected IsGreaterThanOrEqualTo(Supplier<T> valueSupplier) {
        super(valueSupplier);
    }
    
    protected IsGreaterThanOrEqualTo(Supplier<T> valueSupplier, Predicate<T> predicate) {
        super(valueSupplier, predicate);
    }
    
    @Override
    public String renderCondition(String columnName, String placeholder) {
        return columnName + " >= " + placeholder; //$NON-NLS-1$
    }

    public static <T> IsGreaterThanOrEqualTo<T> of(Supplier<T> valueSupplier) {
        return new IsGreaterThanOrEqualTo<>(valueSupplier);
    }
    
    public IsGreaterThanOrEqualTo<T> when(Predicate<T> predicate) {
        return new IsGreaterThanOrEqualTo<>(valueSupplier, predicate);
    }

    public IsGreaterThanOrEqualTo<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsGreaterThanOrEqualTo<>(() -> transformer.apply(value())) : this;
    }
}
