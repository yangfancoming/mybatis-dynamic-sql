
package org.mybatis.dynamic.sql.insert.render;

import java.util.function.Function;

import org.mybatis.dynamic.sql.util.InsertMapping;

public class MultiRowRenderingUtilities {
    
    private MultiRowRenderingUtilities() {}
    
    public static Function<InsertMapping, FieldAndValue> toFieldAndValue(ValuePhraseVisitor visitor) {
        return insertMapping -> MultiRowRenderingUtilities.toFieldAndValue(visitor, insertMapping);
    }
    
    public static FieldAndValue toFieldAndValue(ValuePhraseVisitor visitor, InsertMapping insertMapping) {
        return insertMapping.accept(visitor);
    }
}
