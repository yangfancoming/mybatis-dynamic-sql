
package org.mybatis.dynamic.sql.where.condition;

import org.mybatis.dynamic.sql.AbstractSubselectCondition;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.util.Buildable;

public class IsNotInWithSubselect<T> extends AbstractSubselectCondition<T> {
    
    protected IsNotInWithSubselect(Buildable<SelectModel> selectModelBuilder) {
        super(selectModelBuilder);
    }

    public static <T> IsNotInWithSubselect<T> of(Buildable<SelectModel> selectModelBuilder) {
        return new IsNotInWithSubselect<>(selectModelBuilder);
    }

    @Override
    public String renderCondition(String columnName, String renderedSelectStatement) {
        return columnName + " not in (" + renderedSelectStatement + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
