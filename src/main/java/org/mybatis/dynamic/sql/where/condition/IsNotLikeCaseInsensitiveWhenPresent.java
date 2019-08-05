
package org.mybatis.dynamic.sql.where.condition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IsNotLikeCaseInsensitiveWhenPresent extends IsNotLikeCaseInsensitive {

    protected IsNotLikeCaseInsensitiveWhenPresent(Supplier<String> valueSupplier) {
        super(valueSupplier, Objects::nonNull);
    }
    
    public static IsNotLikeCaseInsensitiveWhenPresent of(Supplier<String> valueSupplier) {
        return new IsNotLikeCaseInsensitiveWhenPresent(valueSupplier);
    }

    @Override
    public IsNotLikeCaseInsensitiveWhenPresent then(UnaryOperator<String> transformer) {
        return shouldRender() ? new IsNotLikeCaseInsensitiveWhenPresent(() -> transformer.apply(value())) : this;
    }
}