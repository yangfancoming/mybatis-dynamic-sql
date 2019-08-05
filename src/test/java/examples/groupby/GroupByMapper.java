
package examples.groupby;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

public interface GroupByMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    List<Map<String, Object>> generalSelect(SelectStatementProvider selectStatement);
}
