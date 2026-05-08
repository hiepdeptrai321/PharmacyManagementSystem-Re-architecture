package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.remote.AuditLogRemote;
import com.example.pharmacy.common.request.AuditLogSearchRequest;
import com.example.pharmacy.server.service.AuditLogQueryService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class AuditLogRemoteAdapter extends UnicastRemoteObject implements AuditLogRemote {
    private final AuditLogQueryService auditLogQueryService;

    public AuditLogRemoteAdapter(AuditLogQueryService auditLogQueryService) throws RemoteException {
        super();
        this.auditLogQueryService = Objects.requireNonNull(auditLogQueryService, "auditLogQueryService must not be null");
    }

    @Override
    public List<AuditLogDTO> findAuditLogs(AuditLogSearchRequest request) throws RemoteException {
        return auditLogQueryService.findAuditLogs(request);
    }

    @Override
    public AuditLogDTO findAuditLogByCode(String auditCode) throws RemoteException {
        return auditLogQueryService.findAuditLogByCode(auditCode);
    }
}
