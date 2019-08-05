
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;

public class IsNotEqualTo<T> extends AbstractSingleValueCondition<T> {

    protected IsNotEqualTo(Supplier<T> valueSupplier) {
        super(valueSupplier);
    }

    protected IsNotEqualTo(Supplier<T> valueSupplier, Predicate<T> predicate) {
        super(valueSupplier, predicate);
    }

    @Override
    public String renderCondition(String columnName, String placeholder) {
        return columnName + " <> " + placeholder; //$NON-NLS-1$
    }
    
    public static <T> IsNotEqualTo<T> of(Supplier<T> valueSupplier) {
        return new IsNotEqualTo<>(valueSupplier);
    }
    
    public IsNotEqualTo<T> when(Predicate<T> predicate) {
        return new IsNotEqualTo<>(valueSupplier, predicate);
    }

    public IsNotEqualTo<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsNotEqualTo<>(() -> transformer.apply(value())) : this;
    }
}
