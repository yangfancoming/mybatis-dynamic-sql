
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;

public class IsNotLike<T> extends AbstractSingleValueCondition<T> {

    protected IsNotLike(Supplier<T> valueSupplier) {
        super(valueSupplier);
    }

    protected IsNotLike(Supplier<T> valueSupplier, Predicate<T> predicate) {
        super(valueSupplier, predicate);
    }

    @Override
    public String renderCondition(String columnName, String placeholder) {
        return columnName + " not like " + placeholder; //$NON-NLS-1$
    }
    
    public static <T> IsNotLike<T> of(Supplier<T> valueSupplier) {
        return new IsNotLike<>(valueSupplier);
    }
    
    public IsNotLike<T> when(Predicate<T> predicate) {
        return new IsNotLike<>(valueSupplier, predicate);
    }

    public IsNotLike<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsNotLike<>(() -> transformer.apply(value())) : this;
    }
}
