
package org.mybatis.dynamic.sql;

import java.util.Objects;
import java.util.function.BooleanSupplier;

public abstract class AbstractNoValueCondition<T> implements VisitableCondition<T> {

    private BooleanSupplier booleanSupplier;
    
    protected AbstractNoValueCondition() {
        booleanSupplier = () -> true;
    }
    
    protected AbstractNoValueCondition(BooleanSupplier booleanSupplier) {
        this.booleanSupplier = Objects.requireNonNull(booleanSupplier);
    }
    
    @Override
    public boolean shouldRender() {
        return booleanSupplier.getAsBoolean();
    }
    
    @Override
    public <R> R accept(ConditionVisitor<T,R> visitor) {
        return visitor.visit(this);
    }
    
    public abstract String renderCondition(String columnName);
}
