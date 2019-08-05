
package org.mybatis.dynamic.sql.where.condition;

import org.mybatis.dynamic.sql.AbstractColumnComparisonCondition;
import org.mybatis.dynamic.sql.BasicColumn;

public class IsGreaterThanColumn<T> extends AbstractColumnComparisonCondition<T> {

    protected IsGreaterThanColumn(BasicColumn column) {
        super(column);
    }

    @Override
    protected String renderCondition(String leftColumn, String rightColumn) {
        return leftColumn + " > " + rightColumn; //$NON-NLS-1$
    }
    
    public static <T> IsGreaterThanColumn<T> of(BasicColumn column) {
        return new IsGreaterThanColumn<>(column);
    }
}
