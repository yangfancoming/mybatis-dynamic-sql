
package org.mybatis.dynamic.sql.select.render;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.OrderByModel;
import org.mybatis.dynamic.sql.select.PagingModel;
import org.mybatis.dynamic.sql.select.QueryExpressionModel;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.util.CustomCollectors;
import org.mybatis.dynamic.sql.util.FragmentAndParameters;
import org.mybatis.dynamic.sql.util.FragmentCollector;

public class SelectRenderer {
    private SelectModel selectModel;
    private RenderingStrategy renderingStrategy;
    private AtomicInteger sequence;
    
    private SelectRenderer(Builder builder) {
        selectModel = Objects.requireNonNull(builder.selectModel);
        renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
        sequence = builder.sequence().orElseGet(() -> new AtomicInteger(1));
    }
    
    public SelectStatementProvider render() {
        FragmentCollector fragmentCollector = selectModel
                .mapQueryExpressions(this::renderQueryExpression)
                .collect(FragmentCollector.collect());
        fragmentCollector.add(renderOrderBy());
        fragmentCollector.add(renderPagingModel());
        
        String selectStatement = fragmentCollector.fragments().collect(Collectors.joining(" ")); //$NON-NLS-1$
        
        return DefaultSelectStatementProvider.withSelectStatement(selectStatement)
                .withParameters(fragmentCollector.parameters())
                .build();
    }

    private FragmentAndParameters renderQueryExpression(QueryExpressionModel queryExpressionModel) {
        return QueryExpressionRenderer.withQueryExpression(queryExpressionModel)
                .withRenderingStrategy(renderingStrategy)
                .withSequence(sequence)
                .build()
                .render();
    }

    private Optional<FragmentAndParameters> renderOrderBy() {
        return selectModel.orderByModel()
                .map(this::renderOrderBy);
    }
    
    private FragmentAndParameters renderOrderBy(OrderByModel orderByModel) {
        String phrase = orderByModel.mapColumns(this::calculateOrderByPhrase)
                .collect(CustomCollectors.joining(", ", "order by ", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return FragmentAndParameters.withFragment(phrase).build();
    }
    
    private String calculateOrderByPhrase(SortSpecification column) {
        String phrase = column.aliasOrName();
        if (column.isDescending()) {
            phrase = phrase + " DESC"; //$NON-NLS-1$
        }
        return phrase;
    }
    
    private Optional<FragmentAndParameters> renderPagingModel() {
        return selectModel.pagingModel().flatMap(this::renderPagingModel);
    }
    
    private Optional<FragmentAndParameters> renderPagingModel(PagingModel pagingModel) {
        return pagingModel.accept(new PagingModelRenderer(renderingStrategy));
    }

    public static Builder withSelectModel(SelectModel selectModel) {
        return new Builder().withSelectModel(selectModel);
    }
    
    public static class Builder {
        private SelectModel selectModel;
        private RenderingStrategy renderingStrategy;
        private AtomicInteger sequence;
        
        public Builder withSelectModel(SelectModel selectModel) {
            this.selectModel = selectModel;
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
        
        private Optional<AtomicInteger> sequence() {
            return Optional.ofNullable(sequence);
        }
        
        public SelectRenderer build() {
            return new SelectRenderer(this);
        }
    }
}
