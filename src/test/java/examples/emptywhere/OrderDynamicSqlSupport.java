
package examples.emptywhere;

import java.util.Date;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public class OrderDynamicSqlSupport {
    
    public static Order order = new Order();
    public static SqlColumn<Integer> personId = order.personId;
    public static SqlColumn<Date> orderDate = order.orderDate;

    public static class Order extends SqlTable {
        public SqlColumn<Integer> personId = column("person_id");
        public SqlColumn<Date> orderDate = column("order_date");
        
        public Order() {
            super("order");
        }
    }
}
