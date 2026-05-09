package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.NhanVienRemote;
import com.example.pharmacy.server.service.NhanVienService;
import com.example.pharmacy.common.model.LuongNhanVien;
import com.example.pharmacy.common.model.NhanVien;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class NhanVienRemoteAdapter extends UnicastRemoteObject implements NhanVienRemote {
    private final NhanVienService nhanVienService;

    public NhanVienRemoteAdapter(NhanVienService nhanVienService) throws RemoteException {
        super();
        this.nhanVienService = Objects.requireNonNull(nhanVienService, "nhanVienService must not be null");
    }

    @Override
    public List<NhanVien> findAll() throws RemoteException {
        return nhanVienService.findAll();
    }

    @Override
    public NhanVien findById(String maNhanVien) throws RemoteException {
        return nhanVienService.findById(maNhanVien);
    }

    @Override
    public String generateNewMaNhanVien() throws RemoteException {
        return nhanVienService.generateNewMaNhanVien();
    }

    @Override
    public boolean create(NhanVien nhanVien) throws RemoteException {
        return nhanVienService.create(nhanVien);
    }

    @Override
    public boolean update(NhanVien nhanVien) throws RemoteException {
        return nhanVienService.update(nhanVien);
    }

    @Override
    public boolean softDelete(String maNhanVien) throws RemoteException {
        return nhanVienService.softDelete(maNhanVien);
    }

    @Override
    public boolean isUsernameAvailable(String username, String excludedMaNhanVien) throws RemoteException {
        return nhanVienService.isUsernameAvailable(username, excludedMaNhanVien);
    }

    @Override
    public List<LuongNhanVien> findLuongByMaNhanVien(String maNhanVien) throws RemoteException {
        return nhanVienService.findLuongByMaNhanVien(maNhanVien);
    }

    @Override
    public String generateNewMaLuongNhanVien() throws RemoteException {
        return nhanVienService.generateNewMaLuongNhanVien();
    }

    @Override
    public boolean saveLuongNhanVien(LuongNhanVien luongNhanVien) throws RemoteException {
        return nhanVienService.saveLuongNhanVien(luongNhanVien);
    }
}
