
package org.mybatis.dynamic.sql.where.condition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IsLikeWhenPresent<T> extends IsLike<T> {

    protected IsLikeWhenPresent(Supplier<T> valueSupplier) {
        super(valueSupplier, Objects::nonNull);
    }

    public static <T> IsLikeWhenPresent<T> of(Supplier<T> valueSupplier) {
        return new IsLikeWhenPresent<>(valueSupplier);
    }

    @Override
    public IsLikeWhenPresent<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsLikeWhenPresent<>(() -> transformer.apply(value())) : this;
    }
}
