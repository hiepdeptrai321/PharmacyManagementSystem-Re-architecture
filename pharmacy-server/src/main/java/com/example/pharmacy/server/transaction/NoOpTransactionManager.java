package com.example.pharmacy.server.transaction;

import java.util.Objects;

public class NoOpTransactionManager implements TransactionManager {
    @Override
    public <T> T execute(TransactionCallback<T> work) {
        try {
            return Objects.requireNonNull(work, "work must not be null").doInTransaction();
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new IllegalStateException("Transaction callback failed.", exception);
        }
    }
}
