package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.enums.AuditAction;
import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.repository.AuditLogRepository;

import java.time.LocalDateTime;
import java.util.Objects;

public class AuditServiceImpl implements AuditService {
    private final AuditLogRepository auditLogRepository;
    private final CodeGenerationService codeGenerationService;

    public AuditServiceImpl(AuditLogRepository auditLogRepository, CodeGenerationService codeGenerationService) {
        this.auditLogRepository = Objects.requireNonNull(auditLogRepository, "auditLogRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public AuditLogDTO logAction(UserContext actor, AuditAction action, String entityName, String entityId, String description) {
        AuditLogDTO auditLog = new AuditLogDTO(
                codeGenerationService.nextCode(BusinessCodeType.HOAT_DONG),
                actor == null ? null : actor.getUserId(),
                actor == null ? null : actor.getUsername(),
                actor == null ? null : actor.getEmployeeId(),
                actor == null ? null : actor.getFullName(),
                action,
                entityName,
                entityId,
                description,
                LocalDateTime.now()
        );
        auditLogRepository.insert(auditLog);
        return auditLog;
    }
}
