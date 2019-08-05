
package org.mybatis.dynamic.sql.where.condition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IsLessThanOrEqualToWhenPresent<T> extends IsLessThanOrEqualTo<T> {

    protected IsLessThanOrEqualToWhenPresent(Supplier<T> valueSupplier) {
        super(valueSupplier, Objects::nonNull);
    }
    
    public static <T> IsLessThanOrEqualToWhenPresent<T> of(Supplier<T> valueSupplier) {
        return new IsLessThanOrEqualToWhenPresent<>(valueSupplier);
    }

    @Override
    public IsLessThanOrEqualToWhenPresent<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsLessThanOrEqualToWhenPresent<>(() -> transformer.apply(value())) : this;
    }
}
