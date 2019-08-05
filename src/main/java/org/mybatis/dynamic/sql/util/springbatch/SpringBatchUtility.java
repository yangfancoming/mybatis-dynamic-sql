
package org.mybatis.dynamic.sql.util.springbatch;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class SpringBatchUtility {
    private SpringBatchUtility() {}
    
    public static final String PARAMETER_KEY = "mybatis3_dsql_query"; //$NON-NLS-1$
    
    public static final RenderingStrategy SPRING_BATCH_READER_RENDERING_STRATEGY =
            new SpringBatchReaderRenderingStrategy();
    
    public static Map<String, Object> toParameterValues(SelectStatementProvider selectStatement) {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put(PARAMETER_KEY, selectStatement);
        return parameterValues;
    }

    /**
     * Select builder that renders in a manner appropriate for the MyBatisPagingItemReader.
     * 
     * <b>Important</b> rendered SQL will contain LIMIT and OFFSET clauses in the SELECT statement. If your database
     * (Oracle) does not support LIMIT and OFFSET, the queries will fail.
     * 
     * @param selectList a column list for the SELECT statement
     * @return FromGatherer used to continue a SELECT statement
     */
    public static QueryExpressionDSL.FromGatherer<SpringBatchPagingReaderSelectModel> selectForPaging(
            BasicColumn...selectList) {
        return SelectDSL.select(SpringBatchPagingReaderSelectModel::new, selectList);
    }

    /**
     * Select builder that renders in a manner appropriate for the MyBatisCursorItemReader.
     * 
     * @param selectList a column list for the SELECT statement
     * @return FromGatherer used to continue a SELECT statement
     */
    public static QueryExpressionDSL.FromGatherer<SpringBatchCursorReaderSelectModel> selectForCursor(
            BasicColumn...selectList) {
        return SelectDSL.select(SpringBatchCursorReaderSelectModel::new, selectList);
    }
}
