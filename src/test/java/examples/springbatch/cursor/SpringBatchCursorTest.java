
package examples.springbatch.cursor;

import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static examples.springbatch.mapper.PersonDynamicSqlSupport.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import examples.springbatch.mapper.PersonMapper;

@SpringBatchTest
@SpringJUnitConfig(classes=CursorReaderBatchConfiguration.class)
public class SpringBatchCursorTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void testThatRowsAreTransformedToUpperCase() throws Exception {
        // starting condition
        assertThat(upperCaseRowCount()).isEqualTo(0);

        JobExecution execution = jobLauncherTestUtils.launchJob();
        assertThat(execution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(numberOfRowsProcessed(execution)).isEqualTo(2);

        // ending condition
        assertThat(upperCaseRowCount()).isEqualTo(2);
    }

    private int numberOfRowsProcessed(JobExecution jobExecution) {
        return jobExecution.getStepExecutions().stream()
                .map(StepExecution::getExecutionContext)
                .mapToInt(this::getRowCount)
                .sum();
    }
    
    private int getRowCount(ExecutionContext executionContext) {
        return executionContext.getInt("row_count", 0);
    }
    
    private long upperCaseRowCount() throws Exception {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

            SelectStatementProvider selectStatement = SelectDSL.select(count())
                    .from(person)
                    .where(lastName, isEqualTo("FLINTSTONE"))
                    .build()
                    .render(RenderingStrategy.MYBATIS3);

            return personMapper.count(selectStatement);
        }
    }
}
