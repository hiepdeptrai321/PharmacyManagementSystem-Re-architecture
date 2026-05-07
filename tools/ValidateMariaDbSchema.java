import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class ValidateMariaDbSchema {
    private static final String DEFAULT_JDBC_URL = "jdbc:mariadb://127.0.0.1:3306/quan_ly_nha_thuoc";
    private static final String DEFAULT_USER = "pharmacy_app";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String ORIGINAL_DB_NAME = "quan_ly_nha_thuoc";
    private static final String TEMP_DB_NAME = "quan_ly_nha_thuoc_schema_check";

    private ValidateMariaDbSchema() {
    }

    public static void main(String[] args) throws Exception {
        String jdbcUrl = resolve("pharmacy.db.url", "PHARMACY_DB_URL", DEFAULT_JDBC_URL);
        String baseUrl = deriveBaseUrl(jdbcUrl);
        String user = resolve("pharmacy.db.user", "PHARMACY_DB_USER", DEFAULT_USER);
        String password = resolve("pharmacy.db.password", "PHARMACY_DB_PASSWORD", DEFAULT_PASSWORD);
        Path schemaPath = Path.of("SQL", "schema_mariadb.sql");

        String schemaSql = Files.readString(schemaPath, StandardCharsets.UTF_8)
                .replace(ORIGINAL_DB_NAME, TEMP_DB_NAME);

        System.out.println("[ValidateMariaDbSchema] Using JDBC base URL: " + baseUrl);
        System.out.println("[ValidateMariaDbSchema] Using JDBC user: " + user);

        try (Connection connection = DriverManager.getConnection(baseUrl, user, password)) {
            recreateDatabase(connection);
            executeSqlScript(connection, schemaSql);
            verifyHoatDong(connection);
        } catch (SQLException exception) {
            throw new SQLException(
                    "Cannot validate MariaDB schema. Check MariaDB service, database user/password, TCP access to 127.0.0.1, and authentication plugin.",
                    exception
            );
        } finally {
            try (Connection connection = DriverManager.getConnection(baseUrl, user, password);
                 Statement stmt = connection.createStatement()) {
                stmt.execute("DROP DATABASE IF EXISTS `" + TEMP_DB_NAME + "`");
            } catch (SQLException ignored) {
            }
        }
    }

    private static String resolve(String systemPropertyName, String environmentVariableName, String defaultValue) {
        String fromSystemProperty = normalize(System.getProperty(systemPropertyName));
        if (fromSystemProperty != null) {
            return fromSystemProperty;
        }
        String fromEnvironment = normalize(System.getenv(environmentVariableName));
        return fromEnvironment != null ? fromEnvironment : defaultValue;
    }

    private static String deriveBaseUrl(String jdbcUrl) {
        int protocolIndex = jdbcUrl.indexOf("://");
        int slashIndex = jdbcUrl.indexOf('/', protocolIndex >= 0 ? protocolIndex + 3 : 0);
        if (slashIndex < 0) {
            return jdbcUrl.endsWith("/") ? jdbcUrl : jdbcUrl + "/";
        }
        return jdbcUrl.substring(0, slashIndex + 1);
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private static void recreateDatabase(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP DATABASE IF EXISTS `" + TEMP_DB_NAME + "`");
            stmt.execute("CREATE DATABASE `" + TEMP_DB_NAME + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
        }
    }

    private static void executeSqlScript(Connection connection, String script) throws SQLException, IOException {
        StringBuilder statement = new StringBuilder();
        for (String line : script.split("\\R")) {
            String trimmed = line.trim();
            if (trimmed.startsWith("--") || trimmed.isEmpty()) {
                continue;
            }
            statement.append(line).append(System.lineSeparator());
            if (trimmed.endsWith(";")) {
                executeStatement(connection, statement.toString());
                statement.setLength(0);
            }
        }
        if (statement.length() > 0) {
            executeStatement(connection, statement.toString());
        }
    }

    private static void executeStatement(Connection connection, String sql) throws SQLException {
        String normalized = sql.trim();
        if (normalized.endsWith(";")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(normalized);
        }
    }

    private static void verifyHoatDong(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("USE `" + TEMP_DB_NAME + "`");
            try (ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE `HoatDong`")) {
                if (!rs.next()) {
                    throw new SQLException("Khong tim thay bang HoatDong sau khi chay schema.");
                }
                String ddl = rs.getString(2);
                if (ddl.contains("GENERATED ALWAYS AS")) {
                    throw new SQLException("Bang HoatDong van con GENERATED ALWAYS AS.");
                }
                System.out.println("Schema executed successfully. HoatDong DDL:");
                System.out.println(ddl);
            }
        }
    }
}
