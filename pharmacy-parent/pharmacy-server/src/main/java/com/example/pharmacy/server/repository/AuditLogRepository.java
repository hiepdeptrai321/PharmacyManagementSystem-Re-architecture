package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.dto.AuditLogDTO;

public interface AuditLogRepository {
    void insert(AuditLogDTO auditLog);
}
