
package org.mybatis.dynamic.sql.util;

import java.util.function.Supplier;

import org.mybatis.dynamic.sql.SqlColumn;

public class ValueMapping<T> extends AbstractColumnMapping implements UpdateMapping {

    private Supplier<T> valueSupplier;
    
    private ValueMapping(SqlColumn<T> column, Supplier<T> valueSupplier) {
        super(column);
        this.valueSupplier = valueSupplier;
    }
    
    public T value() {
        return valueSupplier.get();
    }

    @Override
    public <R> R accept(UpdateMappingVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public static <T> ValueMapping<T> of(SqlColumn<T> column, Supplier<T> valueSupplier) {
        return new ValueMapping<>(column, valueSupplier);
    }
}
