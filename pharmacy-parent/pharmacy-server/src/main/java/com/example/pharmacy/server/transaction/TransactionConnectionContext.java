package com.example.pharmacy.server.transaction;

import java.sql.Connection;

public final class TransactionConnectionContext {
    private static final ThreadLocal<Connection> CURRENT_CONNECTION = new ThreadLocal<>();

    private TransactionConnectionContext() {
    }

    public static Connection getCurrentConnection() {
        return CURRENT_CONNECTION.get();
    }

    public static void bind(Connection connection) {
        CURRENT_CONNECTION.set(connection);
    }

    public static void clear() {
        CURRENT_CONNECTION.remove();
    }
}
