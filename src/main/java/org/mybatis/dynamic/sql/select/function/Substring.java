
package org.mybatis.dynamic.sql.select.function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

public class Substring extends AbstractFunction<String, Substring> {

    private int offset;
    private int length;
    
    private Substring(BindableColumn<String> column, int offset, int length) {
        super(column);
        this.offset = offset;
        this.length = length;
    }
    
    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "substring(" //$NON-NLS-1$
                + column.renderWithTableAlias(tableAliasCalculator)
                + ", " //$NON-NLS-1$
                + offset
                + ", " //$NON-NLS-1$
                + length
                + ")"; //$NON-NLS-1$
    }
    
    @Override
    protected Substring copy() {
        return new Substring(column, offset, length);
    }
    
    public static Substring of(BindableColumn<String> column, int offset, int length) {
        return new Substring(column, offset, length);
    }
}
