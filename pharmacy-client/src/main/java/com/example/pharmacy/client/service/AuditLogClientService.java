package com.example.pharmacy.client.service;

import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.request.AuditLogSearchRequest;

import java.util.List;

public interface AuditLogClientService {
    List<AuditLogDTO> findAuditLogs(AuditLogSearchRequest request);

    AuditLogDTO findAuditLogByCode(String auditCode);
}
