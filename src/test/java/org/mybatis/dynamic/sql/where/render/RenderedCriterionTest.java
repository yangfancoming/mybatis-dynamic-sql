
package org.mybatis.dynamic.sql.where.render;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.util.FragmentAndParameters;

public class RenderedCriterionTest {

    @Test
    public void testSimpleCriteria() {
        RenderedCriterion rc = new RenderedCriterion.Builder()
                .withConnector(Optional.of("and"))
                .withInitialCondition(FragmentAndParameters.withFragment("col1 = :p1").buildOptional())
                .build()
                .get();
        
        FragmentAndParameters fp = rc.renderWithInitialConnector();
        
        assertAll(
                () -> assertThat(fp.fragment()).isEqualTo("and col1 = :p1"),
                () -> assertThat(fp.parameters().isEmpty()).isTrue()
        );
    }
}
