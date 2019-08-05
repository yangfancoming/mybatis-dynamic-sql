
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;

public class IsLike<T> extends AbstractSingleValueCondition<T> {

    protected IsLike(Supplier<T> valueSupplier) {
        super(valueSupplier);
    }

    protected IsLike(Supplier<T> valueSupplier, Predicate<T> predicate) {
        super(valueSupplier, predicate);
    }

    @Override
    public String renderCondition(String columnName, String placeholder) {
        return columnName + " like " + placeholder; //$NON-NLS-1$
    }
    
    public static <T> IsLike<T> of(Supplier<T> valueSupplier) {
        return new IsLike<>(valueSupplier);
    }
    
    public IsLike<T> when(Predicate<T> predicate) {
        return new IsLike<>(valueSupplier, predicate);
    }

    public IsLike<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsLike<>(() -> transformer.apply(value())) : this;
    }
}
