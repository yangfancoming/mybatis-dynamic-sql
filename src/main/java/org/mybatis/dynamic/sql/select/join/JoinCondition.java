
package org.mybatis.dynamic.sql.select.join;

import org.mybatis.dynamic.sql.BasicColumn;

public abstract class JoinCondition {
    private BasicColumn rightColumn;
    
    public JoinCondition(BasicColumn rightColumn) {
        this.rightColumn = rightColumn;
    }
    
    public BasicColumn rightColumn() {
        return rightColumn;
    }
    
    public abstract String operator();
}
