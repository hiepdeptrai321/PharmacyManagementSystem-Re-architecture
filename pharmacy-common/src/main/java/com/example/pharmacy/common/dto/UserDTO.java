package com.example.pharmacy.common.dto;

import com.example.pharmacy.common.enums.UserRole;

import java.io.Serial;
import java.io.Serializable;

public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String employeeId;
    private String username;
    private String displayName;
    private UserRole role;
    private boolean active;

    public UserDTO() {
    }

    public UserDTO(String employeeId, String username, String displayName, UserRole role, boolean active) {
        this.employeeId = employeeId;
        this.username = username;
        this.displayName = displayName;
        this.role = role;
        this.active = active;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
