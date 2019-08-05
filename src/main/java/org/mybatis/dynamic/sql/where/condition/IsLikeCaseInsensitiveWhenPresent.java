
package org.mybatis.dynamic.sql.where.condition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IsLikeCaseInsensitiveWhenPresent extends IsLikeCaseInsensitive {

    protected IsLikeCaseInsensitiveWhenPresent(Supplier<String> valueSupplier) {
        super(valueSupplier, Objects::nonNull);
    }
    
    public static IsLikeCaseInsensitiveWhenPresent of(Supplier<String> valueSupplier) {
        return new IsLikeCaseInsensitiveWhenPresent(valueSupplier);
    }

    @Override
    public IsLikeCaseInsensitiveWhenPresent then(UnaryOperator<String> transformer) {
        return shouldRender() ? new IsLikeCaseInsensitiveWhenPresent(() -> transformer.apply(value())) : this;
    }
}