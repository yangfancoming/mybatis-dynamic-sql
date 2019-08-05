
package org.mybatis.dynamic.sql.where.condition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IsEqualToWhenPresent<T> extends IsEqualTo<T> {

    protected IsEqualToWhenPresent(Supplier<T> valueSupplier) {
        super(valueSupplier, Objects::nonNull);
    }

    public static <T> IsEqualToWhenPresent<T> of(Supplier<T> valueSupplier) {
        return new IsEqualToWhenPresent<>(valueSupplier);
    }

    @Override
    public IsEqualToWhenPresent<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsEqualToWhenPresent<>(() -> transformer.apply(value())) : this;
    }
}
