
package org.mybatis.dynamic.sql.select.aggregate;

import org.mybatis.dynamic.sql.BasicColumn;

public class Avg extends AbstractAggregate<Avg> {

    private Avg(BasicColumn column) {
        super(column);
    }
    
    @Override
    protected String render(String columnName) {
        return "avg(" + columnName + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    protected Avg copy() {
        return new Avg(column);
    }
    
    public static Avg of(BasicColumn column) {
        return new Avg(column);
    }
}
