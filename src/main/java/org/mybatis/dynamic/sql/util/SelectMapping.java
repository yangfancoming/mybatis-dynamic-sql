
package org.mybatis.dynamic.sql.util;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.select.SelectModel;

public class SelectMapping extends AbstractColumnMapping implements UpdateMapping {

    private SelectModel selectModel;
    
    private SelectMapping(SqlColumn<?> column, Buildable<SelectModel> selectModelBuilder) {
        super(column);
        selectModel = selectModelBuilder.build();
    }
    
    public SelectModel selectModel() {
        return selectModel;
    }

    @Override
    public <R> R accept(UpdateMappingVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public static SelectMapping of(SqlColumn<?> column, Buildable<SelectModel> selectModelBuilder) {
        return new SelectMapping(column, selectModelBuilder);
    }
}
