
package org.mybatis.dynamic.sql.select.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

public abstract class AbstractMultipleColumnArithmeticFunction<T extends Number,
        U extends AbstractMultipleColumnArithmeticFunction<T, U>>
        extends AbstractFunction<T, AbstractMultipleColumnArithmeticFunction<T,U>> {
    
    protected BasicColumn secondColumn;
    protected List<BasicColumn> subsequentColumns = new ArrayList<>();
    
    protected AbstractMultipleColumnArithmeticFunction(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            List<BasicColumn> subsequentColumns) {
        super(firstColumn);
        this.secondColumn = Objects.requireNonNull(secondColumn);
        this.subsequentColumns.addAll(subsequentColumns);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        // note - the cast below is added for a type inference bug in the Java9 compiler.
        return Stream.of(Stream.of((BasicColumn) column), Stream.of(secondColumn), subsequentColumns.stream())
                .flatMap(Function.identity())
                .map(column -> column.renderWithTableAlias(tableAliasCalculator))
                .collect(Collectors.joining(padOperator(), "(", ")")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    private String padOperator() {
        return " " + operator() + " "; //$NON-NLS-1$ //$NON-NLS-2$
    }

    protected abstract String operator();
}
