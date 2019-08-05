
package examples.column.comparison;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

public interface ColumnComparisonMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results({
        @Result(column="number1", property="number1", id=true),
        @Result(column="number2", property="number2", id=true)
    })
    List<ColumnComparisonRecord> selectMany(SelectStatementProvider selectStatement);
}
