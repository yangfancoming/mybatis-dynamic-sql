
package org.mybatis.dynamic.sql.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class FragmentCollector {
    List<String> fragments = new ArrayList<>();
    Map<String, Object> parameters = new HashMap<>();
    
    FragmentCollector() {
        super();
    }
    
    private FragmentCollector(FragmentAndParameters initialFragment) {
        add(initialFragment);
    }
    
    public void add(FragmentAndParameters fragmentAndParameters) {
        fragments.add(fragmentAndParameters.fragment());
        parameters.putAll(fragmentAndParameters.parameters());
    }
    
    public void add(Optional<FragmentAndParameters> fragmentAndParameters) {
        fragmentAndParameters.ifPresent(this::add);
    }
    
    public FragmentCollector merge(FragmentCollector other) {
        fragments.addAll(other.fragments);
        parameters.putAll(other.parameters);
        return this;
    }
    
    public Stream<String> fragments() {
        return fragments.stream();
    }
    
    public Map<String, Object> parameters() {
        return parameters;
    }
    
    public boolean hasMultipleFragments() {
        return fragments.size() > 1;
    }
    
    public boolean isEmpty() {
        return fragments.isEmpty();
    }
    
    public static Collector<FragmentAndParameters, FragmentCollector, FragmentCollector> collect() {
        return Collector.of(FragmentCollector::new,
                FragmentCollector::add,
                FragmentCollector::merge);
    }

    public static Collector<FragmentAndParameters, FragmentCollector, FragmentCollector> collect(
            FragmentAndParameters initialFragment) {
        return Collector.of(() -> new FragmentCollector(initialFragment),
                FragmentCollector::add,
                FragmentCollector::merge);
    }
}
