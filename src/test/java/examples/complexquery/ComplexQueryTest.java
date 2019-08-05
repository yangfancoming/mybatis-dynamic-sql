
package examples.complexquery;

import static examples.complexquery.PersonDynamicSqlSupport.firstName;
import static examples.complexquery.PersonDynamicSqlSupport.id;
import static examples.complexquery.PersonDynamicSqlSupport.lastName;
import static examples.complexquery.PersonDynamicSqlSupport.person;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class ComplexQueryTest {

    @Test
    public void testId() {
        SelectStatementProvider selectStatement = search(2, null, null);
        
        String expected = "select person_id, first_name, last_name"
                + " from Person"
                + " where person_id = #{parameters.p1}"
                + " order by last_name, first_name"
                + " fetch first #{parameters._fetchFirstRows} rows only";
                
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
        assertThat(selectStatement.getParameters().get("p1")).isEqualTo(2);
        assertThat(selectStatement.getParameters().get("_fetchFirstRows")).isEqualTo(50L);
    }
    
    @Test
    public void testFirstNameOnly() {
        SelectStatementProvider selectStatement = search(null, "fred", null);
        
        String expected = "select person_id, first_name, last_name"
                + " from Person"
                + " where first_name like #{parameters.p1}"
                + " order by last_name, first_name"
                + " fetch first #{parameters._fetchFirstRows} rows only";
                
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
        assertThat(selectStatement.getParameters().get("p1")).isEqualTo("%fred%");
        assertThat(selectStatement.getParameters().get("_fetchFirstRows")).isEqualTo(50L);
    }
    
    @Test
    public void testLastNameOnly() {
        SelectStatementProvider selectStatement = search(null, null, "flintstone");
        
        String expected = "select person_id, first_name, last_name"
                + " from Person"
                + " where last_name like #{parameters.p1}"
                + " order by last_name, first_name"
                + " fetch first #{parameters._fetchFirstRows} rows only";
                
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
        assertThat(selectStatement.getParameters().get("p1")).isEqualTo("%flintstone%");
        assertThat(selectStatement.getParameters().get("_fetchFirstRows")).isEqualTo(50L);
    }
    
    @Test
    public void testBothNames() {
        SelectStatementProvider selectStatement = search(null, "fred", "flintstone");
        
        String expected = "select person_id, first_name, last_name"
                + " from Person"
                + " where first_name like #{parameters.p1}"
                + " and last_name like #{parameters.p2}"
                + " order by last_name, first_name"
                + " fetch first #{parameters._fetchFirstRows} rows only";
                
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
        assertThat(selectStatement.getParameters().get("p1")).isEqualTo("%fred%");
        assertThat(selectStatement.getParameters().get("p2")).isEqualTo("%flintstone%");
        assertThat(selectStatement.getParameters().get("_fetchFirstRows")).isEqualTo(50L);
    }
    
    @Test
    public void testAllNull() {
        SelectStatementProvider selectStatement = search(null, null, null);
        
        String expected = "select person_id, first_name, last_name"
                + " from Person"
                + " order by last_name, first_name"
                + " fetch first #{parameters._fetchFirstRows} rows only";
                
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
        assertThat(selectStatement.getParameters().get("_fetchFirstRows")).isEqualTo(50L);
    }
    
    public SelectStatementProvider search(Integer targetId, String fName, String lName) {
        QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder = select(id, firstName, lastName)
                .from(person)
                .where();
        
        if (targetId != null) {
            builder
                .and(id, isEqualTo(targetId));
        } else {
            builder
                .and(firstName, isLike(fName).when(Objects::nonNull).then(s -> "%" + s + "%"))
                .and(lastName, isLikeWhenPresent(lName).then(this::addWildcards));
        }

        builder
            .orderBy(lastName, firstName)
            .fetchFirst(50).rowsOnly();
        
        return builder.build().render(RenderingStrategy.MYBATIS3);
    }
    
    public String addWildcards(String s) {
        return "%" + s + "%";
    }
}
