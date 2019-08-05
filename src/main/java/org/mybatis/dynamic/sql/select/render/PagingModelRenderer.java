
package org.mybatis.dynamic.sql.select.render;

import java.util.Optional;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.FetchFirstPagingModel;
import org.mybatis.dynamic.sql.select.LimitAndOffsetPagingModel;
import org.mybatis.dynamic.sql.select.PagingModelVisitor;
import org.mybatis.dynamic.sql.util.FragmentAndParameters;

public class PagingModelRenderer implements PagingModelVisitor<Optional<FragmentAndParameters>> {
    private RenderingStrategy renderingStrategy;

    public PagingModelRenderer(RenderingStrategy renderingStrategy) {
        this.renderingStrategy = renderingStrategy;
    }
    
    @Override
    public Optional<FragmentAndParameters> visit(LimitAndOffsetPagingModel pagingModel) {
        return new LimitAndOffsetPagingModelRenderer(renderingStrategy, pagingModel).render();
    }

    @Override
    public Optional<FragmentAndParameters> visit(FetchFirstPagingModel pagingModel) {
        return new FetchFirstPagingModelRenderer(renderingStrategy, pagingModel).render();
    }
}
