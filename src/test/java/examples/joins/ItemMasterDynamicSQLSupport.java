
package examples.joins;

import java.sql.JDBCType;
import java.util.Date;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class ItemMasterDynamicSQLSupport {
    public static final ItemMaster itemMaster = new ItemMaster();
    public static final SqlColumn<Integer> itemId = itemMaster.itemId;
    public static final SqlColumn<Date> description = itemMaster.description;
    
    public static final class ItemMaster extends SqlTable {
        public final SqlColumn<Integer> itemId = column("item_id", JDBCType.INTEGER);
        public final SqlColumn<Date> description = column("description", JDBCType.DATE);

        public ItemMaster() {
            super("ItemMaster");
        }
    }
}
