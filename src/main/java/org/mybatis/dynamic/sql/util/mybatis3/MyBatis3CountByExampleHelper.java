
package org.mybatis.dynamic.sql.util.mybatis3;

import java.util.function.Function;

import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.util.Buildable;

/**
 * Represents a function that can be used to create a "CountByExample" method in the style
 * of MyBatis Generator. When using this function, you can create a method that does not require a user to
 * call the build().execute() methods - making client code look a bit cleaner.
 * 
 * <p>For example, you can create mapper interface methods like this:
 * 
 * <pre>
 * &#64;SelectProvider(type=SqlProviderAdapter.class, method="select")
 * long count(SelectStatementProvider selectStatement);
 *   
 * default long countByExample(MyBatis3CountByExampleHelper helper) {
 *     return helper.apply(SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
 *             .from(simpleTable))
 *             .build()
 *             .execute();
 * }
 * </pre>
 * 
 * <p>And then call the simplified default method like this:
 * 
 * <pre>
 * long rows = mapper.countByExample(q -&gt;
 *         q.where(occupation, isNull()));
 * </pre>
 * 
 * <p>You can implement a "count all" with the following code:
 * 
 * <pre>
 * long rows = mapper.countByExample(q -&gt; q);
 * </pre>
 * 
 * <p>Or
 * 
 * <pre>
 * long rows = mapper.countByExample(MyBatis3CountByExampleHelper.allRows());
 * </pre>
 *  
 * @author Jeff Butler
 */
@FunctionalInterface
public interface MyBatis3CountByExampleHelper extends
        Function<QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>>, Buildable<MyBatis3SelectModelAdapter<Long>>> {
    
    /**
     * Returns a helper that can be used to count every row in a table.
     * 
     * @return the helper that will count every row in a table
     */
    static MyBatis3CountByExampleHelper allRows() {
        return h -> h;
    }
}
