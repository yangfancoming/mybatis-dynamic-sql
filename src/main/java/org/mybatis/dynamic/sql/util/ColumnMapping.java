
package org.mybatis.dynamic.sql.util;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public class ColumnMapping extends AbstractColumnMapping implements UpdateMapping {

    private BasicColumn rightColumn;
    
    private ColumnMapping(SqlColumn<?> column, BasicColumn rightColumn) {
        super(column);
        this.rightColumn = rightColumn;
    }
    
    public BasicColumn rightColumn() {
        return rightColumn;
    }
    
    @Override
    public <R> R accept(UpdateMappingVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public static ColumnMapping of(SqlColumn<?> column, BasicColumn rightColumn) {
        return new ColumnMapping(column, rightColumn);
    }
}
