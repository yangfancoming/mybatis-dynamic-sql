
package org.mybatis.dynamic.sql.util;

@FunctionalInterface
public interface Buildable<T> {
    T build();
}