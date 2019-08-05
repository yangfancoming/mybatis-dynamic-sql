
package org.mybatis.dynamic.sql;

import java.sql.JDBCType;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class SqlTable {
    
    private Supplier<String> nameSupplier;

    protected SqlTable(String tableName) {
        Objects.requireNonNull(tableName);
        
        this.nameSupplier = () -> tableName;
    }

    protected SqlTable(Supplier<String> tableNameSupplier) {
        Objects.requireNonNull(tableNameSupplier);
        
        this.nameSupplier = tableNameSupplier;
    }

    protected SqlTable(Supplier<Optional<String>> schemaSupplier, String tableName) {
        this(Optional::empty, schemaSupplier, tableName);
    }
    
    protected SqlTable(Supplier<Optional<String>> catalogSupplier, Supplier<Optional<String>> schemaSupplier,
            String tableName) {
        Objects.requireNonNull(catalogSupplier);
        Objects.requireNonNull(schemaSupplier);
        Objects.requireNonNull(tableName);
        
        this.nameSupplier = () -> compose(catalogSupplier, schemaSupplier, tableName);
    }
    
    private String compose(Supplier<Optional<String>> catalogSupplier, Supplier<Optional<String>> schemaSupplier,
            String tableName) {
        return catalogSupplier.get().map(c -> compose(c, schemaSupplier, tableName))
                .orElseGet(() -> compose(schemaSupplier, tableName));
    }
    
    private String compose(String catalog, Supplier<Optional<String>> schemaSupplier, String tableName) {
        return schemaSupplier.get().map(s -> composeCatalogSchemaAndAndTable(catalog, s, tableName))
                .orElseGet(() -> composeCatalogAndTable(catalog, tableName));
    }

    private String compose(Supplier<Optional<String>> schemaSupplier, String tableName) {
        return schemaSupplier.get().map(s -> composeSchemaAndTable(s, tableName))
                .orElse(tableName);
    }
    
    private String composeCatalogAndTable(String catalog, String tableName) {
        return catalog + ".." + tableName; //$NON-NLS-1$
    }

    private String composeSchemaAndTable(String schema, String tableName) {
        return schema + "." + tableName; //$NON-NLS-1$
    }

    private String composeCatalogSchemaAndAndTable(String catalog, String schema, String tableName) {
        return catalog + "." + schema + "." + tableName; //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public String tableNameAtRuntime() {
        return nameSupplier.get();
    }
    
    public <T> SqlColumn<T> allColumns() {
        return SqlColumn.of("*", this); //$NON-NLS-1$
    }
    
    public <T> SqlColumn<T> column(String name) {
        return SqlColumn.of(name, this);
    }

    public <T> SqlColumn<T> column(String name, JDBCType jdbcType) {
        return SqlColumn.of(name, this, jdbcType);
    }

    public <T> SqlColumn<T> column(String name, JDBCType jdbcType, String typeHandler) {
        return SqlColumn.of(name, this, jdbcType).withTypeHandler(typeHandler);
    }
    
    public static SqlTable of(String name) {
        return new SqlTable(name);
    }
}
