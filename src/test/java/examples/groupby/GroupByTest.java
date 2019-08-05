
package examples.groupby;

import static examples.groupby.PersonDynamicSqlSupport.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class GroupByTest {

    private static final String JDBC_URL = "jdbc:hsqldb:mem:aname";
    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
    
    private SqlSessionFactory sqlSessionFactory;
    
    @BeforeEach
    public void setup() throws Exception {
        Class.forName(JDBC_DRIVER);
        InputStream is = getClass().getResourceAsStream("/examples/groupby/CreateGroupByDB.sql");
        try (Connection connection = DriverManager.getConnection(JDBC_URL, "sa", "")) {
            ScriptRunner sr = new ScriptRunner(connection);
            sr.setLogWriter(null);
            sr.runScript(new InputStreamReader(is));
        }
        
        UnpooledDataSource ds = new UnpooledDataSource(JDBC_DRIVER, JDBC_URL, "sa", "");
        Environment environment = new Environment("test", new JdbcTransactionFactory(), ds);
        Configuration config = new Configuration(environment);
        config.addMapper(GroupByMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
    }
    
    @Test
    public void testBasicGroupBy() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(gender, count())
                    .from(person)
                    .groupBy(gender)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select gender, count(*) from Person group by gender";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(2);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("GENDER")).isEqualTo("Male");
            assertThat(row.get("C2")).isEqualTo(4L);

            row = rows.get(1);
            assertThat(row.get("GENDER")).isEqualTo("Female");
            assertThat(row.get("C2")).isEqualTo(3L);
        }
    }

    @Test
    public void testBasicGroupByWithAggregateAlias() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(gender, count().as("count"))
                    .from(person)
                    .groupBy(gender)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select gender, count(*) as count from Person group by gender";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(2);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("GENDER")).isEqualTo("Male");
            assertThat(row.get("COUNT")).isEqualTo(4L);

            row = rows.get(1);
            assertThat(row.get("GENDER")).isEqualTo("Female");
            assertThat(row.get("COUNT")).isEqualTo(3L);
        }
    }

    @Test
    public void testBasicGroupByOrderByWithAggregateAlias() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(gender, count().as("count"))
                    .from(person)
                    .groupBy(gender)
                    .orderBy(gender)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select gender, count(*) as count from Person group by gender order by gender";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(2);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("GENDER")).isEqualTo("Female");
            assertThat(row.get("COUNT")).isEqualTo(3L);

            row = rows.get(1);
            assertThat(row.get("GENDER")).isEqualTo("Male");
            assertThat(row.get("COUNT")).isEqualTo(4L);
        }
    }

    @Test
    public void testBasicGroupByOrderByWithCalculatedColumnAndTableAlias() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(substring(gender, 1, 1).as("ShortGender"), avg(age).as("AverageAge"))
                    .from(person, "a")
                    .groupBy(substring(gender, 1, 1))
                    .orderBy(sortColumn("ShortGender").descending())
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select substring(a.gender, 1, 1) as ShortGender, avg(a.age) as AverageAge from Person a group by substring(a.gender, 1, 1) order by ShortGender DESC";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(2);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("SHORTGENDER")).isEqualTo("M");
            assertThat(row.get("AVERAGEAGE")).isEqualTo(25);

            row = rows.get(1);
            assertThat(row.get("SHORTGENDER")).isEqualTo("F");
            assertThat(row.get("AVERAGEAGE")).isEqualTo(27);
        }
    }

    @Test
    public void testGroupByAfterWhere() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(lastName, count().as("count"))
                    .from(person, "a")
                    .where(gender, isEqualTo("Male"))
                    .groupBy(lastName)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select a.last_name, count(*) as count from Person a where a.gender = #{parameters.p1,jdbcType=VARCHAR} group by a.last_name";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(2);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("LAST_NAME")).isEqualTo("Flintstone");
            assertThat(row.get("COUNT")).isEqualTo(2L);

            row = rows.get(1);
            assertThat(row.get("LAST_NAME")).isEqualTo("Rubble");
            assertThat(row.get("COUNT")).isEqualTo(2L);
        }
    }

    @Test
    public void testLimitAndOffsetAfterGroupBy() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(lastName, count().as("count"))
                    .from(person)
                    .groupBy(lastName)
                    .limit(1)
                    .offset(1)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select last_name, count(*) as count from Person group by last_name limit #{parameters._limit} offset #{parameters._offset}";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(1);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("LAST_NAME")).isEqualTo("Rubble");
            assertThat(row.get("COUNT")).isEqualTo(3L);
        }
    }

    @Test
    public void testLimitOnlyAfterGroupBy() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(lastName, count().as("count"))
                    .from(person)
                    .groupBy(lastName)
                    .limit(1)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select last_name, count(*) as count from Person group by last_name limit #{parameters._limit}";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(1);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("LAST_NAME")).isEqualTo("Flintstone");
            assertThat(row.get("COUNT")).isEqualTo(4L);
        }
    }

    @Test
    public void testOffsetOnlyAfterGroupBy() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(lastName, count().as("count"))
                    .from(person)
                    .groupBy(lastName)
                    .offset(1)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select last_name, count(*) as count from Person group by last_name offset #{parameters._offset} rows";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(1);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("LAST_NAME")).isEqualTo("Rubble");
            assertThat(row.get("COUNT")).isEqualTo(3L);
        }
    }

    @Test
    public void testOffsetAndFetchFirstAfterGroupBy() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(lastName, count().as("count"))
                    .from(person)
                    .groupBy(lastName)
                    .offset(1)
                    .fetchFirst(1).rowsOnly()
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select last_name, count(*) as count from Person group by last_name offset #{parameters._offset} rows fetch first #{parameters._fetchFirstRows} rows only";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(1);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("LAST_NAME")).isEqualTo("Rubble");
            assertThat(row.get("COUNT")).isEqualTo(3L);
        }
    }

    @Test
    public void testFetchFirstOnlyAfterGroupBy() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(lastName, count().as("count"))
                    .from(person)
                    .groupBy(lastName)
                    .fetchFirst(1).rowsOnly()
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select last_name, count(*) as count from Person group by last_name fetch first #{parameters._fetchFirstRows} rows only";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(1);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("LAST_NAME")).isEqualTo("Flintstone");
            assertThat(row.get("COUNT")).isEqualTo(4L);
        }
    }

    @Test
    public void testCountDistinct() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupByMapper mapper = session.getMapper(GroupByMapper.class);
        
            SelectStatementProvider selectStatement = select(countDistinct(lastName).as("count"))
                    .from(person)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            String expected = "select count(distinct last_name) as count from Person";
            assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
            
            List<Map<String, Object>> rows = mapper.generalSelect(selectStatement);
            assertThat(rows.size()).isEqualTo(1);
            Map<String, Object> row = rows.get(0);
            assertThat(row.get("COUNT")).isEqualTo(2L);
        }
    }
}
