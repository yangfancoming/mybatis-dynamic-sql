
package org.mybatis.dynamic.sql.where.condition;

import java.util.Collection;
import java.util.Objects;

public class IsInCaseInsensitiveWhenPresent extends IsInCaseInsensitive {

    protected IsInCaseInsensitiveWhenPresent(Collection<String> values) {
        super(values, s -> s.filter(Objects::nonNull));
    }

    public static IsInCaseInsensitiveWhenPresent of(Collection<String> values) {
        return new IsInCaseInsensitiveWhenPresent(values);
    }
}
