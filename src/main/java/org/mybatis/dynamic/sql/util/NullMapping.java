
package org.mybatis.dynamic.sql.util;

import org.mybatis.dynamic.sql.SqlColumn;

public class NullMapping extends AbstractColumnMapping implements InsertMapping, UpdateMapping {
    private NullMapping(SqlColumn<?> column) {
        super(column);
    }
    
    public static NullMapping of(SqlColumn<?> column) {
        return new NullMapping(column);
    }

    @Override
    public <R> R accept(UpdateMappingVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <R> R accept(InsertMappingVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
