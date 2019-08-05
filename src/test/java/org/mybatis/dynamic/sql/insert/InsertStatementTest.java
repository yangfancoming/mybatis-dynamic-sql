
package org.mybatis.dynamic.sql.insert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mybatis.dynamic.sql.SqlBuilder.insert;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.insert.render.FieldAndValue;
import org.mybatis.dynamic.sql.insert.render.FieldAndValueCollector;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;

public class InsertStatementTest {

    private static final SqlTable foo = SqlTable.of("foo");
    private static final SqlColumn<Integer> id = foo.column("id", JDBCType.INTEGER);
    private static final SqlColumn<String> firstName = foo.column("first_name", JDBCType.VARCHAR);
    private static final SqlColumn<String> lastName = foo.column("last_name", JDBCType.VARCHAR);
    private static final SqlColumn<String> occupation = foo.column("occupation", JDBCType.VARCHAR);
    
    @Test
    public void testFullInsertStatementBuilder() {

        TestRecord record = new TestRecord();
        record.setLastName("jones");
        record.setOccupation("dino driver");
        
        InsertStatementProvider<TestRecord> insertStatement = insert(record)
                .into(foo)
                .map(id).toProperty("id")
                .map(firstName).toProperty("firstName")
                .map(lastName).toProperty("lastName")
                .map(occupation).toProperty("occupation")
                .build()
                .render(RenderingStrategy.MYBATIS3);
        
        String expectedStatement = "insert into foo "
                + "(id, first_name, last_name, occupation) "
                + "values (#{record.id,jdbcType=INTEGER}, #{record.firstName,jdbcType=VARCHAR}, #{record.lastName,jdbcType=VARCHAR}, #{record.occupation,jdbcType=VARCHAR})";
        
        assertThat(insertStatement.getInsertStatement()).isEqualTo(expectedStatement);
    }

    @Test
    public void testInsertStatementBuilderWithNulls() {

        TestRecord record = new TestRecord();
        
        InsertStatementProvider<TestRecord> insertStatement = insert(record)
                .into(foo)
                .map(id).toProperty("id")
                .map(firstName).toProperty("firstName")
                .map(lastName).toProperty("lastName")
                .map(occupation).toNull()
                .build()
                .render(RenderingStrategy.MYBATIS3);

        String expected = "insert into foo (id, first_name, last_name, occupation) "
                + "values (#{record.id,jdbcType=INTEGER}, #{record.firstName,jdbcType=VARCHAR}, #{record.lastName,jdbcType=VARCHAR}, null)";
        assertThat(insertStatement.getInsertStatement()).isEqualTo(expected);
    }

    @Test
    public void testInsertStatementBuilderWithConstants() {

        TestRecord record = new TestRecord();
        
        InsertStatementProvider<TestRecord> insertStatement = insert(record)
                .into(foo)
                .map(id).toConstant("3")
                .map(firstName).toProperty("firstName")
                .map(lastName).toProperty("lastName")
                .map(occupation).toStringConstant("Y")
                .build()
                .render(RenderingStrategy.MYBATIS3);

        String expected = "insert into foo (id, first_name, last_name, occupation) "
                + "values (3, #{record.firstName,jdbcType=VARCHAR}, #{record.lastName,jdbcType=VARCHAR}, 'Y')";
        assertThat(insertStatement.getInsertStatement()).isEqualTo(expected);
    }
    
    @Test
    public void testSelectiveInsertStatementBuilder() {
        TestRecord record = new TestRecord();
        record.setLastName("jones");
        record.setOccupation("dino driver");
        
        InsertStatementProvider<TestRecord> insertStatement = insert(record)
                .into(foo)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(firstName).toPropertyWhenPresent("firstName", record::getFirstName)
                .map(lastName).toPropertyWhenPresent("lastName", record::getLastName)
                .map(occupation).toPropertyWhenPresent("occupation", record::getOccupation)
                .build()
                .render(RenderingStrategy.MYBATIS3);

        String expected = "insert into foo (last_name, occupation) "
                + "values (#{record.lastName,jdbcType=VARCHAR}, #{record.occupation,jdbcType=VARCHAR})";
        assertThat(insertStatement.getInsertStatement()).isEqualTo(expected);
    }

    @Test
    public void testParallelStream() {

        List<FieldAndValue> mappings = new ArrayList<>();
        
        mappings.add(newFieldAndValue(id.name(), "{record.id}"));
        mappings.add(newFieldAndValue(firstName.name(), "{record.firstName}"));
        mappings.add(newFieldAndValue(lastName.name(), "{record.lastName}"));
        mappings.add(newFieldAndValue(occupation.name(), "{record.occupation}"));
        
        FieldAndValueCollector collector = 
                mappings.parallelStream().collect(Collector.of(
                        FieldAndValueCollector::new,
                        FieldAndValueCollector::add,
                        FieldAndValueCollector::merge));
                
        String expectedColumnsPhrase = "(id, first_name, last_name, occupation)";
        String expectedValuesPhrase = "values ({record.id}, {record.firstName}, {record.lastName}, {record.occupation})";
        
        assertAll(
                () -> assertThat(collector.columnsPhrase()).isEqualTo(expectedColumnsPhrase),
                () -> assertThat(collector.valuesPhrase()).isEqualTo(expectedValuesPhrase)
        );
    }
    
    private FieldAndValue newFieldAndValue(String fieldName, String valuePhrase) {
        return FieldAndValue.withFieldName(fieldName)
                .withValuePhrase(valuePhrase)
                .build();
    }
    
    @Test
    public void testParallelStreamForMultiRecord() {

        List<FieldAndValue> mappings = new ArrayList<>();
        
        mappings.add(newFieldAndValues(id.name(), "#{records[%s].id}"));
        mappings.add(newFieldAndValues(firstName.name(), "#{records[%s].firstName}"));
        mappings.add(newFieldAndValues(lastName.name(), "#{records[%s].lastName}"));
        mappings.add(newFieldAndValues(occupation.name(), "#{records[%s].occupation}"));
        
        FieldAndValueCollector collector = 
                mappings.parallelStream().collect(Collector.of(
                        FieldAndValueCollector::new,
                        FieldAndValueCollector::add,
                        FieldAndValueCollector::merge));
                
        String expectedColumnsPhrase = "(id, first_name, last_name, occupation)";
        String expectedValuesPhrase = "values"
                + " (#{records[0].id}, #{records[0].firstName}, #{records[0].lastName}, #{records[0].occupation}),"
                + " (#{records[1].id}, #{records[1].firstName}, #{records[1].lastName}, #{records[1].occupation})";
        
        assertAll(
                () -> assertThat(collector.columnsPhrase()).isEqualTo(expectedColumnsPhrase),
                () -> assertThat(collector.multiRowInsertValuesPhrase(2)).isEqualTo(expectedValuesPhrase)
        );
    }
    
    private FieldAndValue newFieldAndValues(String fieldName, String valuePhrase) {
        return FieldAndValue.withFieldName(fieldName)
                .withValuePhrase(valuePhrase)
                .build();
    }
    
    public static class TestRecord {
        private Integer id;
        private String firstName;
        private String lastName;
        private String occupation;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }
    }
}
