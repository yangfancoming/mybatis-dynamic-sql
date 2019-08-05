
package org.mybatis.dynamic.sql;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class AbstractSingleValueCondition<T> implements VisitableCondition<T> {
    protected Supplier<T> valueSupplier;
    private Predicate<T> predicate;
    
    protected AbstractSingleValueCondition(Supplier<T> valueSupplier) {
        this.valueSupplier = Objects.requireNonNull(valueSupplier);
        predicate = v -> true;
    }
    
    protected AbstractSingleValueCondition(Supplier<T> valueSupplier, Predicate<T> predicate) {
        this.valueSupplier = Objects.requireNonNull(valueSupplier);
        this.predicate = Objects.requireNonNull(predicate);
    }
    
    public T value() {
        return valueSupplier.get();
    }
    
    @Override
    public boolean shouldRender() {
        return predicate.test(value());
    }
    
    @Override
    public <R> R accept(ConditionVisitor<T,R> visitor) {
        return visitor.visit(this);
    }
    
    public abstract String renderCondition(String columnName, String placeholder);
}
