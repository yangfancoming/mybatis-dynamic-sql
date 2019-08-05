
package examples.springbatch.mapper;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public class PersonDynamicSqlSupport {
    
    public static Person person = new Person();
    public static SqlColumn<Integer> id = person.id;
    public static SqlColumn<String> firstName = person.firstName;
    public static SqlColumn<String> lastName = person.lastName;
    public static SqlColumn<Boolean> forPagingTest = person.forPagingTest;

    public static class Person extends SqlTable {
        public SqlColumn<Integer> id = column("id", JDBCType.INTEGER);
        public SqlColumn<String> firstName = column("first_name", JDBCType.VARCHAR);
        public SqlColumn<String> lastName = column("last_name", JDBCType.VARCHAR);
        public SqlColumn<Boolean> forPagingTest = column("for_paging_test", JDBCType.BOOLEAN);
        
        public Person() {
            super("person");
        }
    }
}
