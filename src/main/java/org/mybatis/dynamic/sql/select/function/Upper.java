
package org.mybatis.dynamic.sql.select.function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

public class Upper extends AbstractFunction<String, Upper> {
    
    private Upper(BindableColumn<String> column) {
        super(column);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "upper(" //$NON-NLS-1$
                + column.renderWithTableAlias(tableAliasCalculator)
                + ")"; //$NON-NLS-1$
    }

    @Override
    protected Upper copy() {
        return new Upper(column);
    }

    public static Upper of(BindableColumn<String> column) {
        return new Upper(column);
    }
}
