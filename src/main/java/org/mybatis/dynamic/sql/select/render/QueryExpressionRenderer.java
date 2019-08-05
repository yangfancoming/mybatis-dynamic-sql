
package org.mybatis.dynamic.sql.select.render;

import static org.mybatis.dynamic.sql.util.StringUtilities.spaceAfter;
import static org.mybatis.dynamic.sql.util.StringUtilities.spaceBefore;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.GroupByModel;
import org.mybatis.dynamic.sql.select.QueryExpressionModel;
import org.mybatis.dynamic.sql.select.join.JoinModel;
import org.mybatis.dynamic.sql.util.CustomCollectors;
import org.mybatis.dynamic.sql.util.FragmentAndParameters;
import org.mybatis.dynamic.sql.where.WhereModel;
import org.mybatis.dynamic.sql.where.render.WhereClauseProvider;
import org.mybatis.dynamic.sql.where.render.WhereRenderer;

public class QueryExpressionRenderer {
    private QueryExpressionModel queryExpression;
    private RenderingStrategy renderingStrategy;
    private AtomicInteger sequence;
    
    private QueryExpressionRenderer(Builder builder) {
        queryExpression = Objects.requireNonNull(builder.queryExpression);
        renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
        sequence = Objects.requireNonNull(builder.sequence);
    }
    
    public FragmentAndParameters render() {
        Optional<WhereClauseProvider> whereClause = queryExpression.whereModel().flatMap(this::renderWhereClause);
        
        return FragmentAndParameters.withFragment(calculateQueryExpression(whereClause))
                .withParameters(calculateParameters(whereClause))
                .build();
    }
    
    private String calculateQueryExpression(Optional<WhereClauseProvider> whereClause) {
        return spaceAfter(queryExpression.connector())
                + "select " //$NON-NLS-1$
                + (queryExpression.isDistinct() ? "distinct " : "") //$NON-NLS-1$ //$NON-NLS-2$
                + calculateColumnList()
                + " from " //$NON-NLS-1$
                + calculateTableName(queryExpression.table())
                + spaceBefore(queryExpression.joinModel().map(this::renderJoin))
                + spaceBefore(whereClause.map(WhereClauseProvider::getWhereClause))
                + spaceBefore(queryExpression.groupByModel().map(this::renderGroupBy));
    }

    private Map<String, Object> calculateParameters(Optional<WhereClauseProvider> whereClause) {
        return whereClause
                .map(WhereClauseProvider::getParameters)
                .orElse(Collections.emptyMap());
    }

    private String calculateColumnList() {
        return queryExpression.mapColumns(this::applyTableAndColumnAlias)
                .collect(Collectors.joining(", ")); //$NON-NLS-1$
    }

    private String calculateTableName(SqlTable table) {
        return queryExpression.calculateTableNameIncludingAlias(table);
    }
    
    private String applyTableAndColumnAlias(BasicColumn selectListItem) {
        return selectListItem.renderWithTableAndColumnAlias(queryExpression.tableAliasCalculator());
    }
    
    private String renderJoin(JoinModel joinModel) {
        return JoinRenderer.withJoinModel(joinModel)
                .withQueryExpression(queryExpression)
                .build()
                .render();
    }
    
    private Optional<WhereClauseProvider> renderWhereClause(WhereModel whereModel) {
        return WhereRenderer.withWhereModel(whereModel)
                .withRenderingStrategy(renderingStrategy)
                .withTableAliasCalculator(queryExpression.tableAliasCalculator())
                .withSequence(sequence)
                .build()
                .render();
    }

    private String renderGroupBy(GroupByModel groupByModel) {
        return groupByModel.mapColumns(this::applyTableAlias)
                .collect(CustomCollectors.joining(", ", "group by ", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
    
    private String applyTableAlias(BasicColumn column) {
        return column.renderWithTableAlias(queryExpression.tableAliasCalculator());
    }
    
    public static Builder withQueryExpression(QueryExpressionModel model) {
        return new Builder().withQueryExpression(model);
    }
    
    public static class Builder {
        private QueryExpressionModel queryExpression;
        private RenderingStrategy renderingStrategy;
        private AtomicInteger sequence;
        
        public Builder withQueryExpression(QueryExpressionModel queryExpression) {
            this.queryExpression = queryExpression;
            return this;
        }
        
        public Builder withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }
        
        public Builder withSequence(AtomicInteger sequence) {
            this.sequence = sequence;
            return this;
        }
        
        public QueryExpressionRenderer build() {
            return new QueryExpressionRenderer(this);
        }
    }
}
