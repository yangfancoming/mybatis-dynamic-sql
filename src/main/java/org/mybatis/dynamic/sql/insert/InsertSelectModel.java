
package org.mybatis.dynamic.sql.insert;

import java.util.Objects;
import java.util.Optional;

import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.insert.render.InsertSelectRenderer;
import org.mybatis.dynamic.sql.insert.render.InsertSelectStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.SelectModel;

public class InsertSelectModel {
    private SqlTable table;
    private InsertColumnListModel columnList;
    private SelectModel selectModel;
    
    private InsertSelectModel(Builder builder) {
        table = Objects.requireNonNull(builder.table);
        columnList = builder.columnList;
        selectModel = Objects.requireNonNull(builder.selectModel);
    }

    public SqlTable table() {
        return table;
    }
    
    public SelectModel selectModel() {
        return selectModel;
    }
    
    public Optional<InsertColumnListModel> columnList() {
        return Optional.ofNullable(columnList);
    }
    
    public InsertSelectStatementProvider render(RenderingStrategy renderingStrategy) {
        return InsertSelectRenderer.withInsertSelectModel(this)
                .withRenderingStrategy(renderingStrategy)
                .build()
                .render();
    }
    
    public static Builder withTable(SqlTable table) {
        return new Builder().withTable(table);
    }
    
    public static class Builder {
        private SqlTable table;
        private InsertColumnListModel columnList;
        private SelectModel selectModel;
        
        public Builder withTable(SqlTable table) {
            this.table = table;
            return this;
        }
        
        public Builder withColumnList(InsertColumnListModel columnList) {
            this.columnList = columnList;
            return this;
        }
        
        public Builder withSelectModel(SelectModel selectModel) {
            this.selectModel = selectModel;
            return this;
        }
        
        public InsertSelectModel build() {
            return new InsertSelectModel(this);
        }
    }
}
