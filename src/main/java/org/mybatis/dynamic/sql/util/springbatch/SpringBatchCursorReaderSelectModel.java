
package org.mybatis.dynamic.sql.util.springbatch;

import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class SpringBatchCursorReaderSelectModel {
    
    private SelectModel selectModel;
    
    public SpringBatchCursorReaderSelectModel(SelectModel selectModel) {
        this.selectModel = selectModel;
    }

    public SelectStatementProvider render() {
        return selectModel.render(SpringBatchUtility.SPRING_BATCH_READER_RENDERING_STRATEGY);
    }
}
