
package org.mybatis.dynamic.sql.where.condition;

import org.mybatis.dynamic.sql.AbstractColumnComparisonCondition;
import org.mybatis.dynamic.sql.BasicColumn;

public class IsNotEqualToColumn<T> extends AbstractColumnComparisonCondition<T> {

    protected IsNotEqualToColumn(BasicColumn column) {
        super(column);
    }

    @Override
    protected String renderCondition(String leftColumn, String rightColumn) {
        return leftColumn + " <> " + rightColumn; //$NON-NLS-1$
    }
    
    public static <T> IsNotEqualToColumn<T> of(BasicColumn column) {
        return new IsNotEqualToColumn<>(column);
    }
}
