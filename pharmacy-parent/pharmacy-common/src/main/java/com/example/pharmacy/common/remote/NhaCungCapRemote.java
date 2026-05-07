package com.example.pharmacy.common.remote;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhaCungCapRemote extends Remote {
    String BINDING_NAME = "NhaCungCapRemoteService";

    List<NhaCungCap> findAll() throws RemoteException;

    NhaCungCap findById(String maNhaCungCap) throws RemoteException;

    String generateNewMaNCC() throws RemoteException;

    boolean create(NhaCungCap nhaCungCap) throws RemoteException;

    boolean update(NhaCungCap nhaCungCap) throws RemoteException;

    boolean deleteById(String maNhaCungCap) throws RemoteException;
}
