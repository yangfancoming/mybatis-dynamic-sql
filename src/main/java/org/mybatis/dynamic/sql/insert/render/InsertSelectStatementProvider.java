
package org.mybatis.dynamic.sql.insert.render;

import java.util.Map;

public interface InsertSelectStatementProvider {
    Map<String, Object> getParameters();

    String getInsertStatement();
}
