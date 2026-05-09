package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.remote.CaiDatRemote;
import com.example.pharmacy.common.model.CaiDatDto;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiCaiDatClientService implements CaiDatClientService {
    private final RmiClientProvider clientProvider;

    public RmiCaiDatClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<CaiDatDto> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach cai dat tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public boolean update(CaiDatDto caiDat) {
        try {
            return remote().update(caiDat);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the cap nhat cai dat tren server: " + exception.getMessage());
            return false;
        }
    }

    private CaiDatRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getCaiDatRemote();
    }
}
