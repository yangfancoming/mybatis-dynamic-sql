
package org.mybatis.dynamic.sql.where.condition;

import org.mybatis.dynamic.sql.AbstractSubselectCondition;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.util.Buildable;

public class IsInWithSubselect<T> extends AbstractSubselectCondition<T> {
    
    protected IsInWithSubselect(Buildable<SelectModel> selectModelBuilder) {
        super(selectModelBuilder);
    }

    public static <T> IsInWithSubselect<T> of(Buildable<SelectModel> selectModelBuilder) {
        return new IsInWithSubselect<>(selectModelBuilder);
    }

    @Override
    public String renderCondition(String columnName, String renderedSelectStatement) {
        return columnName + " in (" + renderedSelectStatement + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
