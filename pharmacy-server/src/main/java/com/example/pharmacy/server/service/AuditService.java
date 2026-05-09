package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.enums.AuditAction;

public interface AuditService {
    AuditLogDTO logAction(UserContext actor, AuditAction action, String entityName, String entityId, String description);
}
