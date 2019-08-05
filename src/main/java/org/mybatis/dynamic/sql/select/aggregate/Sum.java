
package org.mybatis.dynamic.sql.select.aggregate;

import org.mybatis.dynamic.sql.BasicColumn;

public class Sum extends AbstractAggregate<Sum> {

    private Sum(BasicColumn column) {
        super(column);
    }
    
    @Override
    protected String render(String columnName) {
        return "sum(" + columnName + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    protected Sum copy() {
        return new Sum(column);
    }
    
    public static Sum of(BasicColumn column) {
        return new Sum(column);
    }
}
