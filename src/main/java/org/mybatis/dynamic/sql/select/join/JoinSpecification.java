
package org.mybatis.dynamic.sql.select.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import org.mybatis.dynamic.sql.SqlTable;

public class JoinSpecification {

    private SqlTable table;
    private List<JoinCriterion> joinCriteria;
    private JoinType joinType;
    
    private JoinSpecification(Builder builder) {
        table = Objects.requireNonNull(builder.table);
        joinCriteria = Objects.requireNonNull(builder.joinCriteria);
        joinType = Objects.requireNonNull(builder.joinType);
    }
    
    public SqlTable table() {
        return table;
    }
    
    public <R> Stream<R> mapJoinCriteria(Function<JoinCriterion, R> mapper) {
        return joinCriteria.stream().map(mapper);
    }
    
    public JoinType joinType() {
        return joinType;
    }
    
    public static Builder withJoinTable(SqlTable table) {
        return new Builder().withJoinTable(table);
    }
    
    public static class Builder {
        private SqlTable table;
        private List<JoinCriterion> joinCriteria = new ArrayList<>();
        private JoinType joinType;
        
        public Builder withJoinTable(SqlTable table) {
            this.table = table;
            return this;
        }
        
        public Builder withJoinCriteria(List<JoinCriterion> joinCriteria) {
            this.joinCriteria.addAll(joinCriteria);
            return this;
        }
        
        public Builder withJoinType(JoinType joinType) {
            this.joinType = joinType;
            return this;
        }
        
        public JoinSpecification build() {
            return new JoinSpecification(this);
        }
    }
}
