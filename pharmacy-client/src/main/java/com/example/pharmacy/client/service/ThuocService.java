package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.RmiThuocClientService;
import com.example.pharmacy.client.service.interfa.ThuocClientService;
import com.example.pharmacy.common.model.ChiTietHoatChatDto;
import com.example.pharmacy.common.model.HoatChatDto;
import com.example.pharmacy.common.model.LoaiHangDto;
import com.example.pharmacy.common.model.ThuocTonKhoDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.util.List;

public class ThuocService {
    private final ThuocClientService thuocClientService =
            new RmiThuocClientService(new RmiClientProvider());

    public List<Thuoc_SanPhamDto> findAll() {
        return thuocClientService.findAll();
    }

    public String generateNewMaThuoc() {
        return thuocClientService.generateNewMaThuoc();
    }

    public List<LoaiHangDto> findAllLoaiHang() {
        return thuocClientService.findAllLoaiHang();
    }

    public List<String> getAllLoaiHang() {
        return thuocClientService.findAllLoaiHangNames();
    }

    public LoaiHangDto findByTenLoaiHang(String tenLoaiHang) {
        if (tenLoaiHang == null || tenLoaiHang.isBlank()) {
            return null;
        }
        return findAllLoaiHang().stream()
                .filter(loaiHang -> tenLoaiHang.equalsIgnoreCase(loaiHang.getTenLoaiHang()))
                .findFirst()
                .orElse(null);
    }

    public List<HoatChatDto> getAllHoatChat() {
        return thuocClientService.findAllHoatChat();
    }

    public List<ChiTietHoatChatDto> findChiTietHoatChatByMaThuoc(String maThuoc) {
        return thuocClientService.findChiTietHoatChatByMaThuoc(maThuoc);
    }

    public boolean create(Thuoc_SanPhamDto thuoc, List<ChiTietHoatChatDto> chiTietHoatChats, String maDonViTinhCoBan) {
        return thuocClientService.create(thuoc, chiTietHoatChats, maDonViTinhCoBan);
    }

    public boolean update(Thuoc_SanPhamDto thuoc, List<ChiTietHoatChatDto> chiTietHoatChats) {
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

    public List<ThuocTonKhoDto> getThuocTonKho() {
        return thuocClientService.getThuocTonKho();
    }

    public List<Thuoc_SP_TheoLoDto> getAllTheoLo() {
        return thuocClientService.getAllTheoLo();
    }

    public List<Thuoc_SanPhamDto> searchByKeyword(String keyword) {
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
