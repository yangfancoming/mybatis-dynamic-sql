
package examples.groupby;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class PersonDynamicSqlSupport {
    public static final Person person = new Person();
    public static final SqlColumn<Integer> id = person.id;
    public static final SqlColumn<String> firstName = person.firstName;
    public static final SqlColumn<String> lastName = person.lastName;
    public static final SqlColumn<String> gender = person.gender;
    public static final SqlColumn<String> human = person.human;
    public static final SqlColumn<Integer> age = person.age;

    public static final class Person extends SqlTable {
        public final SqlColumn<Integer> id = column("person_id", JDBCType.INTEGER);
        public final SqlColumn<String> firstName = column("first_name", JDBCType.VARCHAR);
        public final SqlColumn<String> lastName = column("last_name", JDBCType.VARCHAR);
        public final SqlColumn<String> gender = column("gender", JDBCType.VARCHAR);
        public final SqlColumn<String> human = column("human_flag", JDBCType.VARCHAR);
        public final SqlColumn<Integer> age = column("age", JDBCType.INTEGER);

        public Person() {
            super("Person");
        }
    }
}
