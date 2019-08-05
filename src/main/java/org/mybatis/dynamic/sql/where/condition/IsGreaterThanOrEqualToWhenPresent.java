
package org.mybatis.dynamic.sql.where.condition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IsGreaterThanOrEqualToWhenPresent<T> extends IsGreaterThanOrEqualTo<T> {

    protected IsGreaterThanOrEqualToWhenPresent(Supplier<T> valueSupplier) {
        super(valueSupplier, Objects::nonNull);
    }
    
    public static <T> IsGreaterThanOrEqualToWhenPresent<T> of(Supplier<T> valueSupplier) {
        return new IsGreaterThanOrEqualToWhenPresent<>(valueSupplier);
    }

    @Override
    public IsGreaterThanOrEqualToWhenPresent<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsGreaterThanOrEqualToWhenPresent<>(() -> transformer.apply(value())) : this;
    }
}
