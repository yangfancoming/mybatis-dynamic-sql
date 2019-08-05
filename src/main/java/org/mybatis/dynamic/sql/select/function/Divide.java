
package org.mybatis.dynamic.sql.select.function;

import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.BindableColumn;

public class Divide<T extends Number> extends AbstractMultipleColumnArithmeticFunction<T, Divide<T>> {
    
    private Divide(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            List<BasicColumn> subsequentColumns) {
        super(firstColumn, secondColumn, subsequentColumns);
    }

    @Override
    protected Divide<T> copy() {
        return new Divide<>(column, secondColumn, subsequentColumns);
    }

    @Override
    protected String operator() {
        return "/"; //$NON-NLS-1$
    }

    public static <T extends Number> Divide<T> of(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            List<BasicColumn> subsequentColumns) {
        return new Divide<>(firstColumn, secondColumn, subsequentColumns);
    }
}
