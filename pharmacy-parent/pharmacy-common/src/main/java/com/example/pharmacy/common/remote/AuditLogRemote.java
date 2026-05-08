package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.dto.AuditLogDTO;
import com.example.pharmacy.common.request.AuditLogSearchRequest;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AuditLogRemote extends Remote {
    String BINDING_NAME = "AuditLogRemoteService";

    List<AuditLogDTO> findAuditLogs(AuditLogSearchRequest request) throws RemoteException;

    AuditLogDTO findAuditLogByCode(String auditCode) throws RemoteException;
}
