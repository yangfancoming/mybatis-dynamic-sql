
package org.mybatis.dynamic.sql.util;

import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertSelectStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

/**
 * Adapter for use with MyBatis SQL provider annotations.
 * 
 * @author Jeff Butler
 *
 */
public class SqlProviderAdapter {

    public String delete(DeleteStatementProvider deleteStatement) {
        return deleteStatement.getDeleteStatement();
    }
    
    public String insert(InsertStatementProvider<?> insertStatement) {
        return insertStatement.getInsertStatement();
    }
    
    public String insertMultiple(MultiRowInsertStatementProvider<?> insertStatement) {
        return insertStatement.getInsertStatement();
    }
    
    public String insertSelect(InsertSelectStatementProvider insertStatement) {
        return insertStatement.getInsertStatement();
    }
    
    public String select(SelectStatementProvider selectStatement) {
        return selectStatement.getSelectStatement();
    }

    public String update(UpdateStatementProvider updateStatement) {
        return updateStatement.getUpdateStatement();
    }
}
