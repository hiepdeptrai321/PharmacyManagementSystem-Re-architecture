package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.NhaCungCapDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhaCungCapRemote extends Remote {
    String BINDING_NAME = "NhaCungCapRemoteService";

    List<NhaCungCapDto> findAll() throws RemoteException;

    NhaCungCapDto findById(String maNhaCungCap) throws RemoteException;

    String generateNewMaNCC() throws RemoteException;

    boolean create(NhaCungCapDto nhaCungCap) throws RemoteException;

    boolean update(NhaCungCapDto nhaCungCap) throws RemoteException;

    boolean deleteById(String maNhaCungCap) throws RemoteException;
}
