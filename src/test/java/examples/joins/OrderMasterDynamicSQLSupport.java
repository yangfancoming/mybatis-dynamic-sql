
package examples.joins;

import java.sql.JDBCType;
import java.util.Date;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class OrderMasterDynamicSQLSupport {
    public static final OrderMaster orderMaster = new OrderMaster();
    public static final SqlColumn<Integer> orderId = orderMaster.orderId;
    public static final SqlColumn<Date> orderDate = orderMaster.orderDate;
    
    public static final class OrderMaster extends SqlTable {
        public final SqlColumn<Integer> orderId = column("order_id", JDBCType.INTEGER);
        public final SqlColumn<Date> orderDate = column("order_date", JDBCType.DATE);

        public OrderMaster() {
            super("OrderMaster");
        }
    }
}
