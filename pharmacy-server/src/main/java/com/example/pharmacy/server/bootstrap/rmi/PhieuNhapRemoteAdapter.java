package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.remote.PhieuNhapRemote;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacy.server.service.PhieuNhapService;
import com.example.pharmacy.common.model.ChiTietPhieuNhapDto;
import com.example.pharmacy.common.model.PhieuNhapDto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class PhieuNhapRemoteAdapter extends UnicastRemoteObject implements PhieuNhapRemote {
    private final PhieuNhapService phieuNhapService;

    public PhieuNhapRemoteAdapter(PhieuNhapService phieuNhapService) throws RemoteException {
        super();
        this.phieuNhapService = Objects.requireNonNull(phieuNhapService, "phieuNhapService must not be null");
    }

    @Override
    public String generateNewMaPhieuNhap() throws RemoteException {
        return phieuNhapService.generateNewMaPhieuNhap();
    }

    @Override
    public String createPurchaseOrder(PhieuNhapRequest request, UserContext actor) throws RemoteException {
        return phieuNhapService.createPurchaseOrder(request, actor);
    }

    @Override
    public List<PhieuNhapDto> findAll() throws RemoteException {
        return phieuNhapService.findAll();
    }

    @Override
    public PhieuNhapDto findById(String maPhieuNhap) throws RemoteException {
        return phieuNhapService.findById(maPhieuNhap);
    }

    @Override
    public List<ChiTietPhieuNhapDto> findDetailsByMaPhieuNhap(String maPhieuNhap) throws RemoteException {
        return phieuNhapService.findDetailsByMaPhieuNhap(maPhieuNhap);
    }
}
