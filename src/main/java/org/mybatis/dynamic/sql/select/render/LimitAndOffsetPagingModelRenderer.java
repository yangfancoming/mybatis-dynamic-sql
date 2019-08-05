
package org.mybatis.dynamic.sql.select.render;

import java.util.Optional;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.LimitAndOffsetPagingModel;
import org.mybatis.dynamic.sql.util.FragmentAndParameters;

public class LimitAndOffsetPagingModelRenderer {
    private static final String LIMIT_PARAMETER = "_limit"; //$NON-NLS-1$
    private static final String OFFSET_PARAMETER = "_offset"; //$NON-NLS-1$
    private RenderingStrategy renderingStrategy;
    private LimitAndOffsetPagingModel pagingModel;

    public LimitAndOffsetPagingModelRenderer(RenderingStrategy renderingStrategy,
            LimitAndOffsetPagingModel pagingModel) {
        this.renderingStrategy = renderingStrategy;
        this.pagingModel = pagingModel;
    }
    
    public Optional<FragmentAndParameters> render() {
        return pagingModel.offset()
                .map(this::renderLimitAndOffset)
                .orElseGet(this::renderLimitOnly);
    }

    private Optional<FragmentAndParameters> renderLimitOnly() {
        return FragmentAndParameters.withFragment("limit " + renderPlaceholder(LIMIT_PARAMETER)) //$NON-NLS-1$
                .withParameter(LIMIT_PARAMETER, pagingModel.limit())
                .buildOptional();
    }
    
    private Optional<FragmentAndParameters> renderLimitAndOffset(Long offset) {
        return FragmentAndParameters.withFragment("limit " + renderPlaceholder(LIMIT_PARAMETER) //$NON-NLS-1$
                    + " offset " + renderPlaceholder(OFFSET_PARAMETER)) //$NON-NLS-1$
                .withParameter(LIMIT_PARAMETER, pagingModel.limit())
                .withParameter(OFFSET_PARAMETER, offset)
                .buildOptional();
    }
    
    private String renderPlaceholder(String parameterName) {
        return renderingStrategy.getFormattedJdbcPlaceholder(RenderingStrategy.DEFAULT_PARAMETER_PREFIX,
                parameterName); 
    }
}
