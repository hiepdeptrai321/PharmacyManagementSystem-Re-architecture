package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.DonViTinhRemote;
import com.example.pharmacy.server.service.DonViTinhService;
import com.example.pharmacymanagementsystem_qlht.model.DonViTinh;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class DonViTinhRemoteAdapter extends UnicastRemoteObject implements DonViTinhRemote {
    private final DonViTinhService donViTinhService;

    public DonViTinhRemoteAdapter(DonViTinhService donViTinhService) throws RemoteException {
        super();
        this.donViTinhService = Objects.requireNonNull(donViTinhService, "donViTinhService must not be null");
    }

    @Override
    public List<DonViTinh> findAll() throws RemoteException {
        return donViTinhService.findAll();
    }

    @Override
    public DonViTinh findById(String maDonViTinh) throws RemoteException {
        return donViTinhService.findById(maDonViTinh);
    }

    @Override
    public DonViTinh findByTenDonViTinh(String tenDonViTinh) throws RemoteException {
        return donViTinhService.findByTenDonViTinh(tenDonViTinh);
    }

    @Override
    public String generateNewMaDVT() throws RemoteException {
        return donViTinhService.generateNewMaDVT();
    }

    @Override
    public boolean create(DonViTinh donViTinh) throws RemoteException {
        return donViTinhService.create(donViTinh);
    }

    @Override
    public boolean update(DonViTinh donViTinh) throws RemoteException {
        return donViTinhService.update(donViTinh);
    }

    @Override
    public boolean deleteById(String maDonViTinh) throws RemoteException {
        return donViTinhService.deleteById(maDonViTinh);
    }
}
