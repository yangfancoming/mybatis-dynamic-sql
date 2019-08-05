
package org.mybatis.dynamic.sql.select.function;

import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.BindableColumn;

public class Add<T extends Number> extends AbstractMultipleColumnArithmeticFunction<T, Add<T>> {
    
    private Add(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            List<BasicColumn> subsequentColumns) {
        super(firstColumn, secondColumn, subsequentColumns);
    }

    @Override
    protected Add<T> copy() {
        return new Add<>(column, secondColumn, subsequentColumns);
    }

    @Override
    protected String operator() {
        return "+"; //$NON-NLS-1$
    }

    public static <T extends Number> Add<T> of(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            List<BasicColumn> subsequentColumns) {
        return new Add<>(firstColumn, secondColumn, subsequentColumns);
    }
}
