
package org.mybatis.dynamic.sql.select.aggregate;

import org.mybatis.dynamic.sql.BasicColumn;

public class Count extends AbstractAggregate<Count> {
    
    private Count(BasicColumn column) {
        super(column);
    }
    
    @Override
    protected String render(String columnName) {
        return "count(" + columnName + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    protected Count copy() {
        return new Count(column);
    }
    
    public static Count of(BasicColumn column) {
        return new Count(column);
    }
}
