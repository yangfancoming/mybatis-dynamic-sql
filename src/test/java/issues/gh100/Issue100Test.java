
package issues.gh100;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

public class Issue100Test {

    @Test
    public void testNormalUsage() {
        SelectStatementProvider selectStatement = select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student)
                .join(StudentRegDynamicSqlSupport.studentReg)
                .on(StudentDynamicSqlSupport.id, equalTo(StudentRegDynamicSqlSupport.studentid))
                .where(StudentDynamicSqlSupport.idcard, isEqualTo("fred"))
                .union()
                .select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student)
                .orderBy(StudentDynamicSqlSupport.id)
                .limit(3)
                .offset(2)
                .build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expected = "select student.id, student.name, student.idcard" 
                + " from student join student_reg on student.id = student_reg.studentId"
                + " where student.idcard = #{parameters.p1}"
                + " union"
                + " select id, name, idcard"
                + " from student"
                + " order by id"
                + " limit #{parameters._limit}"
                + " offset #{parameters._offset}";
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
    }

    @Test
    public void testSuccessiveBuild01() {
        QueryExpressionDSL<SelectModel> builder = select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student);
        
        builder.join(StudentRegDynamicSqlSupport.studentReg)
        .on(StudentDynamicSqlSupport.id, equalTo(StudentRegDynamicSqlSupport.studentid));
                
        SelectStatementProvider selectStatement = builder.build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expected = "select student.id, student.name, student.idcard" 
                + " from student join student_reg on student.id = student_reg.studentId";
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
    }

    @Test
    public void testSuccessiveBuild02() {
        QueryExpressionDSL<SelectModel> builder = select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student);
        
        builder.join(StudentRegDynamicSqlSupport.studentReg)
        .on(StudentDynamicSqlSupport.id, equalTo(StudentRegDynamicSqlSupport.studentid))
        .where(StudentDynamicSqlSupport.idcard, isEqualTo("fred"));
                
        SelectStatementProvider selectStatement = builder.build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expected = "select student.id, student.name, student.idcard" 
                + " from student join student_reg on student.id = student_reg.studentId"
                + " where student.idcard = #{parameters.p1}";
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
    }

    @Test
    public void testSuccessiveBuild03() {
        QueryExpressionDSL<SelectModel> builder = select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student);
        
        builder.join(StudentRegDynamicSqlSupport.studentReg)
        .on(StudentDynamicSqlSupport.id, equalTo(StudentRegDynamicSqlSupport.studentid))
        .where(StudentDynamicSqlSupport.idcard, isEqualTo("fred"))
        .orderBy(StudentDynamicSqlSupport.id);
                
        SelectStatementProvider selectStatement = builder.build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expected = "select student.id, student.name, student.idcard" 
                + " from student join student_reg on student.id = student_reg.studentId"
                + " where student.idcard = #{parameters.p1}"
                + " order by id";
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
    }

    @Test
    public void testSuccessiveBuild04() {
        QueryExpressionDSL<SelectModel> builder = select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student);
        
        builder.join(StudentRegDynamicSqlSupport.studentReg)
        .on(StudentDynamicSqlSupport.id, equalTo(StudentRegDynamicSqlSupport.studentid))
        .where(StudentDynamicSqlSupport.idcard, isEqualTo("fred"))
        .orderBy(StudentDynamicSqlSupport.id)
        .limit(3);

        SelectStatementProvider selectStatement = builder.build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expected = "select student.id, student.name, student.idcard" 
                + " from student join student_reg on student.id = student_reg.studentId"
                + " where student.idcard = #{parameters.p1}"
                + " order by id"
                + " limit #{parameters._limit}";
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
    }

    @Test
    public void testSuccessiveBuild05() {
        QueryExpressionDSL<SelectModel> builder = select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student);
        
        builder.join(StudentRegDynamicSqlSupport.studentReg)
        .on(StudentDynamicSqlSupport.id, equalTo(StudentRegDynamicSqlSupport.studentid))
        .where(StudentDynamicSqlSupport.idcard, isEqualTo("fred"))
        .orderBy(StudentDynamicSqlSupport.id)
        .limit(3)
        .offset(2);

        SelectStatementProvider selectStatement = builder.build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expected = "select student.id, student.name, student.idcard" 
                + " from student join student_reg on student.id = student_reg.studentId"
                + " where student.idcard = #{parameters.p1}"
                + " order by id"
                + " limit #{parameters._limit}"
                + " offset #{parameters._offset}";
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
    }

    @Test
    public void testSuccessiveBuild06() {
        QueryExpressionDSL<SelectModel> builder = select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student);
        
        builder.join(StudentRegDynamicSqlSupport.studentReg)
        .on(StudentDynamicSqlSupport.id, equalTo(StudentRegDynamicSqlSupport.studentid))
        .where(StudentDynamicSqlSupport.idcard, isEqualTo("fred"))
        .orderBy(StudentDynamicSqlSupport.id)
        .offset(2);

        SelectStatementProvider selectStatement = builder.build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expected = "select student.id, student.name, student.idcard" 
                + " from student join student_reg on student.id = student_reg.studentId"
                + " where student.idcard = #{parameters.p1}"
                + " order by id"
                + " offset #{parameters._offset} rows";
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
    }

    @Test
    public void testSuccessiveBuild07() {
        QueryExpressionDSL<SelectModel> builder = select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student);
        
        builder.join(StudentRegDynamicSqlSupport.studentReg)
        .on(StudentDynamicSqlSupport.id, equalTo(StudentRegDynamicSqlSupport.studentid))
        .where(StudentDynamicSqlSupport.idcard, isEqualTo("fred"))
        .orderBy(StudentDynamicSqlSupport.id)
        .offset(2)
        .fetchFirst(3).rowsOnly();

        SelectStatementProvider selectStatement = builder.build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expected = "select student.id, student.name, student.idcard" 
                + " from student join student_reg on student.id = student_reg.studentId"
                + " where student.idcard = #{parameters.p1}"
                + " order by id"
                + " offset #{parameters._offset} rows"
                + " fetch first #{parameters._fetchFirstRows} rows only";
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
    }

    @Test
    public void testSuccessiveBuild08() {
        QueryExpressionDSL<SelectModel> builder = select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student);
        
        builder.join(StudentRegDynamicSqlSupport.studentReg)
        .on(StudentDynamicSqlSupport.id, equalTo(StudentRegDynamicSqlSupport.studentid))
        .where(StudentDynamicSqlSupport.idcard, isEqualTo("fred"))
        .orderBy(StudentDynamicSqlSupport.id)
        .fetchFirst(3).rowsOnly();

        SelectStatementProvider selectStatement = builder.build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expected = "select student.id, student.name, student.idcard" 
                + " from student join student_reg on student.id = student_reg.studentId"
                + " where student.idcard = #{parameters.p1}"
                + " order by id"
                + " fetch first #{parameters._fetchFirstRows} rows only";
        assertThat(selectStatement.getSelectStatement()).isEqualTo(expected);
    }

    @Test
    public void test3() {
        QueryExpressionDSL<SelectModel>.JoinSpecificationFinisher on = select(StudentDynamicSqlSupport.id, StudentDynamicSqlSupport.name, StudentDynamicSqlSupport.idcard)
                .from(StudentDynamicSqlSupport.student)
                .join(StudentRegDynamicSqlSupport.studentReg)
                .on(StudentDynamicSqlSupport.id, equalTo(StudentRegDynamicSqlSupport.studentid));
        
        on.where(StudentDynamicSqlSupport.idcard, isEqualTo("fred"));
        
        SelectStatementProvider selectStatement = on
                .build()
                .render(RenderingStrategy.MYBATIS3);
        
        assertThat(selectStatement.getSelectStatement())
            .isEqualTo("select student.id, student.name, student.idcard from student join student_reg on student.id = student_reg.studentId where student.idcard = #{parameters.p1}");
    }
}
