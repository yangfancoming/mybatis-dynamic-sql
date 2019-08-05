
package examples.joins;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public class UserDynamicSQLSupport {
    public static final User1 user1 = new User1();
    public static final User2 user2 = new User2();
   
    
    public static final class User1 extends SqlTable {
        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);
        public final SqlColumn<String> userName = column("user_name", JDBCType.VARCHAR);
        public final SqlColumn<Integer> parentId = column("parent_id", JDBCType.INTEGER);
        
        public User1() {
            super("User");
        }
    }

    public static final class User2 extends SqlTable {
        public final SqlColumn<Integer> userId = column("user_id", JDBCType.INTEGER);
        public final SqlColumn<String> userName = column("user_name", JDBCType.VARCHAR);
        public final SqlColumn<Integer> parentId = column("parent_id", JDBCType.INTEGER);
        
        public User2() {
            super("User");
        }
    }
}
