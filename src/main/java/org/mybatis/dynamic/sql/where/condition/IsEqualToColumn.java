
package org.mybatis.dynamic.sql.where.condition;

import org.mybatis.dynamic.sql.AbstractColumnComparisonCondition;
import org.mybatis.dynamic.sql.BasicColumn;

public class IsEqualToColumn<T> extends AbstractColumnComparisonCondition<T> {

    protected IsEqualToColumn(BasicColumn column) {
        super(column);
    }

    @Override
    protected String renderCondition(String leftColumn, String rightColumn) {
        return leftColumn + " = " + rightColumn; //$NON-NLS-1$
    }
    
    public static <T> IsEqualToColumn<T> of(BasicColumn column) {
        return new IsEqualToColumn<>(column);
    }
}
