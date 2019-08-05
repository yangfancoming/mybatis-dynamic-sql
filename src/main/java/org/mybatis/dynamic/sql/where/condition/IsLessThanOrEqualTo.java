
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;

public class IsLessThanOrEqualTo<T> extends AbstractSingleValueCondition<T> {

    protected IsLessThanOrEqualTo(Supplier<T> valueSupplier) {
        super(valueSupplier);
    }
    
    protected IsLessThanOrEqualTo(Supplier<T> valueSupplier, Predicate<T> predicate) {
        super(valueSupplier, predicate);
    }
    
    @Override
    public String renderCondition(String columnName, String placeholder) {
        return columnName + " <= " + placeholder; //$NON-NLS-1$
    }

    public static <T> IsLessThanOrEqualTo<T> of(Supplier<T> valueSupplier) {
        return new IsLessThanOrEqualTo<>(valueSupplier);
    }
    
    public IsLessThanOrEqualTo<T> when(Predicate<T> predicate) {
        return new IsLessThanOrEqualTo<>(valueSupplier, predicate);
    }

    public IsLessThanOrEqualTo<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsLessThanOrEqualTo<>(() -> transformer.apply(value())) : this;
    }
}
