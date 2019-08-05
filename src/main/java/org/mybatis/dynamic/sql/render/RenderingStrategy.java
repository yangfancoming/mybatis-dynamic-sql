
package org.mybatis.dynamic.sql.render;

import java.util.Optional;

import org.mybatis.dynamic.sql.BindableColumn;

public abstract class RenderingStrategy {
    @SuppressWarnings("squid:S2390")
    public static final RenderingStrategy MYBATIS3 = new MyBatis3RenderingStrategy();
    @SuppressWarnings("squid:S2390")
    public static final RenderingStrategy SPRING_NAMED_PARAMETER = new SpringNamedParameterRenderingStrategy();
    public static final String DEFAULT_PARAMETER_PREFIX = "parameters"; //$NON-NLS-1$
    
    public String getFormattedJdbcPlaceholder(BindableColumn<?> column, String prefix, String parameterName) {
        return getFormattedJdbcPlaceholder(Optional.of(column), prefix, parameterName);
    }

    public String getFormattedJdbcPlaceholder(String prefix, String parameterName) {
        return getFormattedJdbcPlaceholder(Optional.empty(), prefix, parameterName);
    }

    public abstract String getFormattedJdbcPlaceholder(Optional<BindableColumn<?>> column, String prefix,
            String parameterName);
}
