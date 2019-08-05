
package org.mybatis.dynamic.sql.insert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.util.ConstantMapping;
import org.mybatis.dynamic.sql.util.InsertMapping;
import org.mybatis.dynamic.sql.util.NullMapping;
import org.mybatis.dynamic.sql.util.PropertyMapping;
import org.mybatis.dynamic.sql.util.StringConstantMapping;

public class MultiRowInsertDSL<T> {

    private Collection<T> records;
    private SqlTable table;
    private List<InsertMapping> columnMappings = new ArrayList<>();
    
    private MultiRowInsertDSL(Collection<T> records, SqlTable table) {
        this.records = records;
        this.table = table;
    }
    
    public <F> ColumnMappingFinisher<F> map(SqlColumn<F> column) {
        return new ColumnMappingFinisher<>(column);
    }
    
    public MultiRowInsertModel<T> build() {
        return MultiRowInsertModel.withRecords(records)
                .withTable(table)
                .withColumnMappings(columnMappings)
                .build();
    }

    @SafeVarargs
    public static <T> IntoGatherer<T> insert(T...records) {
        return MultiRowInsertDSL.insert(Arrays.asList(records));
    }
    
    public static <T> IntoGatherer<T> insert(Collection<T> records) {
        return new IntoGatherer<>(records);
    }
    
    public static class IntoGatherer<T> {
        private Collection<T> records;
        
        private IntoGatherer(Collection<T> records) {
            this.records = records;
        }

        public MultiRowInsertDSL<T> into(SqlTable table) {
            return new MultiRowInsertDSL<>(records, table);
        }
    }
    
    public class ColumnMappingFinisher<F> {
        private SqlColumn<F> column;
            
        public ColumnMappingFinisher(SqlColumn<F> column) {
            this.column = column;
        }
            
        public MultiRowInsertDSL<T> toProperty(String property) {
            columnMappings.add(PropertyMapping.of(column, property));
            return MultiRowInsertDSL.this;
        }
            
        public MultiRowInsertDSL<T> toNull() {
            columnMappings.add(NullMapping.of(column));
            return MultiRowInsertDSL.this;
        }
            
        public MultiRowInsertDSL<T> toConstant(String constant) {
            columnMappings.add(ConstantMapping.of(column, constant));
            return MultiRowInsertDSL.this;
        }
            
        public MultiRowInsertDSL<T> toStringConstant(String constant) {
            columnMappings.add(StringConstantMapping.of(column, constant));
            return MultiRowInsertDSL.this;
        }
    }
}
