package com.example.pharmacy.server.transaction;

@FunctionalInterface
public interface TransactionCallback<T> {
    T doInTransaction() throws Exception;
}
