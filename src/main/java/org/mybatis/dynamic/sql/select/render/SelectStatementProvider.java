
package org.mybatis.dynamic.sql.select.render;

import java.util.Map;

public interface SelectStatementProvider {
    Map<String, Object> getParameters();

    String getSelectStatement();
}
