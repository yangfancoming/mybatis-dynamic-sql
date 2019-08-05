
package org.mybatis.dynamic.sql.where;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.sql.JDBCType;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.where.render.WhereClauseProvider;

public class WhereModelTest {

    @Test
    public void testThatParameterNameCarriesToSubCriteria() {
        SqlTable table = SqlTable.of("foo");
        SqlColumn<Integer> id = table.column("id", JDBCType.INTEGER);
        
        WhereClauseProvider wc = where(id, isEqualTo(3), or(id, isEqualTo(4)))
                .build()
                .render(RenderingStrategy.MYBATIS3, "myName");

        assertThat(wc.getWhereClause()).isEqualTo("where (id = #{myName.parameters.p1,jdbcType=INTEGER} or id = #{myName.parameters.p2,jdbcType=INTEGER})");
    }
}
