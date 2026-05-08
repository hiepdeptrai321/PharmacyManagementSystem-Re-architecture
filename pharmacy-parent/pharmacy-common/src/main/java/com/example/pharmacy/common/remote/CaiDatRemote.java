package com.example.pharmacy.common.remote;

import com.example.pharmacymanagementsystem_qlht.model.CaiDat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CaiDatRemote extends Remote {
    String BINDING_NAME = "CaiDatRemoteService";

    List<CaiDat> findAll() throws RemoteException;

    boolean update(CaiDat caiDat) throws RemoteException;
}
