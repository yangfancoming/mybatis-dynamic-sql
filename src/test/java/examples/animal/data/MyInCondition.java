
package examples.animal.data;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.mybatis.dynamic.sql.where.condition.IsIn;

public class MyInCondition extends IsIn<String> {
    protected MyInCondition(List<String> values) {
        super(values, s -> s.filter(Objects::nonNull)
                .map(String::trim)
                .filter(st -> !st.isEmpty()));
    }

    public static MyInCondition isIn(String...values) {
        return new MyInCondition(Arrays.asList(values));
    }
}
