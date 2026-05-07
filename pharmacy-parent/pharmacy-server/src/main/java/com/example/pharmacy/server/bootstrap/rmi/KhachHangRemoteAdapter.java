package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.KhachHangRemote;
import com.example.pharmacy.server.service.KhachHangService;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class KhachHangRemoteAdapter extends UnicastRemoteObject implements KhachHangRemote {
    private final KhachHangService khachHangService;

    public KhachHangRemoteAdapter(KhachHangService khachHangService) throws RemoteException {
        super();
        this.khachHangService = Objects.requireNonNull(khachHangService, "khachHangService must not be null");
    }

    @Override
    public List<KhachHang> findAll() throws RemoteException {
        return khachHangService.findAll();
    }

    @Override
    public KhachHang findById(String maKhachHang) throws RemoteException {
        return khachHangService.findById(maKhachHang);
    }

    @Override
    public String generateNewMaKH() throws RemoteException {
        return khachHangService.generateNewMaKH();
    }

    @Override
    public boolean create(KhachHang khachHang) throws RemoteException {
        return khachHangService.create(khachHang);
    }

    @Override
    public boolean save(KhachHang khachHang) throws RemoteException {
        return khachHangService.save(khachHang);
    }

    @Override
    public boolean deleteById(String maKhachHang) throws RemoteException {
        return khachHangService.deleteById(maKhachHang);
    }
}
