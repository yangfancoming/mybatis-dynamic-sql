
package org.mybatis.dynamic.sql.select.aggregate;

import java.util.Objects;
import java.util.Optional;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

/**
 * This class is the base class for aggregate functions.
 * 
 * @author Jeff Butler
 *
 * @param <T> the subclass type
 */
public abstract class AbstractAggregate<T extends AbstractAggregate<T>> implements BasicColumn {
    protected BasicColumn column;
    protected String alias;

    protected AbstractAggregate(BasicColumn column) {
        this.column = Objects.requireNonNull(column);
    }

    @Override
    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return render(column.renderWithTableAlias(tableAliasCalculator));
    }
    
    @Override
    public T as(String alias) {
        T copy = copy();
        copy.alias = alias;
        return copy;
    }

    protected abstract T copy();
    
    /**
     * Calculate the rendered string for the select list.
     * 
     * @param columnName the calculated column name.  It will have the table alias already applied
     *     if applicable.
     * @return the rendered string for the select list
     */
    protected abstract String render(String columnName);
}
