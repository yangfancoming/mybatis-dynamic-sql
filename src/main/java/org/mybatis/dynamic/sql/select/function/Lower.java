
package org.mybatis.dynamic.sql.select.function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

public class Lower extends AbstractFunction<String, Lower> {
    
    private Lower(BindableColumn<String> column) {
        super(column);
    }
    
    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "lower(" //$NON-NLS-1$
                + column.renderWithTableAlias(tableAliasCalculator)
                + ")"; //$NON-NLS-1$
    }

    @Override
    protected Lower copy() {
        return new Lower(column);
    }

    public static Lower of(BindableColumn<String> column) {
        return new Lower(column);
    }
}
