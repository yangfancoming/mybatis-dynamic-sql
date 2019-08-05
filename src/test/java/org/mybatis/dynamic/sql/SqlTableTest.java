
package org.mybatis.dynamic.sql;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public class SqlTableTest {
    
    private static final String NAME_PROPERTY = "nameProperty";

    @Test
    public void testFullName() {
        SqlTable table = new SqlTable("my_table");
        assertThat(table.tableNameAtRuntime()).isEqualTo("my_table");
    }

    @Test
    public void testFullNameSupplier() {
        
        System.setProperty(NAME_PROPERTY, "my_table");
        SqlTable table = new SqlTable(SqlTableTest::namePropertyReader);
        assertThat(table.tableNameAtRuntime()).isEqualTo("my_table");
        System.clearProperty(NAME_PROPERTY);
    }

    @Test
    public void testSchemaSupplierEmpty() {
        SqlTable table = new SqlTable(Optional::empty, "my_table");
        assertThat(table.tableNameAtRuntime()).isEqualTo("my_table");
    }

    @Test
    public void testSchemaSupplierWithValue() {
        SqlTable table = new SqlTable(() -> Optional.of("my_schema"), "my_table");
        assertThat(table.tableNameAtRuntime()).isEqualTo("my_schema.my_table");
    }
    
    @Test
    public void testSingletonSchemaSupplier() {
        SqlTable table = new SqlTable(MySchemaSupplier.instance(), "my_table");
        assertThat(table.tableNameAtRuntime()).isEqualTo("first_schema.my_table");
    }

    @Test
    public void testThatSchemaSupplierDoesDelay() {
        MySchemaSupplier schemaSupplier = new MySchemaSupplier();
        SqlTable table = new SqlTable(schemaSupplier, "my_table");
        assertThat(table.tableNameAtRuntime()).isEqualTo("first_schema.my_table");
        
        schemaSupplier.setFirst(false);
        assertThat(table.tableNameAtRuntime()).isEqualTo("second_schema.my_table");
    }
    
    @Test
    public void testCatalogAndSchemaSupplierEmpty() {
        SqlTable table = new SqlTable(Optional::empty, Optional::empty, "my_table");
        assertThat(table.tableNameAtRuntime()).isEqualTo("my_table");
    }

    @Test
    public void testCatalogSupplierWithValue() {
        SqlTable table = new SqlTable(() -> Optional.of("my_catalog"), Optional::empty, "my_table");
        assertThat(table.tableNameAtRuntime()).isEqualTo("my_catalog..my_table");
    }
    
    @Test
    public void testThatCatalogSupplierDoesDelay() {
        MyCatalogSupplier catalogSupplier = new MyCatalogSupplier();
        SqlTable table = new SqlTable(catalogSupplier, Optional::empty, "my_table");
        assertThat(table.tableNameAtRuntime()).isEqualTo("first_catalog..my_table");
        
        catalogSupplier.setFirst(false);
        assertThat(table.tableNameAtRuntime()).isEqualTo("second_catalog..my_table");
    }
    
    @Test
    public void testThatCatalogSupplierAndSchemaSupplierBothDelay() {
        MyCatalogSupplier catalogSupplier = new MyCatalogSupplier();
        MySchemaSupplier schemaSupplier = new MySchemaSupplier();
        SqlTable table = new SqlTable(catalogSupplier, schemaSupplier, "my_table");
        assertThat(table.tableNameAtRuntime()).isEqualTo("first_catalog.first_schema.my_table");
        
        catalogSupplier.setFirst(false);
        assertThat(table.tableNameAtRuntime()).isEqualTo("second_catalog.first_schema.my_table");
        
        catalogSupplier.setFirst(true);
        schemaSupplier.setFirst(false);
        assertThat(table.tableNameAtRuntime()).isEqualTo("first_catalog.second_schema.my_table");
        
        catalogSupplier.setFirst(false);
        assertThat(table.tableNameAtRuntime()).isEqualTo("second_catalog.second_schema.my_table");
        
        catalogSupplier.setEmpty(true);
        assertThat(table.tableNameAtRuntime()).isEqualTo("second_schema.my_table");
        
        schemaSupplier.setEmpty(true);
        assertThat(table.tableNameAtRuntime()).isEqualTo("my_table");
        
        catalogSupplier.setEmpty(false);
        assertThat(table.tableNameAtRuntime()).isEqualTo("second_catalog..my_table");
    }
    
    private static String namePropertyReader() {
        return System.getProperty(NAME_PROPERTY);
    }
    
    public static class MySchemaSupplier implements Supplier<Optional<String>> {
        private static MySchemaSupplier instance = new MySchemaSupplier();
        
        public static MySchemaSupplier instance() {
            return instance;
        }
        
        private boolean first = true;
        private boolean empty;
        
        public void setFirst(boolean first) {
            this.first = first;
        }
        
        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        @Override
        public Optional<String> get() {
            if (empty) {
                return Optional.empty();
            }
            
            if (first) {
                return Optional.of("first_schema");
            } else {
                return Optional.of("second_schema");
            }
        }
    }

    public static class MyCatalogSupplier implements Supplier<Optional<String>> {
        private boolean first = true;
        private boolean empty;
        
        public void setFirst(boolean first) {
            this.first = first;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        @Override
        public Optional<String> get() {
            if (empty) {
                return Optional.empty();
            }
            
            if (first) {
                return Optional.of("first_catalog");
            } else {
                return Optional.of("second_catalog");
            }
        }
    }
}
