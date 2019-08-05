
package org.mybatis.dynamic.sql.update.render;

import static org.mybatis.dynamic.sql.util.StringUtilities.spaceBefore;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.FragmentAndParameters;
import org.mybatis.dynamic.sql.util.FragmentCollector;
import org.mybatis.dynamic.sql.util.UpdateMapping;
import org.mybatis.dynamic.sql.where.WhereModel;
import org.mybatis.dynamic.sql.where.render.WhereClauseProvider;
import org.mybatis.dynamic.sql.where.render.WhereRenderer;

public class UpdateRenderer {
    private UpdateModel updateModel;
    private RenderingStrategy renderingStrategy;
    private AtomicInteger sequence = new AtomicInteger(1);
    
    private UpdateRenderer(Builder builder) {
        updateModel = Objects.requireNonNull(builder.updateModel);
        renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
    }
    
    public UpdateStatementProvider render() {
        FragmentCollector fc = calculateColumnMappings();
        
        Optional<WhereClauseProvider> whereClause = updateModel.whereModel().flatMap(this::renderWhereClause);
        
        return DefaultUpdateStatementProvider.withUpdateStatement(calculateUpdateStatement(fc, whereClause))
                .withParameters(fc.parameters())
                .withParameters(calculateWhereParameters(whereClause))
                .build();
    }
    
    private FragmentCollector calculateColumnMappings() {
        SetPhraseVisitor visitor = new SetPhraseVisitor(sequence, renderingStrategy);

        return updateModel.mapColumnMappings(toFragmentAndParameters(visitor))
                .collect(FragmentCollector.collect());
    }
    
    private String calculateUpdateStatement(FragmentCollector fc, Optional<WhereClauseProvider> whereClause) {
        return "update" //$NON-NLS-1$
                + spaceBefore(updateModel.table().tableNameAtRuntime())
                + spaceBefore(calculateSetPhrase(fc))
                + spaceBefore(whereClause.map(WhereClauseProvider::getWhereClause));
    }
    
    private Map<String, Object> calculateWhereParameters(Optional<WhereClauseProvider> whereClause) {
        return whereClause
                .map(WhereClauseProvider::getParameters)
                .orElse(Collections.emptyMap());
    }
    
    private String calculateSetPhrase(FragmentCollector collector) {
        return collector.fragments()
                .collect(Collectors.joining(", ", "set ", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
    
    private Optional<WhereClauseProvider> renderWhereClause(WhereModel whereModel) {
        return WhereRenderer.withWhereModel(whereModel)
                .withRenderingStrategy(renderingStrategy)
                .withSequence(sequence)
                .withTableAliasCalculator(TableAliasCalculator.empty())
                .build()
                .render();
    }

    private Function<UpdateMapping, FragmentAndParameters> toFragmentAndParameters(SetPhraseVisitor visitor) {
        return updateMapping -> toFragmentAndParameters(visitor, updateMapping);
    }
    
    private FragmentAndParameters toFragmentAndParameters(SetPhraseVisitor visitor, UpdateMapping updateMapping) {
        return updateMapping.accept(visitor);
    }
    
    public static Builder withUpdateModel(UpdateModel updateModel) {
        return new Builder().withUpdateModel(updateModel);
    }
    
    public static class Builder {
        private UpdateModel updateModel;
        private RenderingStrategy renderingStrategy;

        public Builder withUpdateModel(UpdateModel updateModel) {
            this.updateModel = updateModel;
            return this;
        }
        
        public Builder withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }
        
        public UpdateRenderer build() {
            return new UpdateRenderer(this);
        }
    }
}
