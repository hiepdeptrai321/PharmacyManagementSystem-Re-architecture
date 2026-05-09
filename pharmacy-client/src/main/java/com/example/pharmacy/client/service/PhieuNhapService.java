package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.PhieuNhapClientService;
import com.example.pharmacy.client.service.RmiPhieuNhapClientService;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacy.common.model.ChiTietPhieuNhapDto;
import com.example.pharmacy.common.model.PhieuNhapDto;
import com.example.pharmacy.common.session.UserContext;
import com.example.pharmacy.client.session.UserContextMapper;

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

    public List<PhieuNhapDto> findAll() {
        return phieuNhapClientService.findAll();
    }

    public PhieuNhapDto findById(String maPhieuNhap) {
        return phieuNhapClientService.findById(maPhieuNhap);
    }

    public List<ChiTietPhieuNhapDto> findDetailsByMaPhieuNhap(String maPhieuNhap) {
        return phieuNhapClientService.findDetailsByMaPhieuNhap(maPhieuNhap);
    }
}
