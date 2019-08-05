
package org.mybatis.dynamic.sql.select.aggregate;

import org.mybatis.dynamic.sql.BasicColumn;

public class CountDistinct extends AbstractAggregate<CountDistinct> {
    
    private CountDistinct(BasicColumn column) {
        super(column);
    }
    
    @Override
    protected String render(String columnName) {
        return "count(distinct " + columnName + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    protected CountDistinct copy() {
        return new CountDistinct(column);
    }
    
    public static CountDistinct of(BasicColumn column) {
        return new CountDistinct(column);
    }
}
