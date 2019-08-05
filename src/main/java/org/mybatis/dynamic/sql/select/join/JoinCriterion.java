
package org.mybatis.dynamic.sql.select.join;

import java.util.Objects;

import org.mybatis.dynamic.sql.BasicColumn;

public class JoinCriterion {

    private String connector;
    private BasicColumn leftColumn;
    private JoinCondition joinCondition;
    
    private JoinCriterion(Builder builder) {
        connector = Objects.requireNonNull(builder.connector);
        leftColumn = Objects.requireNonNull(builder.joinColumn);
        joinCondition = Objects.requireNonNull(builder.joinCondition);
    }

    public String connector() {
        return connector;
    }
    
    public BasicColumn leftColumn() {
        return leftColumn;
    }
    
    public BasicColumn rightColumn() {
        return joinCondition.rightColumn();
    }
    
    public String operator() {
        return joinCondition.operator();
    }
    
    public static Builder withJoinColumn(BasicColumn joinColumn) {
        return new Builder().withJoinColumn(joinColumn);
    }
    
    public static class Builder {
        private BasicColumn joinColumn;
        private JoinCondition joinCondition;
        private String connector;
        
        public Builder withJoinColumn(BasicColumn joinColumn) {
            this.joinColumn = joinColumn;
            return this;
        }
        
        public Builder withJoinCondition(JoinCondition joinCondition) {
            this.joinCondition = joinCondition;
            return this;
        }
        
        public Builder withConnector(String connector) {
            this.connector = connector;
            return this;
        }
        
        public JoinCriterion build() {
            return new JoinCriterion(this);
        }
    }
}
