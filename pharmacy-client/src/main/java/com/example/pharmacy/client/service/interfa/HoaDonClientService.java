package com.example.pharmacy.client.service.interfa;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.common.request.HoaDonSearchRequest;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.HoaDonDto;

import java.util.List;

public interface HoaDonClientService {
    String generateNewMaHoaDon();

    String createInvoice(CreateHoaDonRequest request, UserContext actor);

    List<HoaDonDto> findAll();

    HoaDonDto findById(String maHoaDon);

    List<ChiTietHoaDonDto> findDetailsByMaHD(String maHoaDon);

    List<HoaDonDto> search(HoaDonSearchRequest request);
}
