
package org.mybatis.dynamic.sql.delete.render;

import java.util.Map;

public interface DeleteStatementProvider {
    Map<String, Object> getParameters();
    
    String getDeleteStatement();
}