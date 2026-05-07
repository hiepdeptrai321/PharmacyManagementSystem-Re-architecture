package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.KeHangRemote;
import com.example.pharmacy.server.service.KeHangService;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class KeHangRemoteAdapter extends UnicastRemoteObject implements KeHangRemote {
    private final KeHangService keHangService;

    public KeHangRemoteAdapter(KeHangService keHangService) throws RemoteException {
        super();
        this.keHangService = Objects.requireNonNull(keHangService, "keHangService must not be null");
    }

    @Override
    public List<KeHang> findAll() throws RemoteException {
        return keHangService.findAll();
    }

    @Override
    public KeHang findById(String maKeHang) throws RemoteException {
        return keHangService.findById(maKeHang);
    }

    @Override
    public KeHang findByTenKe(String tenKe) throws RemoteException {
        return keHangService.findByTenKe(tenKe);
    }

    @Override
    public String generateNewMaKeHang() throws RemoteException {
        return keHangService.generateNewMaKeHang();
    }

    @Override
    public boolean create(KeHang keHang) throws RemoteException {
        return keHangService.create(keHang);
    }

    @Override
    public boolean update(KeHang keHang) throws RemoteException {
        return keHangService.update(keHang);
    }

    @Override
    public boolean deleteById(String maKeHang) throws RemoteException {
        return keHangService.deleteById(maKeHang);
    }

    @Override
    public List<String> findThuocNamesByKeHang(String maKeHang) throws RemoteException {
        return keHangService.findThuocNamesByKeHang(maKeHang);
    }
}
