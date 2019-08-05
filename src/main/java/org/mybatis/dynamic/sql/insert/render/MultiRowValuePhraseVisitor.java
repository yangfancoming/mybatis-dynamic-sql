
package org.mybatis.dynamic.sql.insert.render;

import java.util.function.Function;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.util.PropertyMapping;

public class MultiRowValuePhraseVisitor extends ValuePhraseVisitor {

    public MultiRowValuePhraseVisitor(RenderingStrategy renderingStrategy) {
        super(renderingStrategy);
    }

    @Override
    public FieldAndValue visit(PropertyMapping mapping) {
        return FieldAndValue.withFieldName(mapping.mapColumn(SqlColumn::name))
                .withValuePhrase(mapping.mapColumn(toMultiRowJdbcPlaceholder(mapping.property())))
                .build();
    }

    private Function<SqlColumn<?>, String> toMultiRowJdbcPlaceholder(String parameterName) {
        return column -> renderingStrategy.getFormattedJdbcPlaceholder(column, "records[%s]", //$NON-NLS-1$
                parameterName);
    }
}
