
package examples.generated.always.mybatis;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

import examples.generated.always.GeneratedAlwaysRecord;

public final class GeneratedAlwaysDynamicSqlSupport {
    public static final GeneratedAlways generatedAlways = new GeneratedAlways();
    public static final SqlColumn<Integer> id = generatedAlways.id;
    public static final SqlColumn<String> firstName = generatedAlways.firstName;
    public static final SqlColumn<String> lastName = generatedAlways.lastName;
    public static final SqlColumn<Integer> age = generatedAlways.age;
    public static final SqlColumn<String> fullName = generatedAlways.fullName;
    
    public static final class GeneratedAlways extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);
        public final SqlColumn<String> firstName = column("first_name", JDBCType.VARCHAR);
        public final SqlColumn<String> lastName = column("last_name", JDBCType.VARCHAR);
        public final SqlColumn<Integer> age = column("age", JDBCType.VARCHAR);
        public final SqlColumn<String> fullName = column("full_name", JDBCType.VARCHAR);

        public GeneratedAlways() {
            super("GeneratedAlways");
        }
    }
    
    public static InsertStatementProvider<GeneratedAlwaysRecord> buildInsert(GeneratedAlwaysRecord record) {
        return insert(record)
                .into(generatedAlways)
                .map(id).toProperty("id")
                .map(firstName).toProperty("firstName")
                .map(lastName).toProperty("lastName")
                .build()
                .render(RenderingStrategy.MYBATIS3);
    }

    public static InsertStatementProvider<GeneratedAlwaysRecord> buildInsertSelectiveStatement(GeneratedAlwaysRecord record) {
        return insert(record)
                .into(generatedAlways)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(firstName).toPropertyWhenPresent("firstName", record::getFirstName)
                .map(lastName).toPropertyWhenPresent("lastName", record::getLastName)
                .build()
                .render(RenderingStrategy.MYBATIS3);
    }
    
    public static UpdateStatementProvider buildUpdateByPrimaryKeyStatement(GeneratedAlwaysRecord record) {
        return update(generatedAlways)
                .set(firstName).equalTo(record.getFirstName())
                .set(lastName).equalTo(record.getLastName())
                .where(id, isEqualTo(record.getId()))
                .build()
                .render(RenderingStrategy.MYBATIS3);
    }

    public static UpdateStatementProvider buildUpdateByPrimaryKeySelectiveStatement(GeneratedAlwaysRecord record) {
        return update(generatedAlways)
                .set(firstName).equalToWhenPresent(record.getFirstName()) // for test coverage
                .set(lastName).equalToWhenPresent(record::getLastName)
                .where(id, isEqualTo(record::getId))
                .build()
                .render(RenderingStrategy.MYBATIS3);
    }

    public static UpdateDSL<UpdateModel> updateByExample(GeneratedAlwaysRecord record) {
        return update(generatedAlways)
                .set(id).equalTo(record::getId)
                .set(firstName).equalTo(record::getFirstName)
                .set(lastName).equalTo(record::getLastName);
    }

    public static UpdateDSL<UpdateModel> updateByExampleSelective(GeneratedAlwaysRecord record) {
        return update(generatedAlways)
                .set(id).equalToWhenPresent(record::getId)
                .set(firstName).equalToWhenPresent(record::getFirstName)
                .set(lastName).equalToWhenPresent(record::getLastName);
    }
    
    public static QueryExpressionDSL<SelectModel> selectByExample() {
        return select(id.as("A_ID"), firstName, lastName, fullName)
                .from(generatedAlways, "a");
    }
    
    public static SelectStatementProvider selectByPrimaryKey(int id_) {
        return select(id.as("A_ID"), firstName, lastName, fullName)
                .from(generatedAlways)
                .where(id, isEqualTo(id_))
                .build()
                .render(RenderingStrategy.MYBATIS3);
    }
}
