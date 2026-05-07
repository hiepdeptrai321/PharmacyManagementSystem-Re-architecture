package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.remote.KhachHangRemote;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiKhachHangClientService implements KhachHangClientService {
    private final RmiClientProvider clientProvider;

    public RmiKhachHangClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<KhachHang> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach khach hang tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public KhachHang findById(String maKhachHang) {
        try {
            return remote().findById(maKhachHang);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet khach hang tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public String generateNewMaKH() {
        try {
            return remote().generateNewMaKH();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma khach hang moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public boolean create(KhachHang khachHang) {
        try {
            return remote().create(khachHang);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tao khach hang tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean save(KhachHang khachHang) {
        try {
            return remote().save(khachHang);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the luu khach hang tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(String maKhachHang) {
        try {
            return remote().deleteById(maKhachHang);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the xoa khach hang tren server: " + exception.getMessage());
            return false;
        }
    }

    private KhachHangRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getKhachHangRemote();
    }
}
