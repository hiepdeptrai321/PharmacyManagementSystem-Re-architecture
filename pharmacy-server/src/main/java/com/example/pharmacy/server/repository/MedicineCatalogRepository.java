package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.request.CreateThuocRequest;
import com.example.pharmacy.common.model.ChiTietHoatChatDto;
import com.example.pharmacy.common.model.HoatChatDto;
import com.example.pharmacy.common.model.LoaiHangDto;
import com.example.pharmacy.common.model.ThuocTonKhoDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.util.List;

public interface MedicineCatalogRepository {
    void insertMedicine(String maThuoc, CreateThuocRequest request);

    void insertBaseUnit(String maThuoc, CreateThuocRequest request);

    List<Thuoc_SanPhamDto> findAllMedicines();

    List<LoaiHangDto> findAllLoaiHang();

    List<String> findAllLoaiHangNames();

    List<HoatChatDto> findAllHoatChat();

    List<ChiTietHoatChatDto> findChiTietHoatChatByMaThuoc(String maThuoc);

    boolean insertMedicine(Thuoc_SanPhamDto thuoc);

    void insertBaseUnit(String maThuoc, String maDonViTinhCoBan);

    void insertChiTietHoatChat(String maThuoc, ChiTietHoatChatDto chiTietHoatChat);

    boolean updateMedicine(Thuoc_SanPhamDto thuoc);

    void updateChiTietHoatChat(String maThuoc, ChiTietHoatChatDto chiTietHoatChat);

    void deleteChiTietHoatChat(String maThuoc, String maHoatChat);

    boolean softDeleteMedicine(String maThuoc);

    int getTongSoLuongTonByMaThuoc(String maThuoc);

    String getTenDonViTinhCoBan(String maThuoc);

    List<ThuocTonKhoDto> getThuocTonKho();

    List<Thuoc_SP_TheoLoDto> findAllLots();
}
