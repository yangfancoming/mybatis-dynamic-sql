
package examples.complexquery;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class PersonDynamicSqlSupport {
    public static final Person person = new Person();
    public static final SqlColumn<Integer> id = person.id;
    public static final SqlColumn<String> firstName = person.firstName;
    public static final SqlColumn<String> lastName = person.lastName;
    public static final SqlColumn<Integer> age = person.age;

    public static final class Person extends SqlTable {
        public final SqlColumn<Integer> id = column("person_id");
        public final SqlColumn<String> firstName = column("first_name");
        public final SqlColumn<String> lastName = column("last_name");
        public final SqlColumn<Integer> age = column("age");

        public Person() {
            super("Person");
        }
    }
}
