
package org.mybatis.dynamic.sql.insert.render;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FieldAndValueCollector {
    
    private List<FieldAndValue> fieldsAndValue = new ArrayList<>();
    
    public FieldAndValueCollector() {
        super();
    }
    
    public void add(FieldAndValue fieldAndValue) {
        fieldsAndValue.add(fieldAndValue);
    }
    
    public FieldAndValueCollector merge(FieldAndValueCollector other) {
        fieldsAndValue.addAll(other.fieldsAndValue);
        return this;
    }

    public String columnsPhrase() {
        return fieldsAndValue.stream()
                .map(FieldAndValue::fieldName)
                .collect(Collectors.joining(", ", "(", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public String valuesPhrase() {
        return fieldsAndValue.stream()
                .map(FieldAndValue::valuePhrase)
                .collect(Collectors.joining(", ", "values (", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
    
    public String multiRowInsertValuesPhrase(int rowCount) {
        return IntStream.range(0, rowCount)
                .mapToObj(this::toSingleRowOfValues)
                .collect(Collectors.joining(", ", "values ", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
    
    private String toSingleRowOfValues(int row) {
        return fieldsAndValue.stream()
                .map(fmv -> fmv.valuePhrase(row))
                .collect(Collectors.joining(", ", "(", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
    
    public static Collector<FieldAndValue, FieldAndValueCollector, FieldAndValueCollector> collect() {
        return Collector.of(FieldAndValueCollector::new,
                FieldAndValueCollector::add,
                FieldAndValueCollector::merge);
    }
}