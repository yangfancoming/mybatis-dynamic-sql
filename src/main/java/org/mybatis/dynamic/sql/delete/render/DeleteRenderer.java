
package org.mybatis.dynamic.sql.delete.render;

import static org.mybatis.dynamic.sql.util.StringUtilities.spaceBefore;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.mybatis.dynamic.sql.delete.DeleteModel;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.where.WhereModel;
import org.mybatis.dynamic.sql.where.render.WhereClauseProvider;
import org.mybatis.dynamic.sql.where.render.WhereRenderer;

public class DeleteRenderer {
    private DeleteModel deleteModel;
    private RenderingStrategy renderingStrategy;
    
    private DeleteRenderer(Builder builder) {
        deleteModel = Objects.requireNonNull(builder.deleteModel);
        renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
    }
    
    public DeleteStatementProvider render() {
        Optional<WhereClauseProvider> whereClause = deleteModel.whereModel().flatMap(this::renderWhereClause);
        
        return DefaultDeleteStatementProvider.withDeleteStatement(calculateDeleteStatement(whereClause))
                .withParameters(calculateParameters(whereClause))
                .build();
    }

    private Optional<WhereClauseProvider> renderWhereClause(WhereModel whereModel) {
        return WhereRenderer.withWhereModel(whereModel)
                .withRenderingStrategy(renderingStrategy)
                .withSequence(new AtomicInteger(1))
                .withTableAliasCalculator(TableAliasCalculator.empty())
                .build()
                .render();
    }
    
    private String calculateDeleteStatement(Optional<WhereClauseProvider> whereClause) {
        return "delete from" //$NON-NLS-1$
                + spaceBefore(deleteModel.table().tableNameAtRuntime())
                + spaceBefore(whereClause.map(WhereClauseProvider::getWhereClause));
    }
    
    private Map<String, Object> calculateParameters(Optional<WhereClauseProvider> whereClause) {
        return whereClause
                .map(WhereClauseProvider::getParameters)
                .orElse(Collections.emptyMap());
    }

    public static Builder withDeleteModel(DeleteModel deleteModel) {
        return new Builder().withDeleteModel(deleteModel);
    }
    
    public static class Builder {
        private DeleteModel deleteModel;
        private RenderingStrategy renderingStrategy;

        public Builder withDeleteModel(DeleteModel deleteModel) {
            this.deleteModel = deleteModel;
            return this;
        }
        
        public Builder withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }
        
        public DeleteRenderer build() {
            return new DeleteRenderer(this);
        }
    }
}
