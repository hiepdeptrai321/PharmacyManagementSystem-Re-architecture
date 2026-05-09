package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.HoaDonClientService;
import com.example.pharmacy.client.service.RmiHoaDonClientService;
import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.common.request.HoaDonSearchRequest;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.HoaDonDto;
import com.example.pharmacy.common.session.UserContext;
import com.example.pharmacy.client.session.UserContextMapper;

import java.util.List;

public class HoaDonService {
    private final HoaDonClientService hoaDonClientService =
            new RmiHoaDonClientService(new RmiClientProvider());

    public String generateNewMaHoaDon() {
        return hoaDonClientService.generateNewMaHoaDon();
    }

    public String createInvoice(CreateHoaDonRequest request, UserContext actor) {
        return hoaDonClientService.createInvoice(request, UserContextMapper.toRemoteUserContext(actor));
    }

    public List<HoaDonDto> findAll() {
        return hoaDonClientService.findAll();
    }

    public HoaDonDto findById(String maHoaDon) {
        return hoaDonClientService.findById(maHoaDon);
    }

    public List<ChiTietHoaDonDto> findDetailsByMaHD(String maHoaDon) {
        return hoaDonClientService.findDetailsByMaHD(maHoaDon);
    }

    public List<HoaDonDto> search(HoaDonSearchRequest request) {
        return hoaDonClientService.search(request);
    }
}
