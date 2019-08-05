
package org.mybatis.dynamic.sql.where.condition;

import java.util.Collection;
import java.util.Objects;

public class IsInWhenPresent<T> extends IsIn<T> {

    protected IsInWhenPresent(Collection<T> values) {
        super(values, s -> s.filter(Objects::nonNull));
    }

    public static <T> IsInWhenPresent<T> of(Collection<T> values) {
        return new IsInWhenPresent<>(values);
    }
}
