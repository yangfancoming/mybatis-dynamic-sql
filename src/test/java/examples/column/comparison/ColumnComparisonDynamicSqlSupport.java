
package examples.column.comparison;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class ColumnComparisonDynamicSqlSupport {
    public static final ColumnComparison columnComparison = new ColumnComparison();
    public static final SqlColumn<Integer> number1 = columnComparison.number1; 
    public static final SqlColumn<Integer> number2 = columnComparison.number2; 
    
    public static final class ColumnComparison extends SqlTable {
        public final SqlColumn<Integer> number1 = column("number1", JDBCType.INTEGER); 
        public final SqlColumn<Integer> number2 = column("number2", JDBCType.INTEGER); 

        public ColumnComparison() {
            super("ColumnComparison");
        }
    }
}
