
package org.mybatis.dynamic.sql.where.condition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IsGreaterThanWhenPresent<T> extends IsGreaterThan<T> {

    protected IsGreaterThanWhenPresent(Supplier<T> valueSupplier) {
        super(valueSupplier, Objects::nonNull);
    }
    
    public static <T> IsGreaterThanWhenPresent<T> of(Supplier<T> valueSupplier) {
        return new IsGreaterThanWhenPresent<>(valueSupplier);
    }

    @Override
    public IsGreaterThanWhenPresent<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsGreaterThanWhenPresent<>(() -> transformer.apply(value())) : this;
    }
}
