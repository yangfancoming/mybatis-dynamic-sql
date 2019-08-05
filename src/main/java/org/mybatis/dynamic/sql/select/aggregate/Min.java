
package org.mybatis.dynamic.sql.select.aggregate;

import org.mybatis.dynamic.sql.BasicColumn;

public class Min extends AbstractAggregate<Min> {

    private Min(BasicColumn column) {
        super(column);
    }
    
    @Override
    protected String render(String columnName) {
        return "min(" + columnName + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    protected Min copy() {
        return new Min(column);
    }
    
    public static Min of(BasicColumn column) {
        return new Min(column);
    }
}
