package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.KeHangDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface KeHangRemote extends Remote {
    String BINDING_NAME = "KeHangRemoteService";

    List<KeHangDto> findAll() throws RemoteException;

    KeHangDto findById(String maKeHang) throws RemoteException;

    KeHangDto findByTenKe(String tenKe) throws RemoteException;

    String generateNewMaKeHang() throws RemoteException;

    boolean create(KeHangDto keHang) throws RemoteException;

    boolean update(KeHangDto keHang) throws RemoteException;

    boolean deleteById(String maKeHang) throws RemoteException;

    List<String> findThuocNamesByKeHang(String maKeHang) throws RemoteException;
}
