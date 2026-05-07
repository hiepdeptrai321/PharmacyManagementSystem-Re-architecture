package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.PhieuDatHangClientService;
import com.example.pharmacy.client.service.RmiPhieuDatHangClientService;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDatHang;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDatHang;
import com.example.pharmacymanagementsystem_qlht.session.UserContext;
import com.example.pharmacymanagementsystem_qlht.session.UserContextMapper;

import java.util.List;

public class PhieuDatHangService {
    private final PhieuDatHangClientService phieuDatHangClientService =
            new RmiPhieuDatHangClientService(new RmiClientProvider());

    public String generateNewMaPhieuDatHang() {
        return phieuDatHangClientService.generateNewMaPhieuDatHang();
    }

    public String create(PhieuDatHang phieuDatHang, List<ChiTietPhieuDatHang> details, UserContext actor) {
        return phieuDatHangClientService.create(phieuDatHang, details, UserContextMapper.toRemoteUserContext(actor));
    }

    public List<PhieuDatHang> findAll() {
        return phieuDatHangClientService.findAll();
    }

    public PhieuDatHang findById(String maPhieuDat) {
        return phieuDatHangClientService.findById(maPhieuDat);
    }

    public List<ChiTietPhieuDatHang> findDetailsByMaPhieuDat(String maPhieuDat) {
        return phieuDatHangClientService.findDetailsByMaPhieuDat(maPhieuDat);
    }

    public boolean approve(String maPhieuDat, UserContext actor) {
        return phieuDatHangClientService.approve(maPhieuDat, UserContextMapper.toRemoteUserContext(actor));
    }
}
