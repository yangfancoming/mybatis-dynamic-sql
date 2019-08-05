
package org.mybatis.dynamic.sql.where.condition;

import org.mybatis.dynamic.sql.AbstractSubselectCondition;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.util.Buildable;

public class IsEqualToWithSubselect<T> extends AbstractSubselectCondition<T> {
    
    protected IsEqualToWithSubselect(Buildable<SelectModel> selectModelBuilder) {
        super(selectModelBuilder);
    }

    public static <T> IsEqualToWithSubselect<T> of(Buildable<SelectModel> selectModelBuilder) {
        return new IsEqualToWithSubselect<>(selectModelBuilder);
    }

    @Override
    public String renderCondition(String columnName, String renderedSelectStatement) {
        return columnName + " = (" + renderedSelectStatement + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
