package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.common.request.HoaDonSearchRequest;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import com.example.pharmacymanagementsystem_qlht.model.HoaDon;

import java.util.List;

public interface HoaDonService {
    String generateNewMaHoaDon();

    String createInvoice(CreateHoaDonRequest request, UserContext actor);

    List<HoaDon> findAll();

    HoaDon findById(String maHoaDon);

    List<ChiTietHoaDon> findDetailsByMaHD(String maHoaDon);

    List<HoaDon> search(HoaDonSearchRequest request);
}
