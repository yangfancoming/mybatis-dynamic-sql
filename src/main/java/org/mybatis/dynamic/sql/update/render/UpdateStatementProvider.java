
package org.mybatis.dynamic.sql.update.render;

import java.util.Map;

public interface UpdateStatementProvider {
    Map<String, Object> getParameters();

    String getUpdateStatement();
}
