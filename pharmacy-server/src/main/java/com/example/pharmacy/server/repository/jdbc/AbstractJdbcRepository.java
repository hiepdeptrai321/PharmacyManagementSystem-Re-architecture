package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.server.config.JdbcConnectionProvider;

import java.sql.Connection;
import java.util.Objects;

public abstract class AbstractJdbcRepository {
    private final JdbcConnectionProvider connectionProvider;

    protected AbstractJdbcRepository(JdbcConnectionProvider connectionProvider) {
        this.connectionProvider = Objects.requireNonNull(connectionProvider, "connectionProvider must not be null");
    }

    protected Connection getConnection() throws Exception {
        return connectionProvider.getConnection();
    }

    protected void closeConnection(Connection connection) {
        connectionProvider.close(connection);
    }
}
