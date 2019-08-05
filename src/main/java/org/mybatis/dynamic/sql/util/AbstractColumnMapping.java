
package org.mybatis.dynamic.sql.util;

import java.util.Objects;
import java.util.function.Function;

import org.mybatis.dynamic.sql.SqlColumn;

public abstract class AbstractColumnMapping {
    protected SqlColumn<?> column;
    
    protected AbstractColumnMapping(SqlColumn<?> column) {
        this.column = Objects.requireNonNull(column);
    }
    
    public <R> R mapColumn(Function<SqlColumn<?>, R> mapper) {
        return mapper.apply(column);
    }
}
 