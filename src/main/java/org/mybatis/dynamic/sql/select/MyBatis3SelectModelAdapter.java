
package org.mybatis.dynamic.sql.select;

import java.util.Objects;
import java.util.function.Function;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

/**
 * This adapter will render the underlying select model for MyBatis3, and then call a MyBatis mapper method.
 * 
 * @author Jeff Butler
 *
 */
public class MyBatis3SelectModelAdapter<R> {

    private SelectModel selectModel;
    private Function<SelectStatementProvider, R> mapperMethod;
    
    private MyBatis3SelectModelAdapter(SelectModel selectModel, Function<SelectStatementProvider, R> mapperMethod) {
        this.selectModel = Objects.requireNonNull(selectModel);
        this.mapperMethod = Objects.requireNonNull(mapperMethod);
    }
    
    public R execute() {
        return mapperMethod.apply(selectStatement());
    }
    
    private SelectStatementProvider selectStatement() {
        return selectModel.render(RenderingStrategy.MYBATIS3);
    }
    
    public static <R> MyBatis3SelectModelAdapter<R> of(SelectModel selectModel,
            Function<SelectStatementProvider, R> mapperMethod) {
        return new MyBatis3SelectModelAdapter<>(selectModel, mapperMethod);
    }
}
