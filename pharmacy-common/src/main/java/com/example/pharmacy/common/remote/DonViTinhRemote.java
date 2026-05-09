package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.DonViTinhDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DonViTinhRemote extends Remote {
    String BINDING_NAME = "DonViTinhRemoteService";

    List<DonViTinhDto> findAll() throws RemoteException;

    DonViTinhDto findById(String maDonViTinh) throws RemoteException;

    DonViTinhDto findByTenDonViTinh(String tenDonViTinh) throws RemoteException;

    String generateNewMaDVT() throws RemoteException;

    boolean create(DonViTinhDto donViTinh) throws RemoteException;

    boolean update(DonViTinhDto donViTinh) throws RemoteException;

    boolean deleteById(String maDonViTinh) throws RemoteException;
}
