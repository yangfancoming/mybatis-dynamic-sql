
package org.mybatis.dynamic.sql;

/**
 * Defines attributes of columns that are necessary for rendering an order by expression.
 * 
 * @author Jeff Butler
 *
 */
public interface SortSpecification {
    /**
     * Returns a new instance of the SortSpecification that should render as descending in an
     * ORDER BY clause.
     * 
     * @return new instance of SortSpecification
     */
    SortSpecification descending();

    /**
     * Return the column alias or column name.
     * 
     * @return the column alias if one has been specified by the user, or else the column name
     */
    String aliasOrName();

    /**
     * Return true if the sort order is descending.
     * 
     * @return true if the SortSpcification should render as descending 
     */
    boolean isDescending();
}
