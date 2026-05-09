package com.example.pharmacy.server.config;

import com.example.pharmacy.server.transaction.TransactionConnectionContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class JdbcConnectionProvider {
    private final MariaDbConnectionFactory connectionFactory;

    public JdbcConnectionProvider(MariaDbConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory, "connectionFactory must not be null");
    }

    public Connection getConnection() throws SQLException {
        Connection transactionBoundConnection = TransactionConnectionContext.getCurrentConnection();
        if (transactionBoundConnection != null) {
            return transactionBoundConnection;
        }
        return connectionFactory.openConnection();
    }

    public void close(Connection connection) {
        Connection transactionBoundConnection = TransactionConnectionContext.getCurrentConnection();
        if (connection == null || connection == transactionBoundConnection) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }
}
