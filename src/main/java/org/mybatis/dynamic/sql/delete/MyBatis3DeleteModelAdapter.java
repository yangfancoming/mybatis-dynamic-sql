
package org.mybatis.dynamic.sql.delete;

import java.util.Objects;
import java.util.function.Function;

import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;

/**
 * This adapter will render the underlying delete model for MyBatis3, and then call a MyBatis mapper method.
 * 
 * @author Jeff Butler
 *
 */
public class MyBatis3DeleteModelAdapter<R> {

    private DeleteModel deleteModel;
    private Function<DeleteStatementProvider, R> mapperMethod;
    
    private MyBatis3DeleteModelAdapter(DeleteModel deleteModel, Function<DeleteStatementProvider, R> mapperMethod) {
        this.deleteModel = Objects.requireNonNull(deleteModel);
        this.mapperMethod = Objects.requireNonNull(mapperMethod);
    }
    
    public R execute() {
        return mapperMethod.apply(deleteStatement());
    }
    
    private DeleteStatementProvider deleteStatement() {
        return deleteModel.render(RenderingStrategy.MYBATIS3);
    }
    
    public static <R> MyBatis3DeleteModelAdapter<R> of(DeleteModel deleteModel,
            Function<DeleteStatementProvider, R> mapperMethod) {
        return new MyBatis3DeleteModelAdapter<>(deleteModel, mapperMethod);
    }
}
