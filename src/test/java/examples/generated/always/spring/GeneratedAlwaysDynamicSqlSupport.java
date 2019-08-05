
package examples.generated.always.spring;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class GeneratedAlwaysDynamicSqlSupport {
    public static final GeneratedAlways generatedAlways = new GeneratedAlways();
    public static final SqlColumn<Integer> id = generatedAlways.id;
    public static final SqlColumn<String> firstName = generatedAlways.firstName;
    public static final SqlColumn<String> lastName = generatedAlways.lastName;
    public static final SqlColumn<String> fullName = generatedAlways.fullName;
    
    public static final class GeneratedAlways extends SqlTable {
        public final SqlColumn<Integer> id = column("id");
        public final SqlColumn<String> firstName = column("first_name");
        public final SqlColumn<String> lastName = column("last_name");
        public final SqlColumn<String> fullName = column("full_name");

        public GeneratedAlways() {
            super("GeneratedAlways");
        }
    }
}
