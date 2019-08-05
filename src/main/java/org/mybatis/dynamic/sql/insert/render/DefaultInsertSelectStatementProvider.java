
package org.mybatis.dynamic.sql.insert.render;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DefaultInsertSelectStatementProvider implements InsertSelectStatementProvider {
    private String insertStatement;
    private Map<String, Object> parameters;
    
    private DefaultInsertSelectStatementProvider(Builder builder) {
        insertStatement = Objects.requireNonNull(builder.insertStatement);
        parameters = Objects.requireNonNull(builder.parameters);
    }
    
    @Override
    public String getInsertStatement() {
        return insertStatement;
    }
    
    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    public static Builder withInsertStatement(String insertStatement) {
        return new Builder().withInsertStatement(insertStatement);
    }
    
    public static class Builder {
        private String insertStatement;
        private Map<String, Object> parameters = new HashMap<>();
        
        public Builder withInsertStatement(String insertStatement) {
            this.insertStatement = insertStatement;
            return this;
        }

        public Builder withParameters(Map<String, Object> parameters) {
            this.parameters.putAll(parameters);
            return this;
        }
        
        public DefaultInsertSelectStatementProvider build() {
            return new DefaultInsertSelectStatementProvider(this);
        }
    }
}
