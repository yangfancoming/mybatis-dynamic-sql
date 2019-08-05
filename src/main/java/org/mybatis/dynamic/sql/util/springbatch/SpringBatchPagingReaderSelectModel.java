
package org.mybatis.dynamic.sql.util.springbatch;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class SpringBatchPagingReaderSelectModel {
    
    private SelectModel selectModel;
    
    public SpringBatchPagingReaderSelectModel(SelectModel selectModel) {
        this.selectModel = selectModel;
    }

    public SelectStatementProvider render() {
        SelectStatementProvider selectStatement =
                selectModel.render(SpringBatchUtility.SPRING_BATCH_READER_RENDERING_STRATEGY);
        return new LimitAndOffsetDecorator(selectStatement);
    }

    public static class LimitAndOffsetDecorator implements SelectStatementProvider {
        private Map<String, Object> parameters = new HashMap<>();
        private String selectStatement;
        
        public LimitAndOffsetDecorator(SelectStatementProvider delegate) {
            parameters.putAll(delegate.getParameters());
            
            selectStatement = delegate.getSelectStatement()
                    + " LIMIT #{_pagesize} OFFSET #{_skiprows}"; //$NON-NLS-1$
        }

        @Override
        public Map<String, Object> getParameters() {
            return parameters;
        }

        @Override
        public String getSelectStatement() {
            return selectStatement;
        }
    }
}
