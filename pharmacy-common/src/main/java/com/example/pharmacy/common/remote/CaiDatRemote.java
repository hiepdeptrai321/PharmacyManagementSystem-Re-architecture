package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.CaiDatDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CaiDatRemote extends Remote {
    String BINDING_NAME = "CaiDatRemoteService";

    List<CaiDatDto> findAll() throws RemoteException;

    boolean update(CaiDatDto caiDat) throws RemoteException;
}
