
# 介绍
    这个库是生成动态 SQL 语句的框架。可把它看作是一个类型安全的 SQL 模板库，另外还支持 MyBatis3 和 Spring JDBC 模板。
    
    该库将生成格式化为由 MyBatis 或 Spring 使用的完整 DELETE，INSERT，SELECT 和 UPDATE 语句。
    
    最常见的用例是生成语句和一组匹配的参数，这些参数可以被 MyBatis 直接使用。该库还将生成与 Spring JDBC 模板兼容的语句和参数对象。
    
    该库通过实现一个类似 SQL 的 DSL 来创建一个对象，该对象包含完整的 SQL 语句以及该语句所需的任何参数。SQL 语句对象可以被 MyBatis 直接用作映射器方法的参数。
    


# 该库可以生成下列类型的 SQL 语句：
    
    
    含灵活的WHERE子句的DELETE语句
    
    几种类型的INSERT语句：
    
    插入单个记录并将空值插入列的语句（“完整”插入）
    
    插入单个记录的语句将忽略空值输入值及其相关列（“选择性”插入）
    
    使用SELECT语句的结果插入到表中的语句
    
    一个参数对象被设计为用JDBC批量插入多个对象
    
    SELECT语句具有灵活的列表，灵活的WHERE子句，支持不同的“group by”，连接，联合，“order by”等。
    
    带有灵活的WHERE子句的UPDATE语句。 像INSERT语句一样，有两种UPDATE语句：
    
    一个“完整的”更新，将设置空值
    
    “选择性”更新将忽略空输入值
    


    Maven 使用
    
    <dependency>
      <groupId>org.mybatis.dynamic-sql</groupId>
      <artifactId>mybatis-dynamic-sql</artifactId>
      <version>1.0.0</version>
    </dependency>