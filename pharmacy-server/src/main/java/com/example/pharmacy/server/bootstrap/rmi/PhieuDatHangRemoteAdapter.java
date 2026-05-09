package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.remote.PhieuDatHangRemote;
import com.example.pharmacy.server.service.PhieuDatHangService;
import com.example.pharmacy.common.model.ChiTietPhieuDatHangDto;
import com.example.pharmacy.common.model.PhieuDatHangDto;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class PhieuDatHangRemoteAdapter extends UnicastRemoteObject implements PhieuDatHangRemote {
    private final PhieuDatHangService phieuDatHangService;

    public PhieuDatHangRemoteAdapter(PhieuDatHangService phieuDatHangService) throws RemoteException {
        super();
        this.phieuDatHangService = Objects.requireNonNull(phieuDatHangService, "phieuDatHangService must not be null");
    }

    @Override
    public String generateNewMaPhieuDatHang() throws RemoteException {
        return phieuDatHangService.generateNewMaPhieuDatHang();
    }

    @Override
    public String create(PhieuDatHangDto phieuDatHang, List<ChiTietPhieuDatHangDto> details, UserContext actor) throws RemoteException {
        return phieuDatHangService.create(phieuDatHang, details, actor);
    }

    @Override
    public List<PhieuDatHangDto> findAll() throws RemoteException {
        return phieuDatHangService.findAll();
    }

    @Override
    public PhieuDatHangDto findById(String maPhieuDat) throws RemoteException {
        return phieuDatHangService.findById(maPhieuDat);
    }

    @Override
    public List<ChiTietPhieuDatHangDto> findDetailsByMaPhieuDat(String maPhieuDat) throws RemoteException {
        return phieuDatHangService.findDetailsByMaPhieuDat(maPhieuDat);
    }

    @Override
    public boolean approve(String maPhieuDat, UserContext actor) throws RemoteException {
        return phieuDatHangService.approve(maPhieuDat, actor);
    }
}
