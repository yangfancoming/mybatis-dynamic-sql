
package org.mybatis.dynamic.sql;

import java.util.Objects;
import java.util.Optional;

import org.mybatis.dynamic.sql.render.TableAliasCalculator;

public class StringConstant implements BasicColumn {

    private String alias;
    private String value;
    
    private StringConstant(String value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "'" + value + "'"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public StringConstant as(String alias) {
        StringConstant copy = new StringConstant(value);
        copy.alias = alias;
        return copy;
    }
    
    public static StringConstant of(String value) {
        return new StringConstant(value);
    }
}
