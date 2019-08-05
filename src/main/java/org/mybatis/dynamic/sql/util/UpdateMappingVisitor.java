
package org.mybatis.dynamic.sql.util;

public interface UpdateMappingVisitor<T> {
    T visit(NullMapping mapping);

    T visit(ConstantMapping mapping);

    T visit(StringConstantMapping mapping);

    <S> T visit(ValueMapping<S> mapping);
    
    T visit(SelectMapping mapping);

    T visit(ColumnMapping columnMapping);
}
