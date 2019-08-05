
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractSingleValueCondition;
import org.mybatis.dynamic.sql.util.StringUtilities;

public class IsNotLikeCaseInsensitive extends AbstractSingleValueCondition<String> {
    protected IsNotLikeCaseInsensitive(Supplier<String> valueSupplier) {
        super(valueSupplier);
    }
    
    protected IsNotLikeCaseInsensitive(Supplier<String> valueSupplier, Predicate<String> predicate) {
        super(valueSupplier, predicate);
    }
    
    @Override
    public String renderCondition(String columnName, String placeholder) {
        return "upper(" + columnName + ") not like " + placeholder; //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    @Override
    public String value() {
        return StringUtilities.safelyUpperCase(super.value());
    }

    public static IsNotLikeCaseInsensitive of(Supplier<String> valueSupplier) {
        return new IsNotLikeCaseInsensitive(valueSupplier);
    }
    
    public IsNotLikeCaseInsensitive when(Predicate<String> predicate) {
        return new IsNotLikeCaseInsensitive(valueSupplier, predicate);
    }

    public IsNotLikeCaseInsensitive then(UnaryOperator<String> transformer) {
        return shouldRender() ? new IsNotLikeCaseInsensitive(() -> transformer.apply(value())) : this;
    }
}