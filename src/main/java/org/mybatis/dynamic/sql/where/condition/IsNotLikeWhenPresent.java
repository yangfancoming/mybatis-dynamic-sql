
package org.mybatis.dynamic.sql.where.condition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IsNotLikeWhenPresent<T> extends IsNotLike<T> {

    protected IsNotLikeWhenPresent(Supplier<T> valueSupplier) {
        super(valueSupplier, Objects::nonNull);
    }

    public static <T> IsNotLikeWhenPresent<T> of(Supplier<T> valueSupplier) {
        return new IsNotLikeWhenPresent<>(valueSupplier);
    }

    @Override
    public IsNotLikeWhenPresent<T> then(UnaryOperator<T> transformer) {
        return shouldRender() ? new IsNotLikeWhenPresent<>(() -> transformer.apply(value())) : this;
    }
}
