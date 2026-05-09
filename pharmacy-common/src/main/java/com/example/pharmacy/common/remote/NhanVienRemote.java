package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.model.LuongNhanVienDto;
import com.example.pharmacy.common.model.NhanVienDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NhanVienRemote extends Remote {
    String BINDING_NAME = "NhanVienRemoteService";

    List<NhanVienDto> findAll() throws RemoteException;

    NhanVienDto findById(String maNhanVien) throws RemoteException;

    String generateNewMaNhanVien() throws RemoteException;

    boolean create(NhanVienDto nhanVien) throws RemoteException;

    boolean update(NhanVienDto nhanVien) throws RemoteException;

    boolean softDelete(String maNhanVien) throws RemoteException;

    boolean isUsernameAvailable(String username, String excludedMaNhanVien) throws RemoteException;

    List<LuongNhanVienDto> findLuongByMaNhanVien(String maNhanVien) throws RemoteException;

    String generateNewMaLuongNhanVien() throws RemoteException;

    boolean saveLuongNhanVien(LuongNhanVienDto luongNhanVien) throws RemoteException;
}
