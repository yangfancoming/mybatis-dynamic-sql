
package org.mybatis.dynamic.sql.where.condition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IsNotEqualToWhenPresent<T> extends IsNotEqualTo<T> {

    protected IsNotEqualToWhenPresent(Supplier<T> valueSupplier) {
        super(valueSupplier, Objects::nonNull);
    }

    public static <T> IsNotEqualToWhenPresent<T> of(Supplier<T> valueSupplier) {
        return new IsNotEqualToWhenPresent<>(valueSupplier);
    }

    @Override
    public IsNotEqualToWhenPresent<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsNotEqualToWhenPresent<>(() -> transformer.apply(value())) : this;
    }
}
