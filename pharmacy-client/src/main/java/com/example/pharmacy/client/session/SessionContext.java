package com.example.pharmacy.client.session;

import com.example.pharmacy.common.session.UserContext;

public final class SessionContext {
    // Temporary client-side session holder for the JavaFX monolith.
    // Do not reuse this static pattern on the future server process.
    private static UserContext currentUser;

    private SessionContext() {
    }

    public static void setCurrentUser(UserContext userContext) {
        currentUser = userContext;
    }

    public static UserContext getCurrentUser() {
        return currentUser;
    }

    public static UserContext requireCurrentUser() {
        if (currentUser == null) {
            throw new IllegalStateException("Phiên đăng nhập không tồn tại hoặc đã hết hạn.");
        }
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void clear() {
        currentUser = null;
    }
}
