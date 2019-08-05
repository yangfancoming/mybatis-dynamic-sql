
package org.mybatis.dynamic.sql.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.mybatis.dynamic.sql.SortSpecification;

public class OrderByModel {
    private List<SortSpecification> columns = new ArrayList<>();
    
    private OrderByModel(List<SortSpecification> columns) {
        this.columns.addAll(columns);
    }
    
    public <R> Stream<R> mapColumns(Function<SortSpecification, R> mapper) {
        return columns.stream().map(mapper);
    }
    
    public static OrderByModel of(SortSpecification...columns) {
        return new OrderByModel(Arrays.asList(columns));
    }
}
