
package org.mybatis.dynamic.sql;

@FunctionalInterface
public interface VisitableCondition<T> {
    <R> R accept(ConditionVisitor<T,R> visitor);

    /**
     * Subclasses can override this to inform the renderer if the condition should not be included
     * in the rendered SQL.  For example, IsEqualWhenPresent will not render if the value is null.
     * 
     * @return true if the condition should render.
     */
    default boolean shouldRender() {
        return true;
    }
}
