
package org.mybatis.dynamic.sql.mybatis3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.sql.JDBCType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlCriterion;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.render.TableAliasCalculator;
import org.mybatis.dynamic.sql.util.FragmentAndParameters;
import org.mybatis.dynamic.sql.where.condition.IsEqualTo;
import org.mybatis.dynamic.sql.where.render.CriterionRenderer;

public class CriterionRendererTest {
    @Test
    public void testAliasWithIgnore() {
        SqlTable table = SqlTable.of("foo");
        SqlColumn<Integer> column = table.column("id", JDBCType.INTEGER);
        
        IsEqualTo<Integer> condition = IsEqualTo.of(() -> 3);
        SqlCriterion<Integer> criterion = SqlCriterion.withColumn(column)
                .withCondition(condition)
                .build();
        AtomicInteger sequence = new AtomicInteger(1);
        FragmentAndParameters fp = CriterionRenderer.withCriterion(criterion)
                .withSequence(sequence)
                .withRenderingStrategy(RenderingStrategy.MYBATIS3)
                .withTableAliasCalculator(TableAliasCalculator.empty())
                .build()
                .render()
                .get()
                .renderWithInitialConnector();
        
        assertAll(
                () -> assertThat(fp.fragment()).isEqualTo("id = #{parameters.p1,jdbcType=INTEGER}"),
                () -> assertThat(fp.parameters().size()).isEqualTo(1)
        );
    }

    @Test
    public void testAliasWithoutIgnore() {
        SqlTable table = SqlTable.of("foo");
        SqlColumn<Integer> column = table.column("id", JDBCType.INTEGER);
        IsEqualTo<Integer> condition = IsEqualTo.of(() -> 3);
        SqlCriterion<Integer> criterion = SqlCriterion.withColumn(column)
                .withCondition(condition)
                .build();
        AtomicInteger sequence = new AtomicInteger(1);
        Map<SqlTable, String> tableAliases = new HashMap<>();
        tableAliases.put(table, "a");
        FragmentAndParameters fp = CriterionRenderer.withCriterion(criterion)
                .withSequence(sequence)
                .withRenderingStrategy(RenderingStrategy.MYBATIS3)
                .withTableAliasCalculator(TableAliasCalculator.of(tableAliases))
                .build()
                .render()
                .get()
                .renderWithInitialConnector();
        
        assertAll(
                () -> assertThat(fp.fragment()).isEqualTo("a.id = #{parameters.p1,jdbcType=INTEGER}"),
                () -> assertThat(fp.parameters().size()).isEqualTo(1)
        );
    }

    @Test
    public void testTypeHandler() {
        SqlTable table = SqlTable.of("foo");
        SqlColumn<Date> column = SqlColumn.withName("id")
                .withTable(table)
                .withJdbcType(JDBCType.DATE)
                .withTypeHandler("foo.Bar")
                .build();
        IsEqualTo<Date> condition = IsEqualTo.of(Date::new);
        SqlCriterion<Date> criterion = SqlCriterion.withColumn(column)
                .withCondition(condition)
                .build();
        AtomicInteger sequence = new AtomicInteger(1);
        FragmentAndParameters fp = CriterionRenderer.withCriterion(criterion)
                .withSequence(sequence)
                .withRenderingStrategy(RenderingStrategy.MYBATIS3)
                .withTableAliasCalculator(TableAliasCalculator.empty())
                .build()
                .render()
                .get()
                .renderWithInitialConnector();
        
        assertAll(
                () -> assertThat(fp.fragment()).isEqualTo("id = #{parameters.p1,jdbcType=DATE,typeHandler=foo.Bar}"),
                () -> assertThat(fp.parameters().size()).isEqualTo(1)
        );
    }

    @Test
    public void testTypeHandlerAndAlias() {
        SqlTable table = SqlTable.of("foo");
        SqlColumn<Integer> column = table.column("id", JDBCType.INTEGER, "foo.Bar");
        IsEqualTo<Integer> condition = IsEqualTo.of(() -> 3);
        SqlCriterion<Integer> criterion = SqlCriterion.withColumn(column)
                .withCondition(condition)
                .build();
        AtomicInteger sequence = new AtomicInteger(1);
        Map<SqlTable, String> tableAliases = new HashMap<>();
        tableAliases.put(table, "a");
        
        FragmentAndParameters fp = CriterionRenderer.withCriterion(criterion)
                .withSequence(sequence)
                .withRenderingStrategy(RenderingStrategy.MYBATIS3)
                .withTableAliasCalculator(TableAliasCalculator.of(tableAliases))
                .build()
                .render()
                .get()
                .renderWithInitialConnector();
        
        assertAll(
                () -> assertThat(fp.fragment()).isEqualTo("a.id = #{parameters.p1,jdbcType=INTEGER,typeHandler=foo.Bar}"),
                () -> assertThat(fp.parameters().size()).isEqualTo(1)
        );
    }
}
