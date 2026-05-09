package com.example.pharmacy.common.session;

import java.io.Serial;
import java.io.Serializable;

public class UserContext implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String userId;
    private final String username;
    private final String employeeId;
    private final String fullName;
    private final String role;

    public UserContext(String userId, String username, String employeeId, String fullName, String role) {
        this.userId = userId;
        this.username = username;
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }
}
