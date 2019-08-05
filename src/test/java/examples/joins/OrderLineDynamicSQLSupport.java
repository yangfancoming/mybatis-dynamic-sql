
package examples.joins;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class OrderLineDynamicSQLSupport {
    public static final OrderLine orderLine = new OrderLine();
    public static final SqlColumn<Integer> orderId = orderLine.orderId;
    public static final SqlColumn<Integer> itemId = orderLine.itemId;
    public static final SqlColumn<Integer> lineNumber = orderLine.lineNumber;
    public static final SqlColumn<Integer> quantity = orderLine.quantity;
    
    public static final class OrderLine extends SqlTable {
        public final SqlColumn<Integer> orderId = column("order_id", JDBCType.INTEGER);
        public final SqlColumn<Integer> itemId = column("item_id", JDBCType.INTEGER);
        public final SqlColumn<Integer> lineNumber = column("line_number", JDBCType.INTEGER);
        public final SqlColumn<Integer> quantity = column("quantity", JDBCType.INTEGER);

        public OrderLine() {
            super("OrderLine");
        }
    }
}
