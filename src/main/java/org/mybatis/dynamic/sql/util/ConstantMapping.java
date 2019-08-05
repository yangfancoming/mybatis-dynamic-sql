
package org.mybatis.dynamic.sql.util;

import org.mybatis.dynamic.sql.SqlColumn;

/**
 * This class represents a mapping between a column and a constant.  The constant should be rendered
 * exactly as specified here.
 * 
 * @author Jeff Butler
 *
 */
public class ConstantMapping extends AbstractColumnMapping implements InsertMapping, UpdateMapping {
    private String constant;

    private ConstantMapping(SqlColumn<?> column) {
        super(column);
    }

    public String constant() {
        return constant;
    }

    public static ConstantMapping of(SqlColumn<?> column, String constant) {
        ConstantMapping mapping = new ConstantMapping(column);
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
