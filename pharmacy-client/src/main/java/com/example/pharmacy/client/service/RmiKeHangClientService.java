package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.remote.KeHangRemote;
import com.example.pharmacy.common.model.KeHangDto;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiKeHangClientService implements KeHangClientService {
    private final RmiClientProvider clientProvider;

    public RmiKeHangClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<KeHangDto> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach ke hang tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public KeHangDto findById(String maKeHang) {
        try {
            return remote().findById(maKeHang);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai ke hang tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public KeHangDto findByTenKe(String tenKe) {
        try {
            return remote().findByTenKe(tenKe);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai ke hang theo ten tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public String generateNewMaKeHang() {
        try {
            return remote().generateNewMaKeHang();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma ke hang moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public boolean create(KeHangDto keHang) {
        try {
            return remote().create(keHang);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tao ke hang tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(KeHangDto keHang) {
        try {
            return remote().update(keHang);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the cap nhat ke hang tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(String maKeHang) {
        try {
            return remote().deleteById(maKeHang);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the xoa ke hang tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public List<String> findThuocNamesByKeHang(String maKeHang) {
        try {
            return remote().findThuocNamesByKeHang(maKeHang);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai thuoc lien ket ke hang tu server: " + exception.getMessage());
            return List.of();
        }
    }

    private KeHangRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getKeHangRemote();
    }
}
