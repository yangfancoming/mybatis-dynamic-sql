
package org.mybatis.dynamic.sql.util.mybatis3;

import java.util.function.BiFunction;

import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;

/**
 * Represents a function that can be used to set values in an "UpdateByExample" method in the style
 * of MyBatis Generator. When using this function, you can create a method that will map record fields to
 * tables columns to be updated in a common mapper, and then allow a user to set a where clause as needed.
 * 
 * @author Jeff Butler
 *
 * @see MyBatis3UpdateByExampleHelper
 * 
 * @param <T> the type of record that will be updated
 */
@FunctionalInterface
public interface MyBatis3UpdateByExampleValueSetter<T> extends
        BiFunction<T, UpdateDSL<MyBatis3UpdateModelAdapter<Integer>>, UpdateDSL<MyBatis3UpdateModelAdapter<Integer>>> {
}
