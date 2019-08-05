
package org.mybatis.dynamic.sql.select;

public interface PagingModelVisitor<R> {
    R visit(LimitAndOffsetPagingModel pagingModel);
    
    R visit(FetchFirstPagingModel pagingModel);
}
