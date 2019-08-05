
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;
import org.mybatis.dynamic.sql.util.StringUtilities;

public class IsLikeCaseInsensitive extends AbstractSingleValueCondition<String> {
    protected IsLikeCaseInsensitive(Supplier<String> valueSupplier) {
        super(valueSupplier);
    }
    
    protected IsLikeCaseInsensitive(Supplier<String> valueSupplier, Predicate<String> predicate) {
        super(valueSupplier, predicate);
    }
    
    @Override
    public String renderCondition(String columnName, String placeholder) {
        return "upper(" + columnName + ") like " + placeholder; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public String value() {
        return StringUtilities.safelyUpperCase(super.value());
    }

    public static IsLikeCaseInsensitive of(Supplier<String> valueSupplier) {
        return new IsLikeCaseInsensitive(valueSupplier);
    }
    
    public IsLikeCaseInsensitive when(Predicate<String> predicate) {
        return new IsLikeCaseInsensitive(valueSupplier, predicate);
    }

    public IsLikeCaseInsensitive then(UnaryOperator<String> transformer) {
        return shouldRender() ? new IsLikeCaseInsensitive(() -> transformer.apply(value())) : this;
    }
}