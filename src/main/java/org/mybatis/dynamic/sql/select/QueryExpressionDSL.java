
package org.mybatis.dynamic.sql.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlCriterion;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.VisitableCondition;
import org.mybatis.dynamic.sql.select.join.JoinCondition;
import org.mybatis.dynamic.sql.select.join.JoinCriterion;
import org.mybatis.dynamic.sql.select.join.JoinModel;
import org.mybatis.dynamic.sql.select.join.JoinSpecification;
import org.mybatis.dynamic.sql.select.join.JoinType;
import org.mybatis.dynamic.sql.util.Buildable;
import org.mybatis.dynamic.sql.where.AbstractWhereDSL;
import org.mybatis.dynamic.sql.where.WhereModel;

public class QueryExpressionDSL<R> implements Buildable<R> {

    private String connector;
    private SelectDSL<R> selectDSL;
    private boolean isDistinct;
    private List<BasicColumn> selectList;
    private SqlTable table;
    private Map<SqlTable, String> tableAliases = new HashMap<>();
    private WhereModel whereModel;
    private GroupByModel groupByModel;
    private JoinModel joinModel;
    private List<JoinSpecification> joinSpecifications = new ArrayList<>();
    private Supplier<R> buildDelegateMethod;
    
    private QueryExpressionDSL(FromGatherer<R> fromGatherer) {
        connector = fromGatherer.connector;
        selectList = Arrays.asList(fromGatherer.selectList);
        isDistinct = fromGatherer.isDistinct;
        selectDSL = Objects.requireNonNull(fromGatherer.selectDSL);
        table = Objects.requireNonNull(fromGatherer.table);
        buildDelegateMethod = this::internalBuild;
    }
    
    private QueryExpressionDSL(FromGatherer<R> fromGatherer, String tableAlias) {
        this(fromGatherer);
        tableAliases.put(table, tableAlias);
    }
    
    public static <R> FromGatherer<R> select(SelectDSL<R> selectDSL, BasicColumn...selectList) {
        return new FromGatherer.Builder<R>()
                .withSelectList(selectList)
                .withSelectDSL(selectDSL)
                .build();
    }
    
    public static <R> FromGatherer<R> selectDistinct(SelectDSL<R> selectDSL, BasicColumn...selectList) {
        return new FromGatherer.Builder<R>()
                .withSelectList(selectList)
                .withSelectDSL(selectDSL)
                .isDistinct()
                .build();
    }
    
    public QueryExpressionWhereBuilder where() {
        return new QueryExpressionWhereBuilder();
    }

    public <T> QueryExpressionWhereBuilder where(BindableColumn<T> column, VisitableCondition<T> condition) {
        return new QueryExpressionWhereBuilder(column, condition);
    }

    public <T> QueryExpressionWhereBuilder where(BindableColumn<T> column, VisitableCondition<T> condition,
            SqlCriterion<?>...subCriteria) {
        return new QueryExpressionWhereBuilder(column, condition, subCriteria);
    }
    
    @Override
    public R build() {
        return buildDelegateMethod.get();
    }

    private R internalBuild() {
        selectDSL.addQueryExpression(buildModel());
        return selectDSL.build();
    }

    public JoinSpecificationStarter join(SqlTable joinTable) {
        return new JoinSpecificationStarter(joinTable, JoinType.INNER);
    }
    
    public JoinSpecificationStarter join(SqlTable joinTable, String tableAlias) {
        tableAliases.put(joinTable, tableAlias);
        return join(joinTable);
    }

    public JoinSpecificationStarter leftJoin(SqlTable joinTable) {
        return new JoinSpecificationStarter(joinTable, JoinType.LEFT);
    }
    
    public JoinSpecificationStarter leftJoin(SqlTable joinTable, String tableAlias) {
        tableAliases.put(joinTable, tableAlias);
        return leftJoin(joinTable);
    }

    public JoinSpecificationStarter rightJoin(SqlTable joinTable) {
        return new JoinSpecificationStarter(joinTable, JoinType.RIGHT);
    }
    
    public JoinSpecificationStarter rightJoin(SqlTable joinTable, String tableAlias) {
        tableAliases.put(joinTable, tableAlias);
        return rightJoin(joinTable);
    }

    public JoinSpecificationStarter fullJoin(SqlTable joinTable) {
        return new JoinSpecificationStarter(joinTable, JoinType.FULL);
    }
    
    public JoinSpecificationStarter fullJoin(SqlTable joinTable, String tableAlias) {
        tableAliases.put(joinTable, tableAlias);
        return fullJoin(joinTable);
    }

    public GroupByFinisher groupBy(BasicColumn...columns) {
        groupByModel = GroupByModel.of(columns);
        selectDSL.addQueryExpression(buildModel());
        return new GroupByFinisher();
    }
    
    public SelectDSL<R> orderBy(SortSpecification...columns) {
        buildDelegateMethod = selectDSL::build;
        selectDSL.addQueryExpression(buildModel());
        selectDSL.setOrderByModel(OrderByModel.of(columns));
        return selectDSL;
    }

    public UnionBuilder union() {
        selectDSL.addQueryExpression(buildModel());
        return new UnionBuilder("union"); //$NON-NLS-1$
    }

    public UnionBuilder unionAll() {
        selectDSL.addQueryExpression(buildModel());
        return new UnionBuilder("union all"); //$NON-NLS-1$
    }

    protected QueryExpressionModel buildModel() {
        return QueryExpressionModel.withSelectList(selectList)
                .withConnector(connector)
                .withTable(table)
                .isDistinct(isDistinct)
                .withTableAliases(tableAliases)
                .withWhereModel(whereModel)
                .withJoinModel(joinModel)
                .withGroupByModel(groupByModel)
                .build();
    }
    
    public SelectDSL<R>.LimitFinisher limit(long limit) {
        buildDelegateMethod = selectDSL::build;
        selectDSL.addQueryExpression(buildModel());
        return selectDSL.limit(limit);
    }

    public SelectDSL<R>.OffsetFirstFinisher offset(long offset) {
        buildDelegateMethod = selectDSL::build;
        selectDSL.addQueryExpression(buildModel());
        return selectDSL.offset(offset);
    }

    public SelectDSL<R>.FetchFirstFinisher fetchFirst(long fetchFirstRows) {
        buildDelegateMethod = selectDSL::build;
        selectDSL.addQueryExpression(buildModel());
        return selectDSL.fetchFirst(fetchFirstRows);
    }
    
    public static class FromGatherer<R> {
        private String connector;
        private BasicColumn[] selectList;
        private SelectDSL<R> selectDSL;
        private boolean isDistinct;
        private SqlTable table;
        private Optional<QueryExpressionDSL<R>> priorQuery;
        
        public FromGatherer(Builder<R> builder) {
            this.connector = builder.connector;
            this.selectList = Objects.requireNonNull(builder.selectList);
            this.selectDSL = Objects.requireNonNull(builder.selectDSL);
            this.isDistinct = builder.isDistinct;
            this.priorQuery = Optional.ofNullable(builder.priorQuery);
        }
        
        public QueryExpressionDSL<R> from(SqlTable table) {
            this.table = table;
            return setPriorBuildDelegate(new QueryExpressionDSL<>(this));
        }

        public QueryExpressionDSL<R> from(SqlTable table, String tableAlias) {
            this.table = table;
            return setPriorBuildDelegate(new QueryExpressionDSL<>(this, tableAlias));
        }
        
        private QueryExpressionDSL<R> setPriorBuildDelegate(QueryExpressionDSL<R> newQuery) {
            priorQuery.ifPresent(pq -> pq.buildDelegateMethod = newQuery::build);
            return newQuery;
        }
        
        public static class Builder<R> {
            private String connector;
            private BasicColumn[] selectList;
            private SelectDSL<R> selectDSL;
            private boolean isDistinct;
            private QueryExpressionDSL<R> priorQuery;
            
            public Builder<R> withConnector(String connector) {
                this.connector = connector;
                return this;
            }

            public Builder<R> withSelectList(BasicColumn[] selectList) {
                this.selectList = selectList;
                return this;
            }

            public Builder<R> withSelectDSL(SelectDSL<R> selectDSL) {
                this.selectDSL = selectDSL;
                return this;
            }
            
            public Builder<R> isDistinct() {
                this.isDistinct = true;
                return this;
            }
            
            public Builder<R> withPriorQuery(QueryExpressionDSL<R> priorQuery) {
                this.priorQuery = priorQuery;
                return this;
            }
            
            public FromGatherer<R> build() {
                return new FromGatherer<>(this);
            }
        }
    }
    
    public class QueryExpressionWhereBuilder extends AbstractWhereDSL<QueryExpressionWhereBuilder>
            implements Buildable<R> {
        private <T> QueryExpressionWhereBuilder() {
            buildDelegateMethod = this::internalBuild;
        }

        private <T> QueryExpressionWhereBuilder(BindableColumn<T> column, VisitableCondition<T> condition) {
            super(column, condition);
            buildDelegateMethod = this::internalBuild;
        }
        
        private <T> QueryExpressionWhereBuilder(BindableColumn<T> column, VisitableCondition<T> condition,
                SqlCriterion<?>...subCriteria) {
            super(column, condition, subCriteria);
            buildDelegateMethod = this::internalBuild;
        }
        
        public UnionBuilder union() {
            whereModel = buildWhereModel();
            selectDSL.addQueryExpression(buildModel());
            return new UnionBuilder("union"); //$NON-NLS-1$
        }

        public UnionBuilder unionAll() {
            whereModel = buildWhereModel();
            selectDSL.addQueryExpression(buildModel());
            return new UnionBuilder("union all"); //$NON-NLS-1$
        }

        public SelectDSL<R> orderBy(SortSpecification...columns) {
            buildDelegateMethod = selectDSL::build;
            whereModel = buildWhereModel();
            selectDSL.addQueryExpression(buildModel());
            selectDSL.setOrderByModel(OrderByModel.of(columns));
            return selectDSL;
        }
        
        public GroupByFinisher groupBy(BasicColumn...columns) {
            groupByModel = GroupByModel.of(columns);
            whereModel = buildWhereModel();
            selectDSL.addQueryExpression(buildModel());
            return new GroupByFinisher();
        }
        
        public SelectDSL<R>.LimitFinisher limit(long limit) {
            buildDelegateMethod = selectDSL::build;
            whereModel = buildWhereModel();
            selectDSL.addQueryExpression(buildModel());
            return selectDSL.limit(limit);
        }
        
        public SelectDSL<R>.OffsetFirstFinisher offset(long offset) {
            buildDelegateMethod = selectDSL::build;
            whereModel = buildWhereModel();
            selectDSL.addQueryExpression(buildModel());
            return selectDSL.offset(offset);
        }
        
        public SelectDSL<R>.FetchFirstFinisher fetchFirst(long fetchFirstRows) {
            buildDelegateMethod = selectDSL::build;
            whereModel = buildWhereModel();
            selectDSL.addQueryExpression(buildModel());
            return selectDSL.fetchFirst(fetchFirstRows);
        }
        
        @Override
        public R build() {
            return buildDelegateMethod.get();
        }
        
        private R internalBuild() {
            whereModel = buildWhereModel();
            selectDSL.addQueryExpression(buildModel());
            return selectDSL.build();
        }
        
        @Override
        protected QueryExpressionWhereBuilder getThis() {
            return this;
        }
    }
    
    public class JoinSpecificationStarter {
        private SqlTable joinTable;
        private JoinType joinType;
        
        public JoinSpecificationStarter(SqlTable joinTable, JoinType joinType) {
            this.joinTable = joinTable;
            this.joinType = joinType;
        }

        public JoinSpecificationFinisher on(BasicColumn joinColumn, JoinCondition joinCondition) {
            return new JoinSpecificationFinisher(joinTable, joinColumn, joinCondition, joinType);
        }

        public JoinSpecificationFinisher on(BasicColumn joinColumn, JoinCondition joinCondition,
                JoinCriterion...joinCriteria) {
            return new JoinSpecificationFinisher(joinTable, joinColumn, joinCondition, joinType, joinCriteria);
        }
    }

    public class JoinSpecificationFinisher implements Buildable<R> {
        private SqlTable joinTable;
        private List<JoinCriterion> joinCriteria = new ArrayList<>();
        private JoinType joinType;
        
        public JoinSpecificationFinisher(SqlTable table, BasicColumn joinColumn,
                JoinCondition joinCondition, JoinType joinType) {
            this.joinTable = table;
            this.joinType = joinType;

            JoinCriterion joinCriterion = JoinCriterion.withJoinColumn(joinColumn)
                    .withJoinCondition(joinCondition)
                    .withConnector("on") //$NON-NLS-1$
                    .build();
            
            joinCriteria.add(joinCriterion);
            buildDelegateMethod = this::internalbuild;
        }

        public JoinSpecificationFinisher(SqlTable table, BasicColumn joinColumn,
                JoinCondition joinCondition, JoinType joinType, JoinCriterion...joinCriteria) {
            this.joinTable = table;
            this.joinType = joinType;

            JoinCriterion joinCriterion = JoinCriterion.withJoinColumn(joinColumn)
                    .withJoinCondition(joinCondition)
                    .withConnector("on") //$NON-NLS-1$
                    .build();
            
            this.joinCriteria.add(joinCriterion);
            this.joinCriteria.addAll(Arrays.asList(joinCriteria));
            buildDelegateMethod = this::internalbuild;
        }
        
        protected JoinSpecification buildJoinSpecification() {
            return JoinSpecification.withJoinTable(joinTable)
                    .withJoinCriteria(joinCriteria)
                    .withJoinType(joinType)
                    .build();
        }
        
        protected JoinModel buildJoinModel() {
            joinSpecifications.add(buildJoinSpecification());
            return JoinModel.of(joinSpecifications);
        }
        
        @Override
        public R build() {
            return buildDelegateMethod.get();
        }
        
        private R internalbuild() {
            joinModel = buildJoinModel();
            selectDSL.addQueryExpression(buildModel());
            return selectDSL.build();
        }
        
        public QueryExpressionWhereBuilder where() {
            joinModel = buildJoinModel();
            return new QueryExpressionWhereBuilder();
        }
        
        public <T> QueryExpressionWhereBuilder where(BindableColumn<T> column, VisitableCondition<T> condition) {
            joinModel = buildJoinModel();
            return new QueryExpressionWhereBuilder(column, condition);
        }

        public <T> QueryExpressionWhereBuilder where(BindableColumn<T> column, VisitableCondition<T> condition,
                SqlCriterion<?>...subCriteria) {
            joinModel = buildJoinModel();
            return new QueryExpressionWhereBuilder(column, condition, subCriteria);
        }

        public JoinSpecificationFinisher and(BasicColumn joinColumn, JoinCondition joinCondition) {
            JoinCriterion joinCriterion = JoinCriterion.withJoinColumn(joinColumn)
                    .withJoinCondition(joinCondition)
                    .withConnector("and") //$NON-NLS-1$
                    .build();
            this.joinCriteria.add(joinCriterion);
            return this;
        }

        public JoinSpecificationStarter join(SqlTable joinTable) {
            joinSpecifications.add(buildJoinSpecification());
            return new JoinSpecificationStarter(joinTable, JoinType.INNER);
        }
        
        public JoinSpecificationStarter join(SqlTable joinTable, String tableAlias) {
            tableAliases.put(joinTable, tableAlias);
            return join(joinTable);
        }

        public JoinSpecificationStarter leftJoin(SqlTable joinTable) {
            joinSpecifications.add(buildJoinSpecification());
            return new JoinSpecificationStarter(joinTable, JoinType.LEFT);
        }
        
        public JoinSpecificationStarter leftJoin(SqlTable joinTable, String tableAlias) {
            tableAliases.put(joinTable, tableAlias);
            return leftJoin(joinTable);
        }

        public JoinSpecificationStarter rightJoin(SqlTable joinTable) {
            joinSpecifications.add(buildJoinSpecification());
            return new JoinSpecificationStarter(joinTable, JoinType.RIGHT);
        }
        
        public JoinSpecificationStarter rightJoin(SqlTable joinTable, String tableAlias) {
            tableAliases.put(joinTable, tableAlias);
            return rightJoin(joinTable);
        }

        public JoinSpecificationStarter fullJoin(SqlTable joinTable) {
            joinSpecifications.add(buildJoinSpecification());
            return new JoinSpecificationStarter(joinTable, JoinType.FULL);
        }
        
        public JoinSpecificationStarter fullJoin(SqlTable joinTable, String tableAlias) {
            tableAliases.put(joinTable, tableAlias);
            return fullJoin(joinTable);
        }

        public SelectDSL<R> orderBy(SortSpecification...columns) {
            buildDelegateMethod = selectDSL::build;
            joinModel = buildJoinModel();
            selectDSL.addQueryExpression(buildModel());
            selectDSL.setOrderByModel(OrderByModel.of(columns));
            return selectDSL;
        }

        public SelectDSL<R>.LimitFinisher limit(long limit) {
            buildDelegateMethod = selectDSL::build;
            joinModel = buildJoinModel();
            selectDSL.addQueryExpression(buildModel());
            return selectDSL.limit(limit);
        }

        public SelectDSL<R>.OffsetFirstFinisher offset(long offset) {
            buildDelegateMethod = selectDSL::build;
            joinModel = buildJoinModel();
            selectDSL.addQueryExpression(buildModel());
            return selectDSL.offset(offset);
        }

        public SelectDSL<R>.FetchFirstFinisher fetchFirst(long fetchFirstRows) {
            buildDelegateMethod = selectDSL::build;
            joinModel = buildJoinModel();
            selectDSL.addQueryExpression(buildModel());
            return selectDSL.fetchFirst(fetchFirstRows);
        }
    }
    
    public class GroupByFinisher implements Buildable<R> {
        public GroupByFinisher() {
            buildDelegateMethod = this::internalBuild;
        }
        
        public SelectDSL<R> orderBy(SortSpecification...columns) {
            buildDelegateMethod = selectDSL::build;
            selectDSL.setOrderByModel(OrderByModel.of(columns));
            return selectDSL;
        }

        @Override
        public R build() {
            return buildDelegateMethod.get();
        }
        
        private R internalBuild() {
            return selectDSL.build();
        }

        public SelectDSL<R>.LimitFinisher limit(long limit) {
            buildDelegateMethod = selectDSL::build;
            return selectDSL.limit(limit);
        }

        public SelectDSL<R>.OffsetFirstFinisher offset(long offset) {
            buildDelegateMethod = selectDSL::build;
            return selectDSL.offset(offset);
        }

        public SelectDSL<R>.FetchFirstFinisher fetchFirst(long fetchFirstRows) {
            buildDelegateMethod = selectDSL::build;
            return selectDSL.fetchFirst(fetchFirstRows);
        }
    }
    
    public class UnionBuilder {
        private String connector;
        
        public UnionBuilder(String connector) {
            this.connector = Objects.requireNonNull(connector);
        }
        
        public FromGatherer<R> select(BasicColumn...selectList) {
            return new FromGatherer.Builder<R>()
                    .withConnector(connector)
                    .withSelectList(selectList)
                    .withSelectDSL(selectDSL)
                    .withPriorQuery(QueryExpressionDSL.this)
                    .build();
        }

        public FromGatherer<R> selectDistinct(BasicColumn...selectList) {
            return new FromGatherer.Builder<R>()
                    .withConnector(connector)
                    .withSelectList(selectList)
                    .withSelectDSL(selectDSL)
                    .isDistinct()
                    .withPriorQuery(QueryExpressionDSL.this)
                    .build();
        }
    }
}
