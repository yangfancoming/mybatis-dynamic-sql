
package examples.generated.always.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import examples.generated.always.GeneratedAlwaysRecord;

public interface GeneratedAlwaysAnnotatedMapper {
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="gaResults", value={
        @Result(property="id", column="id", id=true),
        @Result(property="firstName", column="first_name"),
        @Result(property="lastName", column="last_name"),
        @Result(property="fullName", column="full_name")
    })
    List<GeneratedAlwaysRecord> selectMany(SelectStatementProvider selectStatement);
    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("gaResults")
    GeneratedAlwaysRecord selectByPrimaryKey(SelectStatementProvider selectStatement);
    
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @Options(useGeneratedKeys=true, keyProperty="record.fullName")
    int insert(InsertStatementProvider<GeneratedAlwaysRecord> insertStatement);
    
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);
    
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<GeneratedAlwaysRecord> multiInsert);

    // This is kludgy. Currently MyBatis does not support nested lists in parameter objects
    // when returning generated keys.
    // So we need to do this silliness and decompose the multi row insert into its component parts
    // for the actual MyBatis call
    @Insert({
        "${insertStatement}"
    })
    @Options(useGeneratedKeys=true, keyProperty="records.fullName")
    int insertMultipleWithGeneratedKeys(@Param("insertStatement") String statement, @Param("records") List<GeneratedAlwaysRecord> records);

    default int insertMultipleWithGeneratedKeys(MultiRowInsertStatementProvider<GeneratedAlwaysRecord> multiInsert) {
        return insertMultipleWithGeneratedKeys(multiInsert.getInsertStatement(), multiInsert.getRecords());
    }
}
