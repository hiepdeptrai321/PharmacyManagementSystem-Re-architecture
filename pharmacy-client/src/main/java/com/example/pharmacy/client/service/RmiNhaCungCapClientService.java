package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.remote.NhaCungCapRemote;
import com.example.pharmacy.common.model.NhaCungCapDto;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiNhaCungCapClientService implements NhaCungCapClientService {
    private final RmiClientProvider clientProvider;

    public RmiNhaCungCapClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<NhaCungCapDto> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach nha cung cap tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public NhaCungCapDto findById(String maNhaCungCap) {
        try {
            return remote().findById(maNhaCungCap);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet nha cung cap tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public String generateNewMaNCC() {
        try {
            return remote().generateNewMaNCC();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma nha cung cap moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public boolean create(NhaCungCapDto nhaCungCap) {
        try {
            return remote().create(nhaCungCap);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tao nha cung cap tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(NhaCungCapDto nhaCungCap) {
        try {
            return remote().update(nhaCungCap);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the cap nhat nha cung cap tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(String maNhaCungCap) {
        try {
            return remote().deleteById(maNhaCungCap);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the xoa nha cung cap tren server: " + exception.getMessage());
            return false;
        }
    }

    private NhaCungCapRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getNhaCungCapRemote();
    }
}
