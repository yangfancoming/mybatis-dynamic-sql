
package org.mybatis.dynamic.sql.util;

import org.mybatis.dynamic.sql.SqlColumn;

public class PropertyMapping extends AbstractColumnMapping implements InsertMapping {
    private String property;
    
    private PropertyMapping(SqlColumn<?> column) {
        super(column);
    }
    
    public String property() {
        return property;
    }

    @Override
    public <S> S accept(InsertMappingVisitor<S> visitor) {
        return visitor.visit(this);
    }
    
    public static PropertyMapping of(SqlColumn<?> column, String property) {
        PropertyMapping mapping = new PropertyMapping(column);
        mapping.property = property;
        return mapping;
    }
}
