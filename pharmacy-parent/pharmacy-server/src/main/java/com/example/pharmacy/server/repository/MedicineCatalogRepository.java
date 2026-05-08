package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.request.CreateThuocRequest;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;
import com.example.pharmacymanagementsystem_qlht.model.HoatChat;
import com.example.pharmacymanagementsystem_qlht.model.LoaiHang;
import com.example.pharmacymanagementsystem_qlht.model.ThuocTonKho;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;

import java.util.List;

public interface MedicineCatalogRepository {
    void insertMedicine(String maThuoc, CreateThuocRequest request);

    void insertBaseUnit(String maThuoc, CreateThuocRequest request);

    List<Thuoc_SanPham> findAllMedicines();

    List<LoaiHang> findAllLoaiHang();

    List<String> findAllLoaiHangNames();

    List<HoatChat> findAllHoatChat();

    List<ChiTietHoatChat> findChiTietHoatChatByMaThuoc(String maThuoc);

    boolean insertMedicine(Thuoc_SanPham thuoc);

    void insertBaseUnit(String maThuoc, String maDonViTinhCoBan);

    void insertChiTietHoatChat(String maThuoc, ChiTietHoatChat chiTietHoatChat);

    boolean updateMedicine(Thuoc_SanPham thuoc);

    void updateChiTietHoatChat(String maThuoc, ChiTietHoatChat chiTietHoatChat);

    void deleteChiTietHoatChat(String maThuoc, String maHoatChat);

    boolean softDeleteMedicine(String maThuoc);

    int getTongSoLuongTonByMaThuoc(String maThuoc);

    String getTenDonViTinhCoBan(String maThuoc);

    List<ThuocTonKho> getThuocTonKho();

    List<Thuoc_SP_TheoLo> findAllLots();
}
