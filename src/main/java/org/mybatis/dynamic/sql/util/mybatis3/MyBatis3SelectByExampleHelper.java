
package org.mybatis.dynamic.sql.util.mybatis3;

import java.util.List;
import java.util.function.Function;

import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.util.Buildable;

/**
 * Represents a function that can be used to create a "SelectByExample" method in the style
 * of MyBatis Generator. When using this function, you can create a method that does not require a user to
 * call the build().execute() methods - making client code look a bit cleaner.
 * 
 * <p>For example, you can create mapper interface methods like this:
 * 
 * <pre>
 * &#64;SelectProvider(type=SqlProviderAdapter.class, method="select")
 * &#64;Results(id="SimpleTableResult", value= {
 *         &#64;Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
 *         &#64;Result(column="first_name", property="firstName", jdbcType=JdbcType.VARCHAR),
 *         &#64;Result(column="last_name", property="lastName", jdbcType=JdbcType.VARCHAR),
 *         &#64;Result(column="birth_date", property="birthDate", jdbcType=JdbcType.DATE),
 *         &#64;Result(column="employed", property="employed", jdbcType=JdbcType.VARCHAR),
 *         &#64;Result(column="occupation", property="occupation", jdbcType=JdbcType.VARCHAR)
 * })
 * List&lt;SimpleRecord&gt; selectMany(SelectStatementProvider selectStatement);
 *
 * default List&lt;SimpleRecord&gt; selectByExample(MyBatis3SelectByExampleHelper&lt;SimpleRecord&gt; helper) {
 *     return helper.apply(SelectDSL.selectWithMapper(this::selectMany, simpleTable.allColumns())
 *             .from(simpleTable))
 *             .build()
 *             .execute();
 * }
 * </pre>
 * 
 * <p>And then call the simplified default method like this:
 * 
 * <pre>
 * List&lt;SimpleRecord&gt; rows = mapper.selectByExample(q -&gt;
 *     q.where(id, isEqualTo(1))
 *     .or(occupation, isNull()));
 * </pre>
 *  
 * <p>You can implement a "select all" with the following code:
 * 
 * <pre>
 * List&lt;SimpleRecord&gt; rows = mapper.selectByExample(q -&gt; q);
 * </pre>
 * 
 * <p>Or
 * 
 * <pre>
 * List&lt;SimpleRecord&gt; rows = mapper.selectByExample(MyBatis3SelectByExampleHelper.allRows());
 * </pre>
 * 
 * @author Jeff Butler
 */
@FunctionalInterface
public interface MyBatis3SelectByExampleHelper<T> extends
        Function<QueryExpressionDSL<MyBatis3SelectModelAdapter<List<T>>>,
        Buildable<MyBatis3SelectModelAdapter<List<T>>>> {

    /**
     * Returns a helper that can be used to select every row in a table.
     * 
     * @param <T> the type of row returned
     * 
     * @return the helper that will select every row in a table
     */
    static <T> MyBatis3SelectByExampleHelper<T> allRows() {
        return h -> h;
    }

    /**
     * Returns a helper that can be used to select every row in a table with a specified sort order.
     * 
     * @param <T> the type of row returned
     * @param columns sort columns
     * 
     * @return the helper that will select every row in a table in the specified order
     */
    static <T> MyBatis3SelectByExampleHelper<T> allRowsOrderdBy(SortSpecification...columns) {
        return h -> h.orderBy(columns);
    }
}
