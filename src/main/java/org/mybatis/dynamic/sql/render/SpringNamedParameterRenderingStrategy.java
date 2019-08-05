
package org.mybatis.dynamic.sql.render;

import java.util.Optional;

import org.mybatis.dynamic.sql.BindableColumn;

public class SpringNamedParameterRenderingStrategy extends RenderingStrategy {
    
    @Override
    public String getFormattedJdbcPlaceholder(Optional<BindableColumn<?>> column, String prefix, String parameterName) {
        return ":" + parameterName; //$NON-NLS-1$
    }
}
