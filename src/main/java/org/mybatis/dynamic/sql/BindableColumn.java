
package org.mybatis.dynamic.sql;

import java.sql.JDBCType;
import java.util.Optional;

/**
 * Describes additional attributes of columns that are necessary for binding the column as a JDBC parameter.
 * Columns in where clauses are typically bound.
 * 
 * @author Jeff Butler
 *
 * @param <T> - even though the type is not directly used in this class,
 *     it is used by the compiler to match columns with conditions so it should
 *     not be removed.
*/
public interface BindableColumn<T> extends BasicColumn {

    /**
     * Override the base method definition to make it more specific to this interface. 
     */
    @Override
    BindableColumn<T> as(String alias);

    Optional<JDBCType> jdbcType();
    
    Optional<String> typeHandler();
}
