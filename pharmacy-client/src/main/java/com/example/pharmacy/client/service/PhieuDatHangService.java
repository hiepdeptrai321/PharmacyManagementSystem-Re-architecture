package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.PhieuDatHangClientService;
import com.example.pharmacy.client.service.RmiPhieuDatHangClientService;
import com.example.pharmacy.common.model.ChiTietPhieuDatHangDto;
import com.example.pharmacy.common.model.PhieuDatHangDto;
import com.example.pharmacy.common.session.UserContext;
import com.example.pharmacy.client.session.UserContextMapper;

import java.util.List;

public class PhieuDatHangService {
    private final PhieuDatHangClientService phieuDatHangClientService =
            new RmiPhieuDatHangClientService(new RmiClientProvider());

    public String generateNewMaPhieuDatHang() {
        return phieuDatHangClientService.generateNewMaPhieuDatHang();
    }

    public String create(PhieuDatHangDto phieuDatHang, List<ChiTietPhieuDatHangDto> details, UserContext actor) {
        return phieuDatHangClientService.create(phieuDatHang, details, UserContextMapper.toRemoteUserContext(actor));
    }

    public List<PhieuDatHangDto> findAll() {
        return phieuDatHangClientService.findAll();
    }

    public PhieuDatHangDto findById(String maPhieuDat) {
        return phieuDatHangClientService.findById(maPhieuDat);
    }

    public List<ChiTietPhieuDatHangDto> findDetailsByMaPhieuDat(String maPhieuDat) {
        return phieuDatHangClientService.findDetailsByMaPhieuDat(maPhieuDat);
    }

    public boolean approve(String maPhieuDat, UserContext actor) {
        return phieuDatHangClientService.approve(maPhieuDat, UserContextMapper.toRemoteUserContext(actor));
    }
}
