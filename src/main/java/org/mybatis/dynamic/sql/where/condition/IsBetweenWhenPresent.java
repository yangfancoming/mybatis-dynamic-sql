
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.util.Predicates;

public class IsBetweenWhenPresent<T> extends IsBetween<T> {

    protected IsBetweenWhenPresent(Supplier<T> valueSupplier1, Supplier<T> valueSupplier2) {
        super(valueSupplier1, valueSupplier2, Predicates.bothPresent());
    }
    
    public static class Builder<T> extends AndGatherer<T, IsBetweenWhenPresent<T>> {
        private Builder(Supplier<T> valueSupplier1) {
            super(valueSupplier1);
        }
        
        @Override
        protected IsBetweenWhenPresent<T> build() {
            return new IsBetweenWhenPresent<>(valueSupplier1, valueSupplier2);
        }
    }
    
    public static <T> Builder<T> isBetweenWhenPresent(Supplier<T> valueSupplier) {
        return new Builder<>(valueSupplier);
    }

    @Override
    public IsBetweenWhenPresent<T> then(UnaryOperator<T> transformer1, UnaryOperator<T> transformer2) {
        return shouldRender() ? new IsBetweenWhenPresent<>(() -> transformer1.apply(value1()),
                () -> transformer2.apply(value2())) : this;
    }
}
