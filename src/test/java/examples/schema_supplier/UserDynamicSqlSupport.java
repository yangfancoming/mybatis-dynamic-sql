
package examples.schema_supplier;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public class UserDynamicSqlSupport {
    public static final User user = new User();
    public static final SqlColumn<Integer> id = user.id;
    public static final SqlColumn<String> name = user.name;

    public static final class User extends SqlTable {
        public final SqlColumn<Integer> id = column("user_id", JDBCType.INTEGER);
        public final SqlColumn<String> name = column("user_name", JDBCType.VARCHAR);

        public User() {
            super(SchemaSupplier::schemaPropertyReader, "User");
        }
    }
}
