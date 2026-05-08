package com.example.pharmacy.client.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.CreatePhieuDoiRequest;
import com.example.pharmacy.common.request.CreatePhieuTraRequest;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDoiHang;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuTraHang;
import com.example.pharmacymanagementsystem_qlht.model.HoaDon;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDoiHang;
import com.example.pharmacymanagementsystem_qlht.model.PhieuTraHang;

import java.util.List;

public interface DoiTraClientService {
    HoaDon findHoaDonGocForDoiTra(String maHoaDon);

    List<ChiTietHoaDon> findHoaDonDetailsForDoiTra(String maHoaDon);

    int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh);

    int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh);

    String createPhieuDoi(CreatePhieuDoiRequest request, UserContext actor);

    String createPhieuTra(CreatePhieuTraRequest request, UserContext actor);

    void attachKhachHangToHoaDon(String maHoaDon, String maKhachHang, UserContext actor);

    List<PhieuDoiHang> findAllPhieuDoi();

    PhieuDoiHang findPhieuDoiById(String maPhieuDoi);

    List<ChiTietPhieuDoiHang> findChiTietPhieuDoiByMaPD(String maPhieuDoi);

    List<PhieuTraHang> findAllPhieuTra();

    PhieuTraHang findPhieuTraById(String maPhieuTra);

    List<ChiTietPhieuTraHang> findChiTietPhieuTraByMaPT(String maPhieuTra);
}
