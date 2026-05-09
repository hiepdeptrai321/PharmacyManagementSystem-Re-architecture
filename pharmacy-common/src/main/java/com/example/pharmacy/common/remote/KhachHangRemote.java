package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.KhachHangDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface KhachHangRemote extends Remote {
    String BINDING_NAME = "KhachHangRemoteService";

    List<KhachHangDto> findAll() throws RemoteException;

    KhachHangDto findById(String maKhachHang) throws RemoteException;

    String generateNewMaKH() throws RemoteException;

    boolean create(KhachHangDto khachHang) throws RemoteException;

    boolean save(KhachHangDto khachHang) throws RemoteException;

    boolean deleteById(String maKhachHang) throws RemoteException;
}
