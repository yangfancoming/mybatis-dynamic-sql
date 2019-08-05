
package org.mybatis.dynamic.sql.delete;

import java.util.Objects;
import java.util.function.Function;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.SqlCriterion;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.VisitableCondition;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.util.Buildable;
import org.mybatis.dynamic.sql.where.AbstractWhereDSL;

public class DeleteDSL<R> implements Buildable<R> {

    private Function<DeleteModel, R> adapterFunction;
    private SqlTable table;
    
    private DeleteDSL(SqlTable table, Function<DeleteModel, R> adapterFunction) {
        this.table = Objects.requireNonNull(table);
        this.adapterFunction = Objects.requireNonNull(adapterFunction);
    }
    
    public DeleteWhereBuilder where() {
        return new DeleteWhereBuilder();
    }
    
    public <T> DeleteWhereBuilder where(BindableColumn<T> column, VisitableCondition<T> condition) {
        return new DeleteWhereBuilder(column, condition);
    }
    
    public <T> DeleteWhereBuilder where(BindableColumn<T> column, VisitableCondition<T> condition,
            SqlCriterion<?>...subCriteria) {
        return new DeleteWhereBuilder(column, condition, subCriteria);
    }
    
    /**
     * WARNING! Calling this method could result in an delete statement that deletes
     * all rows in a table.
     * 
     * @return the model class
     */
    @Override
    public R build() {
        DeleteModel deleteModel = DeleteModel.withTable(table).build();
        return adapterFunction.apply(deleteModel);
    }
    
    public static <R> DeleteDSL<R> deleteFrom(Function<DeleteModel, R> adapterFunction, SqlTable table) {
        return new DeleteDSL<>(table, adapterFunction);
    }
    
    public static DeleteDSL<DeleteModel> deleteFrom(SqlTable table) {
        return deleteFrom(Function.identity(), table);
    }
    
    public static <T> DeleteDSL<MyBatis3DeleteModelAdapter<T>> deleteFromWithMapper(
            Function<DeleteStatementProvider, T> mapperMethod, SqlTable table) {
        return deleteFrom(deleteModel -> MyBatis3DeleteModelAdapter.of(deleteModel, mapperMethod), table);
    }
    
    public class DeleteWhereBuilder extends AbstractWhereDSL<DeleteWhereBuilder> implements Buildable<R> {
        
        private <T> DeleteWhereBuilder() {
            super();
        }
        
        private <T> DeleteWhereBuilder(BindableColumn<T> column, VisitableCondition<T> condition) {
            super(column, condition);
        }
        
        private <T> DeleteWhereBuilder(BindableColumn<T> column, VisitableCondition<T> condition,
                SqlCriterion<?>...subCriteria) {
            super(column, condition, subCriteria);
        }
        
        @Override
        public R build() {
            DeleteModel deleteModel = DeleteModel.withTable(table)
                    .withWhereModel(buildWhereModel())
                    .build();
            return adapterFunction.apply(deleteModel);
        }
        
        @Override
        protected DeleteWhereBuilder getThis() {
            return this;
        }
    }
}
