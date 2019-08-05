
package org.mybatis.dynamic.sql.select.function;

import java.sql.JDBCType;
import java.util.Objects;
import java.util.Optional;

import org.mybatis.dynamic.sql.BindableColumn;

public abstract class AbstractFunction<T, U extends AbstractFunction<T, U>> implements BindableColumn<T> {
    protected BindableColumn<T> column;
    protected String alias;

    protected AbstractFunction(BindableColumn<T> column) {
        this.column = Objects.requireNonNull(column);
    }
    
    @Override
    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public U as(String alias) {
        U newThing = copy();
        newThing.alias = alias;
        return newThing;
    }

    @Override
    public Optional<JDBCType> jdbcType() {
        return column.jdbcType();
    }

    @Override
    public Optional<String> typeHandler() {
        return column.typeHandler();
    }
    
    protected abstract U copy();
}
