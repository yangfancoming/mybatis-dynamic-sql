
package org.mybatis.dynamic.sql.select;

import java.util.Optional;

public class FetchFirstPagingModel implements PagingModel {

    private Long fetchFirstRows;
    private Long offset;
    
    private FetchFirstPagingModel(Builder builder) {
        this.fetchFirstRows = builder.fetchFirstRows;
        this.offset = builder.offset;
    }
    
    public Optional<Long> fetchFirstRows() {
        return Optional.ofNullable(fetchFirstRows);
    }
    
    public Optional<Long> offset() {
        return Optional.ofNullable(offset);
    }
    
    @Override
    public <R> R accept(PagingModelVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public static class Builder {
        private Long fetchFirstRows;
        private Long offset;

        public Builder withFetchFirstRows(Long fetchFirstRows) {
            this.fetchFirstRows = fetchFirstRows;
            return this;
        }
        
        public Builder withOffset(Long offset) {
            this.offset = offset;
            return this;
        }
        
        public FetchFirstPagingModel build() {
            return new FetchFirstPagingModel(this);
        }
    }
}
