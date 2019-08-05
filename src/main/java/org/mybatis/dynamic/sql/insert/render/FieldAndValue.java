
package org.mybatis.dynamic.sql.insert.render;

import java.util.Objects;

public class FieldAndValue {
    private String fieldName;
    private String valuePhrase;
    
    private FieldAndValue(Builder builder) {
        fieldName = Objects.requireNonNull(builder.fieldName);
        valuePhrase = Objects.requireNonNull(builder.valuePhrase);
    }
    
    public String fieldName() {
        return fieldName;
    }
    
    public String valuePhrase() {
        return valuePhrase;
    }
    
    public String valuePhrase(int row) {
        return String.format(valuePhrase, row);
    }

    public static Builder withFieldName(String fieldName) {
        return new Builder().withFieldName(fieldName);
    }
    
    public static class Builder {
        private String fieldName;
        private String valuePhrase;

        public Builder withFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }
        
        public Builder withValuePhrase(String valuePhrase) {
            this.valuePhrase = valuePhrase;
            return this;
        }
        
        public FieldAndValue build() {
            return new FieldAndValue(this);
        }
    }
}
