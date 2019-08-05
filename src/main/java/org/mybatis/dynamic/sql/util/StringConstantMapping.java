
package org.mybatis.dynamic.sql.util;

import org.mybatis.dynamic.sql.SqlColumn;

/**
 * This class represents a mapping between a column and a string constant.  The constant should be rendered
 * surrounded by single quotes for SQL.
 * 
 * @author Jeff Butler
 *
 */
public class StringConstantMapping extends AbstractColumnMapping implements InsertMapping, UpdateMapping {
    private String constant;
    
    private StringConstantMapping(SqlColumn<?> column) {
        super(column);
    }

    public String constant() {
        return constant;
    }
    
    public static StringConstantMapping of(SqlColumn<?> column, String constant) {
        StringConstantMapping mapping = new StringConstantMapping(column);
        mapping.constant = constant;
        return mapping;
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
