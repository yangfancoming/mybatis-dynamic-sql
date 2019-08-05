
package org.mybatis.dynamic.sql;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public abstract class AbstractTwoValueCondition<T> implements VisitableCondition<T> {
    protected Supplier<T> valueSupplier1;
    protected Supplier<T> valueSupplier2;
    private BiPredicate<T, T> predicate;
    
    protected AbstractTwoValueCondition(Supplier<T> valueSupplier1, Supplier<T> valueSupplier2) {
        this.valueSupplier1 = Objects.requireNonNull(valueSupplier1);
        this.valueSupplier2 = Objects.requireNonNull(valueSupplier2);
        predicate = (v1, v2) -> true;
    }

    protected AbstractTwoValueCondition(Supplier<T> valueSupplier1, Supplier<T> valueSupplier2,
            BiPredicate<T, T> predicate) {
        this(valueSupplier1, valueSupplier2);
        this.predicate = Objects.requireNonNull(predicate);
    }

    public T value1() {
        return valueSupplier1.get();
    }

    public T value2() {
        return valueSupplier2.get();
    }
    
    @Override
    public boolean shouldRender() {
        return predicate.test(value1(), value2());
    }
    
    @Override
    public <R> R accept(ConditionVisitor<T,R> visitor) {
        return visitor.visit(this);
    }

    public abstract String renderCondition(String columnName, String placeholder1, String placeholder2);
}
