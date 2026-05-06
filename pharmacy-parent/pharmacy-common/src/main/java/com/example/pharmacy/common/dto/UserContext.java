package com.example.pharmacy.common.dto;

import com.example.pharmacy.common.enums.UserRole;

import java.io.Serial;
import java.io.Serializable;

public class UserContext implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;
    private String username;
    private String employeeId;
    private String fullName;
    private UserRole role;

    public UserContext() {
    }

    public UserContext(String userId, String username, String employeeId, String fullName, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
