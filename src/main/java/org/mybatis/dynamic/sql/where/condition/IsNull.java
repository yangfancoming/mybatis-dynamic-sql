
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.BooleanSupplier;

import org.mybatis.dynamic.sql.AbstractNoValueCondition;

public class IsNull<T> extends AbstractNoValueCondition<T> {

    public IsNull() {
        super();
    }
    
    protected IsNull(BooleanSupplier booleanSupplier) {
        super(booleanSupplier);
    }
    
    @Override
    public String renderCondition(String columnName) {
        return columnName + " is null"; //$NON-NLS-1$
    }
    
    public <S> IsNull<S> when(BooleanSupplier booleanSupplier) {
        return new IsNull<>(booleanSupplier);
    }
}
