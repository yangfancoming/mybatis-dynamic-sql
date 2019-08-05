
package org.mybatis.dynamic.sql.where.condition;

import java.util.Collection;
import java.util.Objects;

public class IsNotInWhenPresent<T> extends IsNotIn<T> {

    protected IsNotInWhenPresent(Collection<T> values) {
        super(values, s -> s.filter(Objects::nonNull));
    }

    public static <T> IsNotInWhenPresent<T> of(Collection<T> values) {
        return new IsNotInWhenPresent<>(values);
    }
}
