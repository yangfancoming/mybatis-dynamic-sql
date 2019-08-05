
package examples.simple;

import static examples.simple.SimpleTableDynamicSqlSupport.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class SampleWhereClausesTest {

    @Test
    public void simpleClause1() {
        SelectStatementProvider selectStatement = select(count())
                .from(simpleTable)
                .where(id, isEqualTo(3))
                .build()
                .render(RenderingStrategy.MYBATIS3);
        
        assertThat(selectStatement.getSelectStatement())
                .isEqualTo("select count(*) from SimpleTable where id = #{parameters.p1,jdbcType=INTEGER}");
    }
    
    @Test
    public void simpleClause2() {
        SelectStatementProvider selectStatement = select(count())
                .from(simpleTable, "a")
                .where(id, isNull())
                .build()
                .render(RenderingStrategy.MYBATIS3);
        
        assertThat(selectStatement.getSelectStatement()).isEqualTo("select count(*) from SimpleTable a where a.id is null");
    }
    
    @Test
    public void betweenClause() {
        SelectStatementProvider selectStatement = select(count())
                .from(simpleTable, "a")
                .where(id, isBetween(1).and(4))
                .build()
                .render(RenderingStrategy.MYBATIS3);
        
        assertThat(selectStatement.getSelectStatement())
            .isEqualTo("select count(*) from SimpleTable a where a.id between #{parameters.p1,jdbcType=INTEGER} and #{parameters.p2,jdbcType=INTEGER}");
    }

    @Test
    public void complexClause() {
        SelectStatementProvider selectStatement = select(count())
                .from(simpleTable, "a")
                .where(id, isGreaterThan(2))
                .or(occupation, isNull(), and(id, isLessThan(6)))
                .build()
                .render(RenderingStrategy.MYBATIS3);
        
        assertThat(selectStatement.getSelectStatement())
            .isEqualTo("select count(*) from SimpleTable a where a.id > #{parameters.p1,jdbcType=INTEGER} or (a.occupation is null and a.id < #{parameters.p2,jdbcType=INTEGER})");
    }
}
