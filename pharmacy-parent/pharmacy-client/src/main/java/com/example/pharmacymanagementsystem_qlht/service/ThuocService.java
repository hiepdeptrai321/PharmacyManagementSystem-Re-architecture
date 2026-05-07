package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.RmiThuocClientService;
import com.example.pharmacy.client.service.ThuocClientService;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoatChat;
import com.example.pharmacymanagementsystem_qlht.model.HoatChat;
import com.example.pharmacymanagementsystem_qlht.model.LoaiHang;
import com.example.pharmacymanagementsystem_qlht.model.ThuocTonKho;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;

import java.util.List;

public class ThuocService {
    private final ThuocClientService thuocClientService =
            new RmiThuocClientService(new RmiClientProvider());

    public List<Thuoc_SanPham> findAll() {
        return thuocClientService.findAll();
    }

    public String generateNewMaThuoc() {
        return thuocClientService.generateNewMaThuoc();
    }

    public List<LoaiHang> findAllLoaiHang() {
        return thuocClientService.findAllLoaiHang();
    }

    public List<String> getAllLoaiHang() {
        return thuocClientService.findAllLoaiHangNames();
    }

    public LoaiHang findByTenLoaiHang(String tenLoaiHang) {
        if (tenLoaiHang == null || tenLoaiHang.isBlank()) {
            return null;
        }
        return findAllLoaiHang().stream()
                .filter(loaiHang -> tenLoaiHang.equalsIgnoreCase(loaiHang.getTenLoaiHang()))
                .findFirst()
                .orElse(null);
    }

    public List<HoatChat> getAllHoatChat() {
        return thuocClientService.findAllHoatChat();
    }

    public List<ChiTietHoatChat> findChiTietHoatChatByMaThuoc(String maThuoc) {
        return thuocClientService.findChiTietHoatChatByMaThuoc(maThuoc);
    }

    public boolean create(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats, String maDonViTinhCoBan) {
        return thuocClientService.create(thuoc, chiTietHoatChats, maDonViTinhCoBan);
    }

    public boolean update(Thuoc_SanPham thuoc, List<ChiTietHoatChat> chiTietHoatChats) {
        return thuocClientService.update(thuoc, chiTietHoatChats);
    }

    public boolean softDelete(String maThuoc) {
        return thuocClientService.softDelete(maThuoc);
    }

    public int getTongSoLuongTonByMaThuoc(String maThuoc) {
        return thuocClientService.getTongSoLuongTonByMaThuoc(maThuoc);
    }

    public String getTenDVTByMaThuoc(String maThuoc) {
        return thuocClientService.getTenDonViTinhCoBan(maThuoc);
    }

    public List<ThuocTonKho> getThuocTonKho() {
        return thuocClientService.getThuocTonKho();
    }

    public List<Thuoc_SP_TheoLo> getAllTheoLo() {
        return thuocClientService.getAllTheoLo();
    }

    public List<Thuoc_SanPham> searchByKeyword(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim().toLowerCase();
        if (tuKhoa.isEmpty()) {
            return findAll();
        }
        return findAll().stream()
                .filter(thuoc ->
                        containsIgnoreCase(thuoc.getMaThuoc(), tuKhoa) ||
                                containsIgnoreCase(thuoc.getTenThuoc(), tuKhoa))
                .toList();
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }
}
