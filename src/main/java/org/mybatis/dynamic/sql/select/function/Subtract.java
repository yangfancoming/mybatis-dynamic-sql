
package org.mybatis.dynamic.sql.select.function;

import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.BindableColumn;

public class Subtract<T extends Number> extends AbstractMultipleColumnArithmeticFunction<T, Subtract<T>> {
    
    private Subtract(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            List<BasicColumn> subsequentColumns) {
        super(firstColumn, secondColumn, subsequentColumns);
    }

    @Override
    protected Subtract<T> copy() {
        return new Subtract<>(column, secondColumn, subsequentColumns);
    }

    @Override
    protected String operator() {
        return "-"; //$NON-NLS-1$
    }

    public static <T extends Number> Subtract<T> of(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            List<BasicColumn> subsequentColumns) {
        return new Subtract<>(firstColumn, secondColumn, subsequentColumns);
    }
}
