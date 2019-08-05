
package org.mybatis.dynamic.sql.select.function;

import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.BindableColumn;

public class Multiply<T extends Number> extends AbstractMultipleColumnArithmeticFunction<T, Multiply<T>> {
    
    private Multiply(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            List<BasicColumn> subsequentColumns) {
        super(firstColumn, secondColumn, subsequentColumns);
    }

    @Override
    protected Multiply<T> copy() {
        return new Multiply<>(column, secondColumn, subsequentColumns);
    }

    @Override
    protected String operator() {
        return "*"; //$NON-NLS-1$
    }

    public static <T extends Number> Multiply<T> of(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            List<BasicColumn> subsequentColumns) {
        return new Multiply<>(firstColumn, secondColumn, subsequentColumns);
    }
}
