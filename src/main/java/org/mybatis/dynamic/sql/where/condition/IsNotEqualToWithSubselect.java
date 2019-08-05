
package org.mybatis.dynamic.sql.where.condition;

import org.mybatis.dynamic.sql.AbstractSubselectCondition;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.util.Buildable;

public class IsNotEqualToWithSubselect<T> extends AbstractSubselectCondition<T> {
    
    protected IsNotEqualToWithSubselect(Buildable<SelectModel> selectModelBuilder) {
        super(selectModelBuilder);
    }

    public static <T> IsNotEqualToWithSubselect<T> of(Buildable<SelectModel> selectModelBuilder) {
        return new IsNotEqualToWithSubselect<>(selectModelBuilder);
    }

    @Override
    public String renderCondition(String columnName, String renderedSelectStatement) {
        return columnName + " <> (" + renderedSelectStatement + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
