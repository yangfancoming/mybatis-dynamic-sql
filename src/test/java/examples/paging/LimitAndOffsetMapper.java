
package examples.paging;

import static examples.animal.data.AnimalDataDynamicSqlSupport.*;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import examples.animal.data.AnimalData;

public interface LimitAndOffsetMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="AnimalDataResult", value={
        @Result(column="id", property="id", id=true),
        @Result(column="animal_name", property="animalName"),
        @Result(column="brain_weight", property="brainWeight"),
        @Result(column="body_weight", property="bodyWeight")
    })
    List<AnimalData> selectMany(SelectStatementProvider selectStatement);

    default QueryExpressionDSL<LimitAndOffsetAdapter<List<AnimalData>>> selectByExampleWithLimitAndOffset(int limit, int offset) {
        return SelectDSL.select(selectModel -> LimitAndOffsetAdapter.of(selectModel, this::selectMany, limit, offset),
                id, animalName, brainWeight, bodyWeight)
                .from(animalData);
    }
}
