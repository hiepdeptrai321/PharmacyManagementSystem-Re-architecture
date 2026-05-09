package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.remote.PhieuDatHangRemote;
import com.example.pharmacy.common.model.ChiTietPhieuDatHang;
import com.example.pharmacy.common.model.PhieuDatHang;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiPhieuDatHangClientService implements PhieuDatHangClientService {
    private final RmiClientProvider clientProvider;

    public RmiPhieuDatHangClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public String generateNewMaPhieuDatHang() {
        try {
            return remote().generateNewMaPhieuDatHang();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma phieu dat hang moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public String create(PhieuDatHang phieuDatHang, List<ChiTietPhieuDatHang> details, UserContext actor) {
        try {
            return remote().create(phieuDatHang, details, actor);
        } catch (RemoteException | NotBoundException exception) {
            throw new IllegalStateException("Khong the tao phieu dat hang tren server: " + exception.getMessage(), exception);
        }
    }

    @Override
    public List<PhieuDatHang> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach phieu dat hang tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public PhieuDatHang findById(String maPhieuDat) {
        try {
            return remote().findById(maPhieuDat);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet phieu dat hang tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public List<ChiTietPhieuDatHang> findDetailsByMaPhieuDat(String maPhieuDat) {
        try {
            return remote().findDetailsByMaPhieuDat(maPhieuDat);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet dong phieu dat hang tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public boolean approve(String maPhieuDat, UserContext actor) {
        try {
            return remote().approve(maPhieuDat, actor);
        } catch (RemoteException | NotBoundException exception) {
            throw new IllegalStateException("Khong the duyet phieu dat hang tren server: " + exception.getMessage(), exception);
        }
    }

    private PhieuDatHangRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getPhieuDatHangRemote();
    }
}
