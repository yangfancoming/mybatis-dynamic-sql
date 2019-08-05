
package org.mybatis.dynamic.sql.util.springbatch;

import java.util.Map;

import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class SpringBatchProviderAdapter {

    public String select(Map<String, Object> parameterValues) {
        SelectStatementProvider selectStatement =
                (SelectStatementProvider) parameterValues.get(SpringBatchUtility.PARAMETER_KEY);
        return selectStatement.getSelectStatement();
    }
}
