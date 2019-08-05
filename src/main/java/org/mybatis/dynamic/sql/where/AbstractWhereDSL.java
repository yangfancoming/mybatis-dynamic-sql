
package org.mybatis.dynamic.sql.where;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.SqlCriterion;
import org.mybatis.dynamic.sql.VisitableCondition;

public abstract class AbstractWhereDSL<T extends AbstractWhereDSL<T>> {
    private List<SqlCriterion<?>> criteria = new ArrayList<>();
    
    protected <S> AbstractWhereDSL() {
        super();
    }
    
    protected <S> AbstractWhereDSL(BindableColumn<S> column, VisitableCondition<S> condition) {
        SqlCriterion<S> criterion = SqlCriterion.withColumn(column)
                .withCondition(condition)
                .build();
        criteria.add(criterion);
    }
    
    protected <S> AbstractWhereDSL(BindableColumn<S> column, VisitableCondition<S> condition,
            SqlCriterion<?>...subCriteria) {
        SqlCriterion<S> criterion = SqlCriterion.withColumn(column)
                .withCondition(condition)
                .withSubCriteria(Arrays.asList(subCriteria))
                .build();
        criteria.add(criterion);
    }
    
    public <S> T and(BindableColumn<S> column, VisitableCondition<S> condition) {
        addCriterion("and", column, condition); //$NON-NLS-1$
        return getThis();
    }
    
    public <S> T and(BindableColumn<S> column, VisitableCondition<S> condition, SqlCriterion<?>...subCriteria) {
        addCriterion("and", column, condition, subCriteria); //$NON-NLS-1$
        return getThis();
    }
    
    public <S> T or(BindableColumn<S> column, VisitableCondition<S> condition) {
        addCriterion("or", column, condition); //$NON-NLS-1$
        return getThis();
    }
    
    public <S> T or(BindableColumn<S> column, VisitableCondition<S> condition, SqlCriterion<?>...subCriteria) {
        addCriterion("or", column, condition, subCriteria); //$NON-NLS-1$
        return getThis();
    }
    
    private <S> void addCriterion(String connector, BindableColumn<S> column, VisitableCondition<S> condition) {
        SqlCriterion<S> criterion = SqlCriterion.withColumn(column)
                .withConnector(connector)
                .withCondition(condition)
                .build();
        criteria.add(criterion);
    }
    
    private <S> void addCriterion(String connector, BindableColumn<S> column, VisitableCondition<S> condition,
            SqlCriterion<?>...subCriteria) {
        SqlCriterion<S> criterion = SqlCriterion.withColumn(column)
                .withConnector(connector)
                .withCondition(condition)
                .withSubCriteria(Arrays.asList(subCriteria))
                .build();
        criteria.add(criterion);
    }
    
    protected WhereModel buildWhereModel() {
        return WhereModel.of(criteria);
    }
    
    protected abstract T getThis();
}
