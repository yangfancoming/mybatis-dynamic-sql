
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;

public class IsEqualTo<T> extends AbstractSingleValueCondition<T> {

    protected IsEqualTo(Supplier<T> valueSupplier) {
        super(valueSupplier);
    }

    protected IsEqualTo(Supplier<T> valueSupplier, Predicate<T> predicate) {
        super(valueSupplier, predicate);
    }

    @Override
    public String renderCondition(String columnName, String placeholder) {
        return columnName + " = " + placeholder; //$NON-NLS-1$
    }
    
    public static <T> IsEqualTo<T> of(Supplier<T> valueSupplier) {
        return new IsEqualTo<>(valueSupplier);
    }
    
    public IsEqualTo<T> when(Predicate<T> predicate) {
        return new IsEqualTo<>(valueSupplier, predicate);
    }

    public IsEqualTo<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsEqualTo<>(() -> transformer.apply(value())) : this;
    }
}
