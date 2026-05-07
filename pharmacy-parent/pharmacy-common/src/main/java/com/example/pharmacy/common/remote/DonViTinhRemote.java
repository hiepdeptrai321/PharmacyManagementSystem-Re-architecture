package com.example.pharmacy.common.remote;

import com.example.pharmacymanagementsystem_qlht.model.DonViTinh;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DonViTinhRemote extends Remote {
    String BINDING_NAME = "DonViTinhRemoteService";

    List<DonViTinh> findAll() throws RemoteException;

    DonViTinh findById(String maDonViTinh) throws RemoteException;

    DonViTinh findByTenDonViTinh(String tenDonViTinh) throws RemoteException;

    String generateNewMaDVT() throws RemoteException;

    boolean create(DonViTinh donViTinh) throws RemoteException;

    boolean update(DonViTinh donViTinh) throws RemoteException;

    boolean deleteById(String maDonViTinh) throws RemoteException;
}
