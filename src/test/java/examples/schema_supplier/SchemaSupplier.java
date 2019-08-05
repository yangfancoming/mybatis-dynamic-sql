
package examples.schema_supplier;

import java.util.Optional;

public class SchemaSupplier {
    public static final String schema_property = "schemaToUse";

    public static Optional<String> schemaPropertyReader() {
        return Optional.ofNullable(System.getProperty(schema_property));
    }
}
