package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.NhaCungCapRemote;
import com.example.pharmacy.server.service.NhaCungCapService;
import com.example.pharmacy.common.model.NhaCungCapDto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class NhaCungCapRemoteAdapter extends UnicastRemoteObject implements NhaCungCapRemote {
    private final NhaCungCapService nhaCungCapService;

    public NhaCungCapRemoteAdapter(NhaCungCapService nhaCungCapService) throws RemoteException {
        super();
        this.nhaCungCapService = Objects.requireNonNull(nhaCungCapService, "nhaCungCapService must not be null");
    }

    @Override
    public List<NhaCungCapDto> findAll() throws RemoteException {
        return nhaCungCapService.findAll();
    }

    @Override
    public NhaCungCapDto findById(String maNhaCungCap) throws RemoteException {
        return nhaCungCapService.findById(maNhaCungCap);
    }

    @Override
    public String generateNewMaNCC() throws RemoteException {
        return nhaCungCapService.generateNewMaNCC();
    }

    @Override
    public boolean create(NhaCungCapDto nhaCungCap) throws RemoteException {
        return nhaCungCapService.create(nhaCungCap);
    }

    @Override
    public boolean update(NhaCungCapDto nhaCungCap) throws RemoteException {
        return nhaCungCapService.update(nhaCungCap);
    }

    @Override
    public boolean deleteById(String maNhaCungCap) throws RemoteException {
        return nhaCungCapService.deleteById(maNhaCungCap);
    }
}
