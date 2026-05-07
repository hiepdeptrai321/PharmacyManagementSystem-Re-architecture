package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.PhieuNhapClientService;
import com.example.pharmacy.client.service.RmiPhieuNhapClientService;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuNhap;
import com.example.pharmacymanagementsystem_qlht.model.PhieuNhap;
import com.example.pharmacymanagementsystem_qlht.session.UserContext;
import com.example.pharmacymanagementsystem_qlht.session.UserContextMapper;

import java.util.List;

public class PhieuNhapService {
    private final PhieuNhapClientService phieuNhapClientService =
            new RmiPhieuNhapClientService(new RmiClientProvider());

    public String generateNewMaPhieuNhap() {
        return phieuNhapClientService.generateNewMaPhieuNhap();
    }

    public String createPurchaseOrder(PhieuNhapRequest request, UserContext actor) {
        return phieuNhapClientService.createPurchaseOrder(request, UserContextMapper.toRemoteUserContext(actor));
    }

    public List<PhieuNhap> findAll() {
        return phieuNhapClientService.findAll();
    }

    public PhieuNhap findById(String maPhieuNhap) {
        return phieuNhapClientService.findById(maPhieuNhap);
    }

    public List<ChiTietPhieuNhap> findDetailsByMaPhieuNhap(String maPhieuNhap) {
        return phieuNhapClientService.findDetailsByMaPhieuNhap(maPhieuNhap);
    }
}
