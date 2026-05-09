package com.example.pharmacy.server.service;

import com.example.pharmacy.common.model.ChiTietHoatChatDto;
import com.example.pharmacy.common.model.HoatChatDto;
import com.example.pharmacy.common.model.LoaiHangDto;
import com.example.pharmacy.common.model.ThuocTonKhoDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.util.List;

public interface ThuocService {
    List<Thuoc_SanPhamDto> findAll();

    String generateNewMaThuoc();

    List<LoaiHangDto> findAllLoaiHang();

    List<String> findAllLoaiHangNames();

    List<HoatChatDto> findAllHoatChat();

    List<ChiTietHoatChatDto> findChiTietHoatChatByMaThuoc(String maThuoc);

    boolean create(Thuoc_SanPhamDto thuoc, List<ChiTietHoatChatDto> chiTietHoatChats, String maDonViTinhCoBan);

    boolean update(Thuoc_SanPhamDto thuoc, List<ChiTietHoatChatDto> chiTietHoatChats);

    boolean softDelete(String maThuoc);

    int getTongSoLuongTonByMaThuoc(String maThuoc);

    String getTenDonViTinhCoBan(String maThuoc);

    List<ThuocTonKhoDto> getThuocTonKho();

    List<Thuoc_SP_TheoLoDto> getAllTheoLo();
}
