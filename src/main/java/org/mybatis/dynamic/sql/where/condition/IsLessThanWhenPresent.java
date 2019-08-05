
package org.mybatis.dynamic.sql.where.condition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IsLessThanWhenPresent<T> extends IsLessThan<T> {

    protected IsLessThanWhenPresent(Supplier<T> valueSupplier) {
        super(valueSupplier, Objects::nonNull);
    }
    
    public static <T> IsLessThanWhenPresent<T> of(Supplier<T> valueSupplier) {
        return new IsLessThanWhenPresent<>(valueSupplier);
    }

    @Override
    public IsLessThanWhenPresent<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsLessThanWhenPresent<>(() -> transformer.apply(value())) : this;
    }
}
