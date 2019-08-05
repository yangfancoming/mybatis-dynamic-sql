
package org.mybatis.dynamic.sql;

import java.util.Optional;

import org.mybatis.dynamic.sql.render.TableAliasCalculator;

/**
 * Describes attributes of columns that are necessary for rendering if the column is not expected to
 * be bound as a JDBC parameter.  Columns in select lists, join expressions, and group by expressions
 * are typically not bound.
 * 
 * @author Jeff Butler
 *
 */
public interface BasicColumn {

    /**
     * Returns the columns alias if one has been specified.
     * 
     * @return the column alias
     */
    Optional<String> alias();
    
    /**
     * Returns a new instance of a BasicColumn with the alias set.
     * 
     * @param alias the column alias to set
     * @return new instance with alias set
     */
    BasicColumn as(String alias);
    
    /**
     * Returns the name of the item aliased with a table name if appropriate.
     * For example, "a.foo".  This is appropriate for where clauses and order by clauses.
     * 
     * @param tableAliasCalculator the table alias calculator for the current renderer
     * @return the item name with the table alias applied
     */
    String renderWithTableAlias(TableAliasCalculator tableAliasCalculator);
    
    /**
     * Returns the name of the item aliased with a table name and column alias if appropriate.
     * For example, "a.foo as bar".  This is appropriate for select list clauses.
     * 
     * @param tableAliasCalculator the table alias calculator for the current renderer
     * @return the item name with the table and column aliases applied
     */
    default String renderWithTableAndColumnAlias(TableAliasCalculator tableAliasCalculator) {
        String nameAndTableAlias = renderWithTableAlias(tableAliasCalculator);
        
        return alias().map(a -> nameAndTableAlias + " as " + a) //$NON-NLS-1$
                .orElse(nameAndTableAlias);
    }
}
