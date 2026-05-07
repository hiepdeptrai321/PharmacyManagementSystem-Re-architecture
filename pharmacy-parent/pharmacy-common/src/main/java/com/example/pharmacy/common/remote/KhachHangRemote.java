package com.example.pharmacy.common.remote;

import com.example.pharmacymanagementsystem_qlht.model.KhachHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface KhachHangRemote extends Remote {
    String BINDING_NAME = "KhachHangRemoteService";

    List<KhachHang> findAll() throws RemoteException;

    KhachHang findById(String maKhachHang) throws RemoteException;

    String generateNewMaKH() throws RemoteException;

    boolean create(KhachHang khachHang) throws RemoteException;

    boolean save(KhachHang khachHang) throws RemoteException;

    boolean deleteById(String maKhachHang) throws RemoteException;
}
