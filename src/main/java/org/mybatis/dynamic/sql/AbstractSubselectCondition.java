
package org.mybatis.dynamic.sql;

import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.util.Buildable;

public abstract class AbstractSubselectCondition<T> implements VisitableCondition<T> {
    private SelectModel selectModel;
    
    protected AbstractSubselectCondition(Buildable<SelectModel> selectModelBuilder) {
        this.selectModel = selectModelBuilder.build();
    }
    
    public SelectModel selectModel() {
        return selectModel;
    }

    @Override
    public <R> R accept(ConditionVisitor<T, R> visitor) {
        return visitor.visit(this);
    }

    public abstract String renderCondition(String columnName, String renderedSelectStatement);
}
