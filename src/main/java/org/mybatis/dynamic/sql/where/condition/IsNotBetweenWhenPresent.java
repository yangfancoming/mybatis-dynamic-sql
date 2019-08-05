
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.mybatis.dynamic.sql.util.Predicates;

public class IsNotBetweenWhenPresent<T> extends IsNotBetween<T> {

    protected IsNotBetweenWhenPresent(Supplier<T> valueSupplier1, Supplier<T> valueSupplier2) {
        super(valueSupplier1, valueSupplier2, Predicates.bothPresent());
    }
    
    public static class Builder<T> extends AndGatherer<T, IsNotBetweenWhenPresent<T>> {
        private Builder(Supplier<T> valueSupplier1) {
            super(valueSupplier1);
        }
        
        @Override
        protected IsNotBetweenWhenPresent<T> build() {
            return new IsNotBetweenWhenPresent<>(valueSupplier1, valueSupplier2);
        }
    }
    
    public static <T> Builder<T> isNotBetweenWhenPresent(Supplier<T> valueSupplier) {
        return new Builder<>(valueSupplier);
    }

    @Override
    public IsNotBetweenWhenPresent<T> then(UnaryOperator<T> transformer1, UnaryOperator<T> transformer2) {
        return shouldRender() ? new IsNotBetweenWhenPresent<>(() -> transformer1.apply(value1()),
                () -> transformer2.apply(value2())) : this;
    }
}
