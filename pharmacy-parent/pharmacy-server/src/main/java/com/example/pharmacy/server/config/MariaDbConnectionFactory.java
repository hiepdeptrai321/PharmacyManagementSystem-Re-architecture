package com.example.pharmacy.server.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class MariaDbConnectionFactory {
    private final DatabaseProperties databaseProperties;

    public MariaDbConnectionFactory(DatabaseProperties databaseProperties) {
        this.databaseProperties = Objects.requireNonNull(databaseProperties, "databaseProperties must not be null");
    }

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                databaseProperties.getUrl(),
                databaseProperties.getUsername(),
                databaseProperties.getPassword()
        );
    }
}
