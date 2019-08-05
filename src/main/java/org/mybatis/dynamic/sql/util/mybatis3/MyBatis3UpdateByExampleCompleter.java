
package org.mybatis.dynamic.sql.util.mybatis3;

import java.util.Objects;
import java.util.function.ToIntFunction;

import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

/**
 * This class is used to complete an update by example method in the style of MyBatis generator.
 * 
 * @author Jeff Butler
 *
 * @see MyBatis3UpdateByExampleHelper
 * 
 * @param <T> the type of record that will be updated
 */
public class MyBatis3UpdateByExampleCompleter<T> {
    private SqlTable table;
    private MyBatis3UpdateByExampleHelper helper;
    private ToIntFunction<UpdateStatementProvider> mapper;
    private MyBatis3UpdateByExampleValueSetter<T> valueSetter;
    
    private MyBatis3UpdateByExampleCompleter(MyBatis3UpdateByExampleCompleter.Builder<T> builder) {
        helper = Objects.requireNonNull(builder.helper);
        mapper = Objects.requireNonNull(builder.mapper);
        valueSetter = Objects.requireNonNull(builder.valueSetter);
        table = Objects.requireNonNull(builder.table);
    }
    
    public int usingRecord(T record) {
        return valueSetter.andThen(helper)
                .apply(record, UpdateDSL.updateWithMapper(p -> mapper.applyAsInt(p), table))
                .build()
                .execute();
    }
    
    public static class Builder<T> {
        private SqlTable table;
        private MyBatis3UpdateByExampleHelper helper;
        private ToIntFunction<UpdateStatementProvider> mapper;
        private MyBatis3UpdateByExampleValueSetter<T> valueSetter;
        
        public MyBatis3UpdateByExampleCompleter.Builder<T> withTable(SqlTable table) {
            this.table = table;
            return this;
        }
        
        public MyBatis3UpdateByExampleCompleter.Builder<T> withHelper(MyBatis3UpdateByExampleHelper helper) {
            this.helper = helper;
            return this;
        }

        public MyBatis3UpdateByExampleCompleter.Builder<T> withMapper(
                ToIntFunction<UpdateStatementProvider> mapper) {
            this.mapper = mapper;
            return this;
        }
        
        public MyBatis3UpdateByExampleCompleter.Builder<T> withValueSetter(
                MyBatis3UpdateByExampleValueSetter<T> valueSetter) {
            this.valueSetter = valueSetter;
            return this;
        }
        
        public MyBatis3UpdateByExampleCompleter<T> build() {
            return new MyBatis3UpdateByExampleCompleter<>(this);
        }
    }
}
