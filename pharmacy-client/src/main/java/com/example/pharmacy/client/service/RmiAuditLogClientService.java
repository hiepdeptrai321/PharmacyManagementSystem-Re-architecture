package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.AuditLogClientService;
import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.remote.AuditLogRemote;
import com.example.pharmacy.common.request.AuditLogSearchRequest;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiAuditLogClientService implements AuditLogClientService {
    private final RmiClientProvider clientProvider;

    public RmiAuditLogClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<AuditLogDTO> findAuditLogs(AuditLogSearchRequest request) {
        try {
            return remote().findAuditLogs(request);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai audit log tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public AuditLogDTO findAuditLogByCode(String auditCode) {
        try {
            return remote().findAuditLogByCode(auditCode);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet hoat dong tu server: " + exception.getMessage());
            return null;
        }
    }

    private AuditLogRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getAuditLogRemote();
    }
}
