
package org.mybatis.dynamic.sql.util.springbatch;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.render.MyBatis3RenderingStrategy;

/**
 * This rendering strategy should be used for MyBatis3 statements using one of the 
 * Spring batch readers supplied by mybatis-spring integration (http://www.mybatis.org/spring/).
 * Those readers are MyBatisPagingItemReader and MyBatisCursorItemReader.
 *
 */
public class SpringBatchReaderRenderingStrategy extends MyBatis3RenderingStrategy {

    @Override
    public String getFormattedJdbcPlaceholder(BindableColumn<?> column, String prefix, String parameterName) {
        String newPrefix = SpringBatchUtility.PARAMETER_KEY + "." + prefix; //$NON-NLS-1$
        return super.getFormattedJdbcPlaceholder(column, newPrefix, parameterName);
    }
}
