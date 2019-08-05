
package org.mybatis.dynamic.sql.where.condition;

import org.mybatis.dynamic.sql.AbstractColumnComparisonCondition;
import org.mybatis.dynamic.sql.BasicColumn;

public class IsGreaterThanOrEqualToColumn<T> extends AbstractColumnComparisonCondition<T> {

    protected IsGreaterThanOrEqualToColumn(BasicColumn column) {
        super(column);
    }

    @Override
    protected String renderCondition(String leftColumn, String rightColumn) {
        return leftColumn + " >= " + rightColumn; //$NON-NLS-1$
    }
    
    public static <T> IsGreaterThanOrEqualToColumn<T> of(BasicColumn column) {
        return new IsGreaterThanOrEqualToColumn<>(column);
    }
}
