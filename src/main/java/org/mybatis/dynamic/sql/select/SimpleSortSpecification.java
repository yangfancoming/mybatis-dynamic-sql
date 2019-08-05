
package org.mybatis.dynamic.sql.select;

import java.util.Objects;

import org.mybatis.dynamic.sql.SortSpecification;

/**
 * This class is used for an order by phrase where there is no suitable column name
 * to use (for example a calculated column or an aggregate column). 
 * 
 * @author Jeff Butler
 */
public class SimpleSortSpecification implements SortSpecification {
    
    private String name;
    private boolean isDescending;

    private SimpleSortSpecification(String name) {
        this.name = Objects.requireNonNull(name);
    }
    
    @Override
    public SortSpecification descending() {
        SimpleSortSpecification answer = new SimpleSortSpecification(name);
        answer.isDescending = true;
        return answer;
    }

    @Override
    public String aliasOrName() {
        return name;
    }

    @Override
    public boolean isDescending() {
        return isDescending;
    }
    
    public static SimpleSortSpecification of(String name) {
        return new SimpleSortSpecification(name);
    }
}
