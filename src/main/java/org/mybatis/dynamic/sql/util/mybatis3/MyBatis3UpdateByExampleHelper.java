
package org.mybatis.dynamic.sql.util.mybatis3;

import java.util.function.Function;

import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.util.Buildable;

/**
 * Represents a function that can be used to create an "UpdateByExample" method in the style
 * of MyBatis Generator. When using this function, you can create a method that does not require a user to
 * call the build().execute() methods - making client code look a bit cleaner.
 * 
 * <p>For example, you can create mapper interface methods like this:
 * 
 * <pre>
 * &#64;UpdateProvider(type=SqlProviderAdapter.class, method="update")
 * int update(UpdateStatementProvider updateStatement);
 *   
 * default MyBatis3UpdateByExampleCompleter updateByExampleSelective(MyBatis3UpdateByExampleHelper helper) {
 *     return new MyBatis3UpdateByExampleCompleter.Builder&lt;SimpleTableRecord&gt;()
 *             .withHelper(helper)
 *             .withMapper(this::update)
 *             .withTable(simpleTable)
 *             .withValueSetter((record, dsl) -&gt;
 *                 dsl.set(id).equalToWhenPresent(record::getId)
 *                 .set(firstName).equalToWhenPresent(record::getFirstName)
 *                 .set(lastName).equalToWhenPresent(record::getLastName)
 *                 .set(birthDate).equalToWhenPresent(record::getBirthDate)
 *                 .set(employed).equalToWhenPresent(record::getEmployed)
 *                 .set(occupation).equalToWhenPresent(record::getOccupation))
 *             .build();
 * }
 * </pre>
 * 
 * <p>And then call the simplified default method like this:
 * 
 * <pre>
 * int rows = mapper.updateByExampleSelective(q -&gt;
 *                q.where(id, isEqualTo(100))
 *                .and(firstName, isEqualTo("Joe")))
 *                .usingRecord(record);
 * </pre>
 *  
 * <p>You can implement an "update all" with the following code:
 * 
 * <pre>
 * int rows = mapper.updateByExampleSelective(q -&gt; q)
 *                .usingRecord(record);
 * </pre>
 * 
 * <p>Or
 * 
 * <pre>
 * int rows = mapper.updateByExampleSelective(MyBatis3UpdateByExampleHelper.allRows())
 *                .usingRecord(record);
 * </pre>
 * 
 * @see MyBatis3UpdateByExampleCompleter
 * @see MyBatis3UpdateByExampleValueSetter
 * 
 * @author Jeff Butler
 */
@FunctionalInterface
public interface MyBatis3UpdateByExampleHelper extends
        Function<UpdateDSL<MyBatis3UpdateModelAdapter<Integer>>, Buildable<MyBatis3UpdateModelAdapter<Integer>>> {

    /**
     * Returns a helper that can be used to update every row in a table.
     * 
     * @return the helper that will update every row in a table
     */
    static MyBatis3UpdateByExampleHelper allRows() {
        return h -> h;
    }
}
