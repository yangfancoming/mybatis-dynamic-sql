
package org.mybatis.dynamic.sql.update;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.update.render.UpdateRenderer;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.UpdateMapping;
import org.mybatis.dynamic.sql.where.WhereModel;

public class UpdateModel {
    private SqlTable table;
    private WhereModel whereModel;
    private List<UpdateMapping> columnMappings;
    
    private UpdateModel(Builder builder) {
        table = Objects.requireNonNull(builder.table);
        whereModel = builder.whereModel;
        columnMappings = Objects.requireNonNull(builder.columnMappings);
    }
    
    public SqlTable table() {
        return table;
    }
    
    public Optional<WhereModel> whereModel() {
        return Optional.ofNullable(whereModel);
    }
    
    public <R> Stream<R> mapColumnMappings(Function<UpdateMapping, R> mapper) {
        return columnMappings.stream().map(mapper);
    }
    
    public UpdateStatementProvider render(RenderingStrategy renderingStrategy) {
        return UpdateRenderer.withUpdateModel(this)
                .withRenderingStrategy(renderingStrategy)
                .build()
                .render();
    }
    
    public static Builder withTable(SqlTable table) {
        return new Builder().withTable(table);
    }
    
    public static class Builder {
        private SqlTable table;
        private WhereModel whereModel;
        private List<UpdateMapping> columnMappings = new ArrayList<>();
        
        public Builder withTable(SqlTable table) {
            this.table = table;
            return this;
        }
        
        public Builder withColumnMappings(List<UpdateMapping> columnMappings) {
            this.columnMappings.addAll(columnMappings);
            return this;
        }
        
        public Builder withWhereModel(WhereModel whereModel) {
            this.whereModel = whereModel;
            return this;
        }
        
        public UpdateModel build() {
            return new UpdateModel(this);
        }
    }
}
