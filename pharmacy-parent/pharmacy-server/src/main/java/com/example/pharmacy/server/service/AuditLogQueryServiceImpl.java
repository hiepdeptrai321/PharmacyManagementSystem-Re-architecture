package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.request.AuditLogSearchRequest;
import com.example.pharmacy.server.repository.AuditLogRepository;

import java.util.List;
import java.util.Objects;

public class AuditLogQueryServiceImpl implements AuditLogQueryService {
    private final AuditLogRepository auditLogRepository;

    public AuditLogQueryServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = Objects.requireNonNull(auditLogRepository, "auditLogRepository must not be null");
    }

    @Override
    public List<AuditLogDTO> findAuditLogs(AuditLogSearchRequest request) {
        return auditLogRepository.findAuditLogs(request);
    }

    @Override
    public AuditLogDTO findAuditLogByCode(String auditCode) {
        return auditLogRepository.findAuditLogByCode(auditCode);
    }
}
