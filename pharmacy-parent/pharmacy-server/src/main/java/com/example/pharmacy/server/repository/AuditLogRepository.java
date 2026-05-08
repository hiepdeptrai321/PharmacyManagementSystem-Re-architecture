package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.request.AuditLogSearchRequest;

import java.util.List;

public interface AuditLogRepository {
    void insert(AuditLogDTO auditLog);

    List<AuditLogDTO> findAuditLogs(AuditLogSearchRequest request);

    AuditLogDTO findAuditLogByCode(String auditCode);
}
