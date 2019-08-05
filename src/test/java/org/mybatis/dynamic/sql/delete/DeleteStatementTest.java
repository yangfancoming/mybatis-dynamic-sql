
package org.mybatis.dynamic.sql.delete;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.sql.JDBCType;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;

public class DeleteStatementTest {
    private static final SqlTable foo = SqlTable.of("foo");
    private static final SqlColumn<Integer> id = foo.column("id", JDBCType.INTEGER);
    private static final SqlColumn<String> firstName = foo.column("first_name", JDBCType.VARCHAR);

    @Test
    public void testFullStatement() {
        DeleteStatementProvider deleteStatement = deleteFrom(foo)
                .where(id, isEqualTo(3), and(firstName, isEqualTo("Betty")))
                .or(firstName, isLikeCaseInsensitive("%Fr%"))
                .build()
                .render(RenderingStrategy.MYBATIS3);

        String expectedFullStatement = "delete from foo where (id = #{parameters.p1,jdbcType=INTEGER} and first_name = #{parameters.p2,jdbcType=VARCHAR}) or upper(first_name) like #{parameters.p3,jdbcType=VARCHAR}";

        assertAll(
                () -> assertThat(deleteStatement.getDeleteStatement()).isEqualTo(expectedFullStatement),
                () -> assertThat(deleteStatement.getParameters().size()).isEqualTo(3),
                () -> assertThat(deleteStatement.getParameters().get("p1")).isEqualTo(3),
                () -> assertThat(deleteStatement.getParameters().get("p2")).isEqualTo("Betty"),
                () -> assertThat(deleteStatement.getParameters().get("p3")).isEqualTo("%FR%")
        );
    }

    @Test
    public void testFullStatementWithoutWhere() {
        DeleteStatementProvider deleteStatement = deleteFrom(foo)
                .build()
                .render(RenderingStrategy.MYBATIS3);

        String expectedFullStatement = "delete from foo";

        assertAll(
                () -> assertThat(deleteStatement.getDeleteStatement()).isEqualTo(expectedFullStatement),
                () -> assertThat(deleteStatement.getParameters().size()).isEqualTo(0)
        );
    }
}
