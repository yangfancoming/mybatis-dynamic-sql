
package org.mybatis.dynamic.sql.where;

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.SqlCriterion;
import org.mybatis.dynamic.sql.VisitableCondition;

public class WhereDSL extends AbstractWhereDSL<WhereDSL> {

    private WhereDSL() {
        super();
    }

    private <T> WhereDSL(BindableColumn<T> column, VisitableCondition<T> condition) {
        super(column, condition);
    }

    private <T> WhereDSL(BindableColumn<T> column, VisitableCondition<T> condition, SqlCriterion<?>... subCriteria) {
        super(column, condition, subCriteria);
    }

    @Override
    protected WhereDSL getThis() {
        return this;
    }

    public static WhereDSL where() {
        return new WhereDSL();
    }

    public static <T> WhereDSL where(BindableColumn<T> column, VisitableCondition<T> condition) {
        return new WhereDSL(column, condition);
    }

    public static <T> WhereDSL where(BindableColumn<T> column, VisitableCondition<T> condition,
            SqlCriterion<?>... subCriteria) {
        return new WhereDSL(column, condition, subCriteria);
    }

    public WhereModel build() {
        return super.buildWhereModel();
    }
}
