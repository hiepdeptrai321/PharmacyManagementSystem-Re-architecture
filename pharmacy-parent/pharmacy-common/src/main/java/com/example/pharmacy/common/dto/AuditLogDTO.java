package com.example.pharmacy.common.dto;

import com.example.pharmacy.common.enums.AuditAction;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class AuditLogDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String auditCode;
    private String userId;
    private String username;
    private String employeeId;
    private String fullName;
    private AuditAction action;
    private String entityName;
    private String entityId;
    private String description;
    private LocalDateTime createdAt;

    public AuditLogDTO() {
    }

    public AuditLogDTO(
            String auditCode,
            String userId,
            String username,
            String employeeId,
            String fullName,
            AuditAction action,
            String entityName,
            String entityId,
            String description,
            LocalDateTime createdAt
    ) {
        this.auditCode = auditCode;
        this.userId = userId;
        this.username = username;
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.action = action;
        this.entityName = entityName;
        this.entityId = entityId;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getAuditCode() {
        return auditCode;
    }

    public void setAuditCode(String auditCode) {
        this.auditCode = auditCode;
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

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
