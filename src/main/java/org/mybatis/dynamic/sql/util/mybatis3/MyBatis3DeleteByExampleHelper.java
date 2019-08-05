
package org.mybatis.dynamic.sql.util.mybatis3;

import java.util.function.Function;

import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter;
import org.mybatis.dynamic.sql.util.Buildable;

/**
 * Represents a function that can be used to create a "DeleteByExample" method in the style
 * of MyBatis Generator. When using this function, you can create a method that does not require a user to
 * call the build().execute() methods - making client code look a bit cleaner.
 * 
 * <p>For example, you can create mapper interface methods like this:
 * 
 * <pre>
 * &#64;DeleteProvider(type=SqlProviderAdapter.class, method="delete")
 * int delete(DeleteStatementProvider deleteStatement);
 *   
 * default int deleteByExample(MyBatis3DeleteByExampleHelper helper) {
 *     return helper.apply(DeleteDSL.deleteFromWithMapper(this::delete, simpleTable))
 *           .build()
 *           .execute();
 * }
 * </pre>
 * 
 * <p>And then call the simplified default method like this:
 * 
 * <pre>
 * int rows = mapper.deleteByExample(q -&gt;
 *           q.where(occupation, isNull()));
 * </pre>
 *  
 * <p>You can implement a "delete all" with the following code:
 * 
 * <pre>
 * int rows = mapper.deleteByExample(q -&gt; q);
 * </pre>
 * 
 * <p>Or
 * 
 * <pre>
 * long rows = mapper.deleteByExample(MyBatis3DeleteByExampleHelper.allRows());
 * </pre>

 * @author Jeff Butler
 */
@FunctionalInterface
public interface MyBatis3DeleteByExampleHelper extends
        Function<DeleteDSL<MyBatis3DeleteModelAdapter<Integer>>, Buildable<MyBatis3DeleteModelAdapter<Integer>>> {

    /**
     * Returns a helper that can be used to delete every row in a table.
     * 
     * @return the helper that will delete every row in a table
     */
    static MyBatis3DeleteByExampleHelper allRows() {
        return h -> h;
    }
}
