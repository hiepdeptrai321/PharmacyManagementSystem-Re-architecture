package com.example.pharmacy.server.config;

public final class DatabaseProperties {
    private final String url;
    private final String username;
    private final String password;

    public DatabaseProperties(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DatabaseProperties fromEnvironment() {
        String url = System.getProperty(
                "pharmacy.db.url",
                System.getenv().getOrDefault("PHARMACY_DB_URL", "jdbc:mariadb://localhost:3306/quan_ly_nha_thuoc")
        );
        String username = System.getProperty(
                "pharmacy.db.user",
                System.getenv().getOrDefault("PHARMACY_DB_USER", "root")
        );
        String password = System.getProperty(
                "pharmacy.db.password",
                System.getenv().getOrDefault("PHARMACY_DB_PASSWORD", "root")
        );
        return new DatabaseProperties(url, username, password);
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
