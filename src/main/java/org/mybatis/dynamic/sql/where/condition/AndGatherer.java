
package org.mybatis.dynamic.sql.where.condition;

import java.util.function.Supplier;

/**
 * Utility class supporting the "and" part of a between condition. This class supports builders,
 * so it is mutable.
 * 
 * @author Jeff Butler
 *
 * @param <T> the type of field for the between condition 
 * @param <R> the type of condition being built
 */
public abstract class AndGatherer<T, R> {
    protected Supplier<T> valueSupplier1;
    protected Supplier<T> valueSupplier2;
    
    protected AndGatherer(Supplier<T> valueSupplier1) {
        this.valueSupplier1 = valueSupplier1;
    }
    
    public R and(T value2) {
        return and(() -> value2);
    }

    public R and(Supplier<T> valueSupplier2) {
        this.valueSupplier2 = valueSupplier2;
        return build();
    }
    
    protected abstract R build();
}
