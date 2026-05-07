package com.example.pharmacy.common.remote;

import com.example.pharmacymanagementsystem_qlht.model.KeHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface KeHangRemote extends Remote {
    String BINDING_NAME = "KeHangRemoteService";

    List<KeHang> findAll() throws RemoteException;

    KeHang findById(String maKeHang) throws RemoteException;

    KeHang findByTenKe(String tenKe) throws RemoteException;

    String generateNewMaKeHang() throws RemoteException;

    boolean create(KeHang keHang) throws RemoteException;

    boolean update(KeHang keHang) throws RemoteException;

    boolean deleteById(String maKeHang) throws RemoteException;

    List<String> findThuocNamesByKeHang(String maKeHang) throws RemoteException;
}
