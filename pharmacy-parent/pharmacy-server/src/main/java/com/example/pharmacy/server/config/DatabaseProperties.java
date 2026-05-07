package com.example.pharmacy.server.config;

public final class DatabaseProperties {
    public static final String DEFAULT_URL = "jdbc:mariadb://127.0.0.1:3306/quan_ly_nha_thuoc";
    public static final String DEFAULT_USERNAME = "pharmacy_app";
    public static final String DEFAULT_PASSWORD = "123456";

    private final String url;
    private final String username;
    private final String password;

    public DatabaseProperties(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DatabaseProperties fromEnvironment() {
        String url = resolveWithDefault("pharmacy.db.url", "PHARMACY_DB_URL", DEFAULT_URL);
        String username = resolveWithDefault("pharmacy.db.user", "PHARMACY_DB_USER", DEFAULT_USERNAME);
        String password = resolveWithDefault("pharmacy.db.password", "PHARMACY_DB_PASSWORD", DEFAULT_PASSWORD);
        return new DatabaseProperties(url, username, password);
    }

    public static String resolveOverride(String systemPropertyName, String environmentVariableName) {
        String fromSystemProperty = normalize(System.getProperty(systemPropertyName));
        if (fromSystemProperty != null) {
            return fromSystemProperty;
        }
        return normalize(System.getenv(environmentVariableName));
    }

    private static String resolveWithDefault(String systemPropertyName, String environmentVariableName, String defaultValue) {
        String override = resolveOverride(systemPropertyName, environmentVariableName);
        return override != null ? override : defaultValue;
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
