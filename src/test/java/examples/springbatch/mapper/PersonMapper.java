
package examples.springbatch.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.springbatch.SpringBatchProviderAdapter;

import examples.springbatch.common.Person;

@Mapper
public interface PersonMapper {

    @SelectProvider(type=SpringBatchProviderAdapter.class, method="select")
    @Results({
        @Result(column="id", property="id", id=true),
        @Result(column="first_name", property="firstName"),
        @Result(column="last_name", property="lastName")
    })
    List<Person> selectMany(Map<String, Object> parameterValues);

    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);
}
