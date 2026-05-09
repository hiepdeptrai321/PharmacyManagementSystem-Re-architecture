package com.example.pharmacy.common.session;

import java.io.Serial;
import java.io.Serializable;

public class LoginResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final boolean success;
    private final String message;
    private final UserContext userContext;

    public LoginResult(boolean success, String message, UserContext userContext) {
        this.success = success;
        this.message = message;
        this.userContext = userContext;
    }

    public static LoginResult success(UserContext userContext) {
        return new LoginResult(true, "Đăng nhập thành công.", userContext);
    }

    public static LoginResult failure(String message) {
        return new LoginResult(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public UserContext getUserContext() {
        return userContext;
    }
}
