package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.NhomDuocLyClientService;
import com.example.pharmacy.common.remote.NhomDuocLyRemote;
import com.example.pharmacy.common.model.NhomDuocLyDto;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiNhomDuocLyClientService implements NhomDuocLyClientService {
    private final RmiClientProvider clientProvider;

    public RmiNhomDuocLyClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<NhomDuocLyDto> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach nhom duoc ly tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public NhomDuocLyDto findById(String maNhomDuocLy) {
        try {
            return remote().findById(maNhomDuocLy);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai nhom duoc ly tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public String generateNewMaNhomDuocLy() {
        try {
            return remote().generateNewMaNhomDuocLy();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma nhom duoc ly moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public boolean create(NhomDuocLyDto nhomDuocLy) {
        try {
            return remote().create(nhomDuocLy);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tao nhom duoc ly tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(NhomDuocLyDto nhomDuocLy) {
        try {
            return remote().update(nhomDuocLy);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the cap nhat nhom duoc ly tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(String maNhomDuocLy) {
        try {
            return remote().deleteById(maNhomDuocLy);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the xoa nhom duoc ly tren server: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy) {
        try {
            return remote().findThuocNamesByNhomDuocLy(maNhomDuocLy);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai thuoc lien ket nhom duoc ly tu server: " + exception.getMessage());
            return List.of();
        }
    }

    private NhomDuocLyRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getNhomDuocLyRemote();
    }
}
