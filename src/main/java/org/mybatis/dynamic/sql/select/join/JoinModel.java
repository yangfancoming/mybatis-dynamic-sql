
package org.mybatis.dynamic.sql.select.join;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class JoinModel {
    private List<JoinSpecification> joinSpecifications = new ArrayList<>();
    
    private JoinModel(List<JoinSpecification> joinSpecifications) {
        this.joinSpecifications.addAll(joinSpecifications);
    }

    public <R> Stream<R> mapJoinSpecifications(Function<JoinSpecification, R> mapper) {
        return joinSpecifications.stream().map(mapper);
    }
    
    public static JoinModel of(List<JoinSpecification> joinSpecifications) {
        return new JoinModel(joinSpecifications);
    }
}
