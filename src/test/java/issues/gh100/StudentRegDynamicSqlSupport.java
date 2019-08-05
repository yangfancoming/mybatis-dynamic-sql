
package issues.gh100;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public class StudentRegDynamicSqlSupport {
    public static final StudentReg studentReg = new StudentReg();
    public static final SqlColumn<String> studentid = studentReg.studentid;
    public static final SqlColumn<String> examnumber = studentReg.examnumber;
    public static final SqlColumn<String> regcode = studentReg.regcode;

    public static final class StudentReg extends SqlTable {
        public final SqlColumn<String> studentid = column("studentId");
        public final SqlColumn<String> examnumber = column("examnumber");
        public final SqlColumn<String> regcode = column("regcode");
        
        public StudentReg() {
            super("student_reg");
        }
    }
}
