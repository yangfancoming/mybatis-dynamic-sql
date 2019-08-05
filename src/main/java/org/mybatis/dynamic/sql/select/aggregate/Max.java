
package org.mybatis.dynamic.sql.select.aggregate;

import org.mybatis.dynamic.sql.BasicColumn;

public class Max extends AbstractAggregate<Max> {

    private Max(BasicColumn column) {
        super(column);
    }
    
    @Override
    protected String render(String columnName) {
        return "max(" + columnName + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    protected Max copy() {
        return new Max(column);
    }
    
    public static Max of(BasicColumn column) {
        return new Max(column);
    }
}
