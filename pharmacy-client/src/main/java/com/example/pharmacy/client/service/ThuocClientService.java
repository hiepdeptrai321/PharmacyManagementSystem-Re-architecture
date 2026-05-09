package com.example.pharmacy.client.service;

import com.example.pharmacy.common.model.ChiTietHoatChat;
import com.example.pharmacy.common.model.HoatChat;
import com.example.pharmacy.common.model.LoaiHang;
import com.example.pharmacy.common.model.ThuocTonKho;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLo;
import com.example.pharmacy.common.model.Thuoc_SanPham;

import java.util.List;

public interface ThuocClientService {
    List<Thuoc_SanPham> findAll();

    String generateNewMaThuoc();

    List<LoaiHang> findAllLoaiHang();

    List<String> findAllLoaiHangNames();

    List<HoatChat> findAllHoatChat();

    List<ChiTietHoatChat> findChiTietHoatChatByMaThuoc(String maThuoc);

    boolean create(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats, String maDonViTinhCoBan);

    boolean update(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats);

    boolean softDelete(String maThuoc);

    int getTongSoLuongTonByMaThuoc(String maThuoc);

    String getTenDonViTinhCoBan(String maThuoc);

    List<ThuocTonKho> getThuocTonKho();

    List<Thuoc_SP_TheoLo> getAllTheoLo();
}
