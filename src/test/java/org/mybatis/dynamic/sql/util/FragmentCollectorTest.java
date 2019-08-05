
package org.mybatis.dynamic.sql.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

public class FragmentCollectorTest {

    @Test
    public void testWhereFragmentCollectorMerge() {
        FragmentCollector fc1 = new FragmentCollector();
        FragmentAndParameters fp1 = FragmentAndParameters.withFragment(":p1")
                .withParameter("p1", 1)
                .build();
        fc1.add(fp1);

        FragmentCollector fc2 = new FragmentCollector();
        FragmentAndParameters fp2 = FragmentAndParameters.withFragment(":p2")
                .withParameter("p2", 2)
                .build();
        fc2.add(fp2);

        fc1.merge(fc2);

        assertAll(
                () -> assertThat(fc1.fragments.size()).isEqualTo(2),
                () -> assertThat(fc1.fragments.get(0)).isEqualTo(":p1"),
                () -> assertThat(fc1.fragments.get(1)).isEqualTo(":p2"),
                () -> assertThat(fc1.parameters.size()).isEqualTo(2),
                () -> assertThat(fc1.parameters.get("p1")).isEqualTo(1),
                () -> assertThat(fc1.parameters.get("p2")).isEqualTo(2)
        );
    }
}
