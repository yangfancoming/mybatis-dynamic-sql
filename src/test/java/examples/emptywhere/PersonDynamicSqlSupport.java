
package examples.emptywhere;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public class PersonDynamicSqlSupport {
    
    public static Person person = new Person();
    public static SqlColumn<Integer> id = person.id;
    public static SqlColumn<String> firstName = person.firstName;
    public static SqlColumn<String> lastName = person.lastName;

    public static class Person extends SqlTable {
        public SqlColumn<Integer> id = column("id");
        public SqlColumn<String> firstName = column("first_name");
        public SqlColumn<String> lastName = column("last_name");
        
        public Person() {
            super("person");
        }
    }
}
