package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.CreatePhieuDoiRequest;
import com.example.pharmacy.common.request.CreatePhieuTraRequest;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.ChiTietPhieuDoiHangDto;
import com.example.pharmacy.common.model.ChiTietPhieuTraHangDto;
import com.example.pharmacy.common.model.HoaDonDto;
import com.example.pharmacy.common.model.PhieuDoiHangDto;
import com.example.pharmacy.common.model.PhieuTraHangDto;

import java.util.List;

public interface DoiTraService {
    HoaDonDto findHoaDonGocForDoiTra(String maHoaDon);

    List<ChiTietHoaDonDto> findHoaDonDetailsForDoiTra(String maHoaDon);

    int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh);

    int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh);

    String createPhieuDoi(CreatePhieuDoiRequest request, UserContext actor);

    String createPhieuTra(CreatePhieuTraRequest request, UserContext actor);

    void attachKhachHangToHoaDon(String maHoaDon, String maKhachHang, UserContext actor);

    List<PhieuDoiHangDto> findAllPhieuDoi();

    PhieuDoiHangDto findPhieuDoiById(String maPhieuDoi);

    List<ChiTietPhieuDoiHangDto> findChiTietPhieuDoiByMaPD(String maPhieuDoi);

    List<PhieuTraHangDto> findAllPhieuTra();

    PhieuTraHangDto findPhieuTraById(String maPhieuTra);

    List<ChiTietPhieuTraHangDto> findChiTietPhieuTraByMaPT(String maPhieuTra);
}
