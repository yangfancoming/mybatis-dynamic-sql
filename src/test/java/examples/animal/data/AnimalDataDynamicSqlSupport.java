
package examples.animal.data;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class AnimalDataDynamicSqlSupport {
    public static final AnimalData animalData = new AnimalData();
    public static final SqlColumn<Integer> id = animalData.id; 
    public static final SqlColumn<String> animalName = animalData.animalName;
    public static final SqlColumn<Double> bodyWeight = animalData.bodyWeight;
    public static final SqlColumn<Double> brainWeight = animalData.brainWeight;
    
    public static final class AnimalData extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER); 
        public final SqlColumn<String> animalName = column("animal_name", JDBCType.VARCHAR);
        public final SqlColumn<Double> bodyWeight = column("body_weight", JDBCType.DOUBLE);
        public final SqlColumn<Double> brainWeight = column("brain_weight", JDBCType.DOUBLE);

        public AnimalData() {
            super("AnimalData");
        }
    }
}
