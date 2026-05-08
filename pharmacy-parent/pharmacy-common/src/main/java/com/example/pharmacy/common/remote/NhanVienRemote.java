package com.example.pharmacy.common.remote;

import com.example.pharmacymanagementsystem_qlht.model.LuongNhanVien;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhanVienRemote extends Remote {
    String BINDING_NAME = "NhanVienRemoteService";

    List<NhanVien> findAll() throws RemoteException;

    NhanVien findById(String maNhanVien) throws RemoteException;

    String generateNewMaNhanVien() throws RemoteException;

    boolean create(NhanVien nhanVien) throws RemoteException;

    boolean update(NhanVien nhanVien) throws RemoteException;

    boolean softDelete(String maNhanVien) throws RemoteException;

    boolean isUsernameAvailable(String username, String excludedMaNhanVien) throws RemoteException;

    List<LuongNhanVien> findLuongByMaNhanVien(String maNhanVien) throws RemoteException;

    String generateNewMaLuongNhanVien() throws RemoteException;

    boolean saveLuongNhanVien(LuongNhanVien luongNhanVien) throws RemoteException;
}
