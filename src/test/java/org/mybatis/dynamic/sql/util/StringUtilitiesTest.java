
package org.mybatis.dynamic.sql.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StringUtilitiesTest {

    @Test
    public void testThatUpperCaseIsAppliedAfter() {
        Stream<String> ss = Stream.of("fred", "wilma", "barney", "betty");
        
        UnaryOperator<Stream<String>> valueModifier = s -> s.filter(st -> st.equals("fred"));
        
        UnaryOperator<Stream<String>> ua = StringUtilities.upperCaseAfter(valueModifier);
        
        List<String> list = ua.apply(ss).collect(Collectors.toList());

        assertAll(
                () -> assertThat(list.size()).isEqualTo(1),
                () -> assertThat(list.get(0)).isEqualTo("FRED")
        );
    }
}
