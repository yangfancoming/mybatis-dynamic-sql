
package org.mybatis.dynamic.sql.delete;

import java.util.Objects;
import java.util.Optional;

import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.delete.render.DeleteRenderer;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.where.WhereModel;

public class DeleteModel {
    private SqlTable table;
    private WhereModel whereModel;
    
    private DeleteModel(Builder builder) {
        table = Objects.requireNonNull(builder.table);
        whereModel = builder.whereModel;
    }
    
    public SqlTable table() {
        return table;
    }
    
    public Optional<WhereModel> whereModel() {
        return Optional.ofNullable(whereModel);
    }
    
    public DeleteStatementProvider render(RenderingStrategy renderingStrategy) {
        return DeleteRenderer.withDeleteModel(this)
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
        
        public Builder withTable(SqlTable table) {
            this.table = table;
            return this;
        }
        
        public Builder withWhereModel(WhereModel whereModel) {
            this.whereModel = whereModel;
            return this;
        }
        
        public DeleteModel build() {
            return new DeleteModel(this);
        }
    }
}
