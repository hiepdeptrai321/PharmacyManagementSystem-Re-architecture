package com.example.pharmacy.common.response;

import com.example.pharmacy.common.dto.UserDTO;

import java.io.Serial;
import java.io.Serializable;

public class LoginResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private boolean success;
    private String message;
    private String sessionId;
    private UserDTO user;

    public LoginResponse() {
    }

    public LoginResponse(boolean success, String message, String sessionId, UserDTO user) {
        this.success = success;
        this.message = message;
        this.sessionId = sessionId;
        this.user = user;
    }

    public static LoginResponse success(String sessionId, UserDTO user) {
        return new LoginResponse(true, "Dang nhap thanh cong.", sessionId, user);
    }

    public static LoginResponse failure(String message) {
        return new LoginResponse(false, message, null, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
