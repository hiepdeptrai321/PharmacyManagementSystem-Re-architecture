package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.remote.PhieuNhapRemote;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacy.common.model.ChiTietPhieuNhapDto;
import com.example.pharmacy.common.model.PhieuNhapDto;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiPhieuNhapClientService implements PhieuNhapClientService {
    private final RmiClientProvider clientProvider;

    public RmiPhieuNhapClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public String generateNewMaPhieuNhap() {
        try {
            return remote().generateNewMaPhieuNhap();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma phieu nhap moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public String createPurchaseOrder(PhieuNhapRequest request, UserContext actor) {
        try {
            return remote().createPurchaseOrder(request, actor);
        } catch (RemoteException | NotBoundException exception) {
            throw new IllegalStateException("Khong the tao phieu nhap tren server: " + exception.getMessage(), exception);
        }
    }

    @Override
    public List<PhieuNhapDto> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach phieu nhap tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public PhieuNhapDto findById(String maPhieuNhap) {
        try {
            return remote().findById(maPhieuNhap);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet phieu nhap tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public List<ChiTietPhieuNhapDto> findDetailsByMaPhieuNhap(String maPhieuNhap) {
        try {
            return remote().findDetailsByMaPhieuNhap(maPhieuNhap);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet dong phieu nhap tu server: " + exception.getMessage());
            return List.of();
        }
    }

    private PhieuNhapRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getPhieuNhapRemote();
    }
}
