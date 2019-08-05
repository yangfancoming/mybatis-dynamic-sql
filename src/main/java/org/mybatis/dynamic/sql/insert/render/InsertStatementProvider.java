
package org.mybatis.dynamic.sql.insert.render;

public interface InsertStatementProvider<T> {
    T getRecord();
    
    String getInsertStatement();
}
