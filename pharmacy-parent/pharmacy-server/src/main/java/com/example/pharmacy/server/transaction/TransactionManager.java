package com.example.pharmacy.server.transaction;

public interface TransactionManager {
    <T> T execute(TransactionCallback<T> work);
}
