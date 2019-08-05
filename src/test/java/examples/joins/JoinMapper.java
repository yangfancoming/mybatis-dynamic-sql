
package examples.joins;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

public interface JoinMapper {
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("SimpleJoinResult")
    List<OrderMaster> selectMany(SelectStatementProvider selectStatement);
    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    List<Map<String, Object>> generalSelect(SelectStatementProvider selectStatement);
    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results ({
        @Result(column="user_id", property="userId"),
        @Result(column="user_name", property="userName"),
        @Result(column="parent_id", property="parentId")
    })
    List<User> selectUsers(SelectStatementProvider selectStatement);
}
