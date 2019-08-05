
package org.mybatis.dynamic.sql.select.aggregate;

import java.util.Optional;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;

/**
 * CountAll seems like the other aggregates, but it is special because there is no column.
 * Rather than dealing with a useless and confusing abstraction, we simply implement
 * BasicColumn directly.
 *  
 * @author Jeff Butler
 */
public class CountAll implements BasicColumn {
    
    private String alias;

    public CountAll() {
        super();
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "count(*)"; //$NON-NLS-1$
    }

    @Override
    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public CountAll as(String alias) {
        CountAll copy = new CountAll();
        copy.alias = alias;
        return copy;
    }
}
