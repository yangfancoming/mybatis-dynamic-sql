
package org.mybatis.dynamic.sql.select.join;

import org.mybatis.dynamic.sql.BasicColumn;

public class EqualTo extends JoinCondition {

    public EqualTo(BasicColumn rightColumn) {
        super(rightColumn);
    }

    @Override
    public String operator() {
        return "="; //$NON-NLS-1$
    }
}
