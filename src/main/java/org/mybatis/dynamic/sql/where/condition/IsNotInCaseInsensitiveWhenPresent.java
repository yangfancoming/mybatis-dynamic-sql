
package org.mybatis.dynamic.sql.where.condition;

import java.util.Collection;
import java.util.Objects;

public class IsNotInCaseInsensitiveWhenPresent extends IsNotInCaseInsensitive {

    protected IsNotInCaseInsensitiveWhenPresent(Collection<String> values) {
        super(values, s -> s.filter(Objects::nonNull));
    }

    public static IsNotInCaseInsensitiveWhenPresent of(Collection<String> values) {
        return new IsNotInCaseInsensitiveWhenPresent(values);
    }
}
