
package org.mybatis.dynamic.sql.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.mybatis.dynamic.sql.BasicColumn;

public class GroupByModel {
    private List<BasicColumn> columns = new ArrayList<>();
    
    private GroupByModel(List<BasicColumn> columns) {
        this.columns.addAll(columns);
    }
    
    public <R> Stream<R> mapColumns(Function<BasicColumn, R> mapper) {
        return columns.stream().map(mapper);
    }
    
    public static GroupByModel of(BasicColumn...columns) {
        return new GroupByModel(Arrays.asList(columns));
    }
}
