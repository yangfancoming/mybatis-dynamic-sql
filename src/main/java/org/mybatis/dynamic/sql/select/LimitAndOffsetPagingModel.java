
package org.mybatis.dynamic.sql.select;

import java.util.Objects;
import java.util.Optional;

public class LimitAndOffsetPagingModel implements PagingModel {

    private Long limit;
    private Long offset;
    
    private LimitAndOffsetPagingModel(Builder builder) {
        this.limit = Objects.requireNonNull(builder.limit);
        this.offset = builder.offset;
    }
    
    public Long limit() {
        return limit;
    }
    
    public Optional<Long> offset() {
        return Optional.ofNullable(offset);
    }
    
    @Override
    public <R> R accept(PagingModelVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public static class Builder {
        private Long limit;
        private Long offset;

        public Builder withLimit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        public Builder withOffset(Long offset) {
            this.offset = offset;
            return this;
        }
        
        public LimitAndOffsetPagingModel build() {
            return new LimitAndOffsetPagingModel(this);
        }
    }
}
