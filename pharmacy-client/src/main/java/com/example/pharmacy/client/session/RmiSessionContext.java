package com.example.pharmacy.client.session;

import com.example.pharmacy.common.dto.UserDTO;
import com.example.pharmacy.common.response.LoginResponse;

import java.util.Optional;

public class RmiSessionContext {
    private UserDTO currentUser;
    private String sessionId;

    public synchronized void establish(LoginResponse response) {
        if (response == null || !response.isSuccess() || response.getUser() == null) {
            clear();
            return;
        }
        this.currentUser = response.getUser();
        this.sessionId = response.getSessionId();
    }

    public synchronized Optional<UserDTO> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    public synchronized Optional<String> getSessionId() {
        return Optional.ofNullable(sessionId);
    }

    public synchronized boolean isAuthenticated() {
        return currentUser != null && sessionId != null && !sessionId.isBlank();
    }

    public synchronized void clear() {
        this.currentUser = null;
        this.sessionId = null;
    }
}
