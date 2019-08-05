
package org.mybatis.dynamic.sql.mybatis3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.update;

import java.sql.JDBCType;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

public class UpdateStatementTest {
    private static final SqlTable foo = SqlTable.of("foo");
    private static final SqlColumn<Integer> id = foo.column("id", JDBCType.INTEGER);
    private static final SqlColumn<String> firstName = foo.column("firstName", JDBCType.VARCHAR);
    private static final SqlColumn<String> lastName = foo.column("lastName", JDBCType.VARCHAR);
    private static final SqlColumn<String> occupation = foo.column("occupation", JDBCType.VARCHAR);

    @Test
    public void testUpdateParameter() {
        UpdateStatementProvider updateStatement = update(foo)
                .set(firstName).equalTo("fred")
                .set(lastName).equalTo("jones")
                .set(occupation).equalToNull()
                .where(id, isEqualTo(3))
                .build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expected = "update foo set firstName = #{parameters.p1,jdbcType=VARCHAR}, "
                + "lastName = #{parameters.p2,jdbcType=VARCHAR}, "
                + "occupation = null "
                + "where id = #{parameters.p3,jdbcType=INTEGER}";
                
        assertThat(updateStatement.getUpdateStatement()).isEqualTo(expected);
        assertThat(updateStatement.getParameters().size()).isEqualTo(3);
        assertThat(updateStatement.getParameters().get("p1")).isEqualTo("fred");
        assertThat(updateStatement.getParameters().get("p2")).isEqualTo("jones");
        assertThat(updateStatement.getParameters().get("p3")).isEqualTo(3);
    }

    @Test
    public void testUpdateParameterStartWithNull() {
        UpdateStatementProvider updateStatement = update(foo)
                .set(occupation).equalToNull()
                .set(firstName).equalTo("fred")
                .set(lastName).equalTo("jones")
                .where(id, isEqualTo(3))
                .and(firstName, isEqualTo("barney"))
                .build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expectedSetClause = "update foo set occupation = null, "
                + "firstName = #{parameters.p1,jdbcType=VARCHAR}, "
                + "lastName = #{parameters.p2,jdbcType=VARCHAR} "
                + "where id = #{parameters.p3,jdbcType=INTEGER} "
                + "and firstName = #{parameters.p4,jdbcType=VARCHAR}";
                
        assertThat(updateStatement.getUpdateStatement()).isEqualTo(expectedSetClause);
        assertThat(updateStatement.getParameters().size()).isEqualTo(4);
        assertThat(updateStatement.getParameters().get("p1")).isEqualTo("fred");
        assertThat(updateStatement.getParameters().get("p2")).isEqualTo("jones");
        assertThat(updateStatement.getParameters().get("p3")).isEqualTo(3);
        assertThat(updateStatement.getParameters().get("p4")).isEqualTo("barney");
    }
}
