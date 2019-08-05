
package issues.gh100;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public class StudentDynamicSqlSupport {
    public static final Student student = new Student();
    public static final SqlColumn<String> id = student.id;
    public static final SqlColumn<String> name = student.name;
    public static final SqlColumn<String> idcard = student.idcard;

    public static final class Student extends SqlTable {
        public final SqlColumn<String> id = column("id");
        public final SqlColumn<String> name = column("name");
        public final SqlColumn<String> idcard = column("idcard");
        
        public Student() {
            super("student");
        }
    }
}
