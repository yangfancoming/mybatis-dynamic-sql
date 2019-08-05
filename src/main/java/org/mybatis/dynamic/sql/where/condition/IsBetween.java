
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.AbstractTwoValueCondition;

public class IsBetween<T> extends AbstractTwoValueCondition<T> {

    protected IsBetween(Supplier<T> valueSupplier1, Supplier<T> valueSupplier2) {
        super(valueSupplier1, valueSupplier2);
    }
    
    protected IsBetween(Supplier<T> valueSupplier1, Supplier<T> valueSupplier2, BiPredicate<T, T> predicate) {
        super(valueSupplier1, valueSupplier2, predicate);
    }
    
    @Override
    public String renderCondition(String columnName, String placeholder1, String placeholder2) {
        return columnName + " between " + placeholder1 + " and " + placeholder2; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public static class Builder<T> extends AndGatherer<T, IsBetween<T>> {
        private Builder(Supplier<T> valueSupplier1) {
            super(valueSupplier1);
        }
        
        @Override
        protected IsBetween<T> build() {
            return new IsBetween<>(valueSupplier1, valueSupplier2);
        }
    }
    
    public static <T> Builder<T> isBetween(Supplier<T> valueSupplier1) {
        return new Builder<>(valueSupplier1);
    }

    public IsBetween<T> when(BiPredicate<T, T> predicate) {
        return new IsBetween<>(valueSupplier1, valueSupplier2, predicate);
    }

    public IsBetween<T> then(UnaryOperator<T> transformer1, UnaryOperator<T> transformer2) {
        return shouldRender() ? new IsBetween<>(() -> transformer1.apply(value1()),
                () -> transformer2.apply(value2())) : this;
    }
}
