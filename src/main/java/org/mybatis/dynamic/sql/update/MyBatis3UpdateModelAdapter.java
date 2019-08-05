
package org.mybatis.dynamic.sql.update;

import java.util.Objects;
import java.util.function.Function;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

/**
 * This adapter will render the underlying update model for MyBatis3, and then call a MyBatis mapper method.
 * 
 * @author Jeff Butler
 *
 */
public class MyBatis3UpdateModelAdapter<R> {

    private UpdateModel updateModel;
    private Function<UpdateStatementProvider, R> mapperMethod;
    
    private MyBatis3UpdateModelAdapter(UpdateModel updateModel, Function<UpdateStatementProvider, R> mapperMethod) {
        this.updateModel = Objects.requireNonNull(updateModel);
        this.mapperMethod = Objects.requireNonNull(mapperMethod);
    }
    
    public R execute() {
        return mapperMethod.apply(updateStatement());
    }
    
    private UpdateStatementProvider updateStatement() {
        return updateModel.render(RenderingStrategy.MYBATIS3);
    }
    
    public static <R> MyBatis3UpdateModelAdapter<R> of(UpdateModel updateModel,
            Function<UpdateStatementProvider, R> mapperMethod) {
        return new MyBatis3UpdateModelAdapter<>(updateModel, mapperMethod);
    }
}
