
package org.mybatis.dynamic.sql.render;

import java.util.Optional;

import org.mybatis.dynamic.sql.BindableColumn;

public class MyBatis3RenderingStrategy extends RenderingStrategy {
    @Override
    public String getFormattedJdbcPlaceholder(Optional<BindableColumn<?>> column, String prefix, String parameterName) {
        return "#{" //$NON-NLS-1$
                + prefix
                + "." //$NON-NLS-1$
                + parameterName
                + renderJdbcType(column)
                + renderTypeHandler(column)
                + "}"; //$NON-NLS-1$
    }
    
    private String renderTypeHandler(Optional<BindableColumn<?>> column) {
        return column
                .flatMap(BindableColumn::typeHandler)
                .map(th -> ",typeHandler=" + th) //$NON-NLS-1$
                .orElse(""); //$NON-NLS-1$
    }
    
    private String renderJdbcType(Optional<BindableColumn<?>> column) {
        return column
                .flatMap(BindableColumn::jdbcType)
                .map(jt -> ",jdbcType=" + jt.getName()) //$NON-NLS-1$
                .orElse(""); //$NON-NLS-1$
    }
}
