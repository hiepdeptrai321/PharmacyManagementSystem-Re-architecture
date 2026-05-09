package com.example.pharmacy.server.transaction;

import com.example.pharmacy.server.config.MariaDbConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class JdbcTransactionManager implements TransactionManager {
    private final MariaDbConnectionFactory connectionFactory;

    public JdbcTransactionManager(MariaDbConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory, "connectionFactory must not be null");
    }

    @Override
    public <T> T execute(TransactionCallback<T> work) {
        Objects.requireNonNull(work, "work must not be null");
        Connection existingConnection = TransactionConnectionContext.getCurrentConnection();
        if (existingConnection != null) {
            try {
                return work.doInTransaction();
            } catch (RuntimeException exception) {
                throw exception;
            } catch (Exception exception) {
                throw new IllegalStateException("Nested transaction callback failed.", exception);
            }
        }

        try (Connection connection = connectionFactory.openConnection()) {
            boolean originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            TransactionConnectionContext.bind(connection);
            try {
                T result = work.doInTransaction();
                connection.commit();
                return result;
            } catch (RuntimeException exception) {
                rollbackQuietly(connection);
                throw exception;
            } catch (Exception exception) {
                rollbackQuietly(connection);
                throw new IllegalStateException("Transaction failed.", exception);
            } finally {
                TransactionConnectionContext.clear();
                connection.setAutoCommit(originalAutoCommit);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not open or finalize MariaDB transaction.", exception);
        }
    }

    private void rollbackQuietly(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ignored) {
        }
    }
}
