
package org.mybatis.dynamic.sql;

import java.util.Objects;
import java.util.Optional;

import org.mybatis.dynamic.sql.render.TableAliasCalculator;

public class Constant implements BasicColumn {

    private String alias;
    private String value;
    
    private Constant(String value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return value;
    }

    @Override
    public Constant as(String alias) {
        Constant copy = new Constant(value);
        copy.alias = alias;
        return copy;
    }
    
    public static Constant of(String value) {
        return new Constant(value);
    }
}
