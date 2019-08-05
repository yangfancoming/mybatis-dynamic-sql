
package org.mybatis.dynamic.sql.select;

public interface PagingModel {
    <R> R accept(PagingModelVisitor<R> visitor);
}
