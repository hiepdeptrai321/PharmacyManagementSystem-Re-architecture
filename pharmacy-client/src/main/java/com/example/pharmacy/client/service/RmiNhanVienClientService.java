package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.remote.NhanVienRemote;
import com.example.pharmacy.common.model.LuongNhanVienDto;
import com.example.pharmacy.common.model.NhanVienDto;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiNhanVienClientService implements NhanVienClientService {
    private final RmiClientProvider clientProvider;

    public RmiNhanVienClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<NhanVienDto> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach nhan vien tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public NhanVienDto findById(String maNhanVien) {
        try {
            return remote().findById(maNhanVien);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet nhan vien tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public String generateNewMaNhanVien() {
        try {
            return remote().generateNewMaNhanVien();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma nhan vien moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public boolean create(NhanVienDto nhanVien) {
        try {
            return remote().create(nhanVien);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tao nhan vien tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(NhanVienDto nhanVien) {
        try {
            return remote().update(nhanVien);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the cap nhat nhan vien tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean softDelete(String maNhanVien) {
        try {
            return remote().softDelete(maNhanVien);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the xoa mem nhan vien tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean isUsernameAvailable(String username, String excludedMaNhanVien) {
        try {
            return remote().isUsernameAvailable(username, excludedMaNhanVien);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the kiem tra tai khoan nhan vien tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public List<LuongNhanVienDto> findLuongByMaNhanVien(String maNhanVien) {
        try {
            return remote().findLuongByMaNhanVien(maNhanVien);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach luong nhan vien tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public String generateNewMaLuongNhanVien() {
        try {
            return remote().generateNewMaLuongNhanVien();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma luong nhan vien moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public boolean saveLuongNhanVien(LuongNhanVienDto luongNhanVien) {
        try {
            return remote().saveLuongNhanVien(luongNhanVien);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the luu luong nhan vien tren server: " + exception.getMessage());
            return false;
        }
    }

    private NhanVienRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getNhanVienRemote();
    }
}
