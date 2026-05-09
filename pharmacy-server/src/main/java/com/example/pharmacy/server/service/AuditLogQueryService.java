package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.request.AuditLogSearchRequest;

import java.util.List;

public interface AuditLogQueryService {
    List<AuditLogDTO> findAuditLogs(AuditLogSearchRequest request);

    AuditLogDTO findAuditLogByCode(String auditCode);
}
