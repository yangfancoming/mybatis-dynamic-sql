
package org.mybatis.dynamic.sql.insert.render;

import static org.mybatis.dynamic.sql.util.StringUtilities.spaceBefore;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.insert.InsertColumnListModel;
import org.mybatis.dynamic.sql.insert.InsertSelectModel;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class InsertSelectRenderer {

    private InsertSelectModel model;
    private RenderingStrategy renderingStrategy;
    
    private InsertSelectRenderer(Builder builder) {
        model = Objects.requireNonNull(builder.model);
        renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
    }
    
    public InsertSelectStatementProvider render() {
        SelectStatementProvider selectStatement = model.selectModel().render(renderingStrategy);
        
        return DefaultInsertSelectStatementProvider.withInsertStatement(calculateInsertStatement(selectStatement))
                .withParameters(selectStatement.getParameters())
                .build();
    }
    
    private String calculateInsertStatement(SelectStatementProvider selectStatement) {
        return "insert into" //$NON-NLS-1$
                + spaceBefore(model.table().tableNameAtRuntime())
                + spaceBefore(calculateColumnsPhrase())
                + spaceBefore(selectStatement.getSelectStatement());
    }
    
    private Optional<String> calculateColumnsPhrase() {
        return model.columnList()
                .map(this::calculateColumnsPhrase);
    }
    
    private String calculateColumnsPhrase(InsertColumnListModel columnList) {
        return columnList.mapColumns(SqlColumn::name)
                .collect(Collectors.joining(", ", "(", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
    
    public static Builder withInsertSelectModel(InsertSelectModel model) {
        return new Builder().withInsertSelectModel(model);
    }
    
    public static class Builder {
        private InsertSelectModel model;
        private RenderingStrategy renderingStrategy;
        
        public Builder withInsertSelectModel(InsertSelectModel model) {
            this.model = model;
            return this;
        }
        
        public Builder withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }
        
        public InsertSelectRenderer build() {
            return new InsertSelectRenderer(this);
        }
    }
}
