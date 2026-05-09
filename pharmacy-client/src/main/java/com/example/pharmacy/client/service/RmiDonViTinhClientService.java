package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.remote.DonViTinhRemote;
import com.example.pharmacy.common.model.DonViTinh;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiDonViTinhClientService implements DonViTinhClientService {
    private final RmiClientProvider clientProvider;

    public RmiDonViTinhClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<DonViTinh> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach don vi tinh tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public DonViTinh findById(String maDonViTinh) {
        try {
            return remote().findById(maDonViTinh);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai don vi tinh tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public DonViTinh findByTenDonViTinh(String tenDonViTinh) {
        try {
            return remote().findByTenDonViTinh(tenDonViTinh);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tim don vi tinh tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public String generateNewMaDVT() {
        try {
            return remote().generateNewMaDVT();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma don vi tinh moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public boolean create(DonViTinh donViTinh) {
        try {
            return remote().create(donViTinh);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tao don vi tinh tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(DonViTinh donViTinh) {
        try {
            return remote().update(donViTinh);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the cap nhat don vi tinh tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(String maDonViTinh) {
        try {
            return remote().deleteById(maDonViTinh);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the xoa don vi tinh tren server: " + exception.getMessage());
            return false;
        }
    }

    private DonViTinhRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getDonViTinhRemote();
    }
}
