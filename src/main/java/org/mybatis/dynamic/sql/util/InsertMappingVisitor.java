
package org.mybatis.dynamic.sql.util;

public interface InsertMappingVisitor<T> {
    T visit(NullMapping mapping);

    T visit(ConstantMapping mapping);

    T visit(StringConstantMapping mapping);

    T visit(PropertyMapping mapping);
}
