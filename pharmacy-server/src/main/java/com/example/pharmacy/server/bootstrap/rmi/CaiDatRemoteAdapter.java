package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.CaiDatRemote;
import com.example.pharmacy.server.service.CaiDatService;
import com.example.pharmacy.common.model.CaiDatDto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class CaiDatRemoteAdapter extends UnicastRemoteObject implements CaiDatRemote {
    private final CaiDatService caiDatService;

    public CaiDatRemoteAdapter(CaiDatService caiDatService) throws RemoteException {
        super();
        this.caiDatService = Objects.requireNonNull(caiDatService, "caiDatService must not be null");
    }

    @Override
    public List<CaiDatDto> findAll() throws RemoteException {
        return caiDatService.findAll();
    }

    @Override
    public boolean update(CaiDatDto caiDat) throws RemoteException {
        return caiDatService.update(caiDat);
    }
}
