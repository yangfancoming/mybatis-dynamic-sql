
package org.mybatis.dynamic.sql.util;

import java.util.function.Function;

import org.mybatis.dynamic.sql.SqlColumn;

public interface InsertMapping {
    <R> R mapColumn(Function<SqlColumn<?>, R> mapper);

    <R> R accept(InsertMappingVisitor<R> visitor);
}
