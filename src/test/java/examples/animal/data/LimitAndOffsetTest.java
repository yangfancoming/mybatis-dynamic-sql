
package examples.animal.data;

import static examples.animal.data.AnimalDataDynamicSqlSupport.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

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

public class LimitAndOffsetTest {

    private static final String JDBC_URL = "jdbc:hsqldb:mem:aname";
    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver"; 
    
    private SqlSessionFactory sqlSessionFactory;
    
    @BeforeEach
    public void setup() throws Exception {
        Class.forName(JDBC_DRIVER);
        InputStream is = getClass().getResourceAsStream("/examples/animal/data/CreateAnimalData.sql");
        try (Connection connection = DriverManager.getConnection(JDBC_URL, "sa", "")) {
            ScriptRunner sr = new ScriptRunner(connection);
            sr.setLogWriter(null);
            sr.runScript(new InputStreamReader(is));
        }
        
        UnpooledDataSource ds = new UnpooledDataSource(JDBC_DRIVER, JDBC_URL, "sa", "");
        Environment environment = new Environment("test", new JdbcTransactionFactory(), ds);
        Configuration config = new Configuration(environment);
        config.addMapper(AnimalDataMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
    }

    @Test
    public void testLimitAndOffsetAfterFrom() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SelectStatementProvider selectStatement = select(animalData.allColumns())
                    .from(animalData)
                    .limit(3)
                    .offset(22)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            AnimalDataMapper mapper = sqlSession.getMapper(AnimalDataMapper.class);
            List<AnimalData> records = mapper.selectMany(selectStatement);
        
            assertAll(
                    () -> assertThat(records.size()).isEqualTo(3),
                    () -> assertThat(records.get(0).getId()).isEqualTo(23),
                    () -> assertThat(selectStatement.getSelectStatement()).isEqualTo("select * from AnimalData limit #{parameters._limit} offset #{parameters._offset}"),
                    () -> assertThat(selectStatement.getParameters().get("_limit")).isEqualTo(3L),
                    () -> assertThat(selectStatement.getParameters().get("_offset")).isEqualTo(22L)
            );
        }
    }

    @Test
    public void testLimitOnlyAfterFrom() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SelectStatementProvider selectStatement = select(animalData.allColumns())
                    .from(animalData)
                    .limit(3)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            AnimalDataMapper mapper = sqlSession.getMapper(AnimalDataMapper.class);
            List<AnimalData> records = mapper.selectMany(selectStatement);
        
            assertAll(
                    () -> assertThat(records.size()).isEqualTo(3),
                    () -> assertThat(records.get(0).getId()).isEqualTo(1),
                    () -> assertThat(selectStatement.getSelectStatement()).isEqualTo("select * from AnimalData limit #{parameters._limit}"),
                    () -> assertThat(selectStatement.getParameters().get("_limit")).isEqualTo(3L)
            );
        }
    }

    @Test
    public void testOffsetOnlyAfterFrom() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SelectStatementProvider selectStatement = select(animalData.allColumns())
                    .from(animalData)
                    .offset(22)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            AnimalDataMapper mapper = sqlSession.getMapper(AnimalDataMapper.class);
            List<AnimalData> records = mapper.selectMany(selectStatement);
        
            assertAll(
                    () -> assertThat(records.size()).isEqualTo(43),
                    () -> assertThat(records.get(0).getId()).isEqualTo(23),
                    () -> assertThat(selectStatement.getSelectStatement()).isEqualTo("select * from AnimalData offset #{parameters._offset} rows"),
                    () -> assertThat(selectStatement.getParameters().get("_offset")).isEqualTo(22L)
            );
        }
    }

    @Test
    public void testLimitAndOffsetAfterWhere() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SelectStatementProvider selectStatement = select(animalData.allColumns())
                    .from(animalData)
                    .where(id, isLessThan(50))
                    .and(id, isGreaterThan(22))
                    .limit(3)
                    .offset(22)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            AnimalDataMapper mapper = sqlSession.getMapper(AnimalDataMapper.class);
            List<AnimalData> records = mapper.selectMany(selectStatement);
        
            assertAll(
                    () -> assertThat(records.size()).isEqualTo(3),
                    () -> assertThat(records.get(0).getId()).isEqualTo(45),
                    () -> assertThat(selectStatement.getSelectStatement()).isEqualTo("select * from AnimalData where id < #{parameters.p1,jdbcType=INTEGER} and id > #{parameters.p2,jdbcType=INTEGER} limit #{parameters._limit} offset #{parameters._offset}"),
                    () -> assertThat(selectStatement.getParameters().get("_limit")).isEqualTo(3L),
                    () -> assertThat(selectStatement.getParameters().get("_offset")).isEqualTo(22L)
            );
        }
    }

    @Test
    public void testLimitOnlyAfterWhere() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SelectStatementProvider selectStatement = select(animalData.allColumns())
                    .from(animalData)
                    .where(id, isLessThan(50))
                    .limit(3)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            AnimalDataMapper mapper = sqlSession.getMapper(AnimalDataMapper.class);
            List<AnimalData> records = mapper.selectMany(selectStatement);
        
            assertAll(
                    () -> assertThat(records.size()).isEqualTo(3),
                    () -> assertThat(records.get(0).getId()).isEqualTo(1),
                    () -> assertThat(selectStatement.getSelectStatement()).isEqualTo("select * from AnimalData where id < #{parameters.p1,jdbcType=INTEGER} limit #{parameters._limit}"),
                    () -> assertThat(selectStatement.getParameters().get("_limit")).isEqualTo(3L)
            );
        }
    }

    @Test
    public void testOffsetOnlyAfterWhere() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SelectStatementProvider selectStatement = select(animalData.allColumns())
                    .from(animalData)
                    .where(id, isLessThan(50))
                    .offset(22)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            AnimalDataMapper mapper = sqlSession.getMapper(AnimalDataMapper.class);
            List<AnimalData> records = mapper.selectMany(selectStatement);
        
            assertAll(
                    () -> assertThat(records.size()).isEqualTo(27),
                    () -> assertThat(records.get(0).getId()).isEqualTo(23),
                    () -> assertThat(selectStatement.getSelectStatement()).isEqualTo("select * from AnimalData where id < #{parameters.p1,jdbcType=INTEGER} offset #{parameters._offset} rows"),
                    () -> assertThat(selectStatement.getParameters().get("_offset")).isEqualTo(22L)
            );
        }
    }

    @Test
    public void testLimitAndOffsetAfterOrderBy() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SelectStatementProvider selectStatement = select(animalData.allColumns())
                    .from(animalData)
                    .orderBy(id)
                    .limit(3)
                    .offset(22)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            AnimalDataMapper mapper = sqlSession.getMapper(AnimalDataMapper.class);
            List<AnimalData> records = mapper.selectMany(selectStatement);
        
            assertAll(
                    () -> assertThat(records.size()).isEqualTo(3),
                    () -> assertThat(records.get(0).getId()).isEqualTo(23),
                    () -> assertThat(selectStatement.getSelectStatement()).isEqualTo("select * from AnimalData order by id limit #{parameters._limit} offset #{parameters._offset}"),
                    () -> assertThat(selectStatement.getParameters().get("_limit")).isEqualTo(3L),
                    () -> assertThat(selectStatement.getParameters().get("_offset")).isEqualTo(22L)
            );
        }
    }

    @Test
    public void testLimitOnlyAfterOrderBy() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SelectStatementProvider selectStatement = select(animalData.allColumns())
                    .from(animalData)
                    .orderBy(id)
                    .limit(3)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
        
            AnimalDataMapper mapper = sqlSession.getMapper(AnimalDataMapper.class);
            List<AnimalData> records = mapper.selectMany(selectStatement);
        
            assertAll(
                    () -> assertThat(records.size()).isEqualTo(3),
                    () -> assertThat(records.get(0).getId()).isEqualTo(1),
                    () -> assertThat(selectStatement.getSelectStatement()).isEqualTo("select * from AnimalData order by id limit #{parameters._limit}"),
                    () -> assertThat(selectStatement.getParameters().get("_limit")).isEqualTo(3L)
            );
        }
    }

    @Test
    public void testOffsetOnlyAfterOrderBy() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SelectStatementProvider selectStatement = select(animalData.allColumns())
                    .from(animalData)
                    .orderBy(id)
                    .offset(22)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
        
            AnimalDataMapper mapper = sqlSession.getMapper(AnimalDataMapper.class);
            List<AnimalData> records = mapper.selectMany(selectStatement);
        
            assertAll(
                    () -> assertThat(records.size()).isEqualTo(43),
                    () -> assertThat(records.get(0).getId()).isEqualTo(23),
                    () -> assertThat(selectStatement.getSelectStatement()).isEqualTo("select * from AnimalData order by id offset #{parameters._offset} rows"),
                    () -> assertThat(selectStatement.getParameters().get("_offset")).isEqualTo(22L)
            );
        }
    }
}
