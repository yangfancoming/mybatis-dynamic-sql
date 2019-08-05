
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.BooleanSupplier;

import org.mybatis.dynamic.sql.AbstractNoValueCondition;

public class IsNotNull<T> extends AbstractNoValueCondition<T> {

    public IsNotNull() {
        super();
    }
    
    protected IsNotNull(BooleanSupplier booleanSupplier) {
        super(booleanSupplier);
    }
    
    @Override
    public String renderCondition(String columnName) {
        return columnName + " is not null"; //$NON-NLS-1$
    }
    
    public <S> IsNotNull<S> when(BooleanSupplier booleanSupplier) {
        return new IsNotNull<>(booleanSupplier);
    }
}
