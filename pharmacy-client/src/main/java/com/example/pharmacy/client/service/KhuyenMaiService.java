package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.KhuyenMaiClientService;
import com.example.pharmacy.client.service.RmiKhuyenMaiClientService;
import com.example.pharmacy.common.model.ChiTietKhuyenMaiDto;
import com.example.pharmacy.common.model.KhuyenMaiDto;
import com.example.pharmacy.common.model.LoaiKhuyenMaiDto;
import com.example.pharmacy.common.model.Thuoc_SP_TangKemDto;

import java.sql.Date;
import java.util.List;

public class KhuyenMaiService {
    private final KhuyenMaiClientService khuyenMaiClientService =
            new RmiKhuyenMaiClientService(new RmiClientProvider());

    public List<KhuyenMaiDto> findAll() {
        return khuyenMaiClientService.findAll();
    }

    public KhuyenMaiDto findById(String maKhuyenMai) {
        return khuyenMaiClientService.findById(maKhuyenMai);
    }

    public List<KhuyenMaiDto> searchByKeyword(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim().toLowerCase();
        return tuKhoa.isEmpty() ? findAll() : khuyenMaiClientService.searchByKeyword(tuKhoa);
    }

    public String generateNewMaKM() {
        return khuyenMaiClientService.generateNewMaKM();
    }

    public List<LoaiKhuyenMaiDto> findAllLoaiKhuyenMai() {
        return khuyenMaiClientService.findAllLoaiKhuyenMai();
    }

    public LoaiKhuyenMaiDto findLoaiKhuyenMaiById(String maLoaiKhuyenMai) {
        return khuyenMaiClientService.findLoaiKhuyenMaiById(maLoaiKhuyenMai);
    }

    public LoaiKhuyenMaiDto findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai) {
        return khuyenMaiClientService.findLoaiKhuyenMaiByTen(tenLoaiKhuyenMai);
    }

    public List<ChiTietKhuyenMaiDto> findChiTietByMaKM(String maKhuyenMai) {
        return khuyenMaiClientService.findChiTietByMaKM(maKhuyenMai);
    }

    public List<Thuoc_SP_TangKemDto> findQuaTangByMaKM(String maKhuyenMai) {
        return khuyenMaiClientService.findQuaTangByMaKM(maKhuyenMai);
    }

    public boolean create(KhuyenMaiDto khuyenMai, List<ChiTietKhuyenMaiDto> chiTietKhuyenMais, List<Thuoc_SP_TangKemDto> quaTangKhuyenMais) {
        return khuyenMaiClientService.create(khuyenMai, chiTietKhuyenMais, quaTangKhuyenMais);
    }

    public boolean update(KhuyenMaiDto khuyenMai, List<ChiTietKhuyenMaiDto> chiTietKhuyenMais, List<Thuoc_SP_TangKemDto> quaTangKhuyenMais) {
        return khuyenMaiClientService.update(khuyenMai, chiTietKhuyenMais, quaTangKhuyenMais);
    }

    public boolean deleteByMaKM(String maKhuyenMai) {
        return khuyenMaiClientService.deleteByMaKM(maKhuyenMai);
    }

    public List<KhuyenMaiDto> findActiveOn(Date ngay) {
        return khuyenMaiClientService.findActiveOn(ngay);
    }

    public List<KhuyenMaiDto> findActiveInvoiceOn(Date ngay) {
        return khuyenMaiClientService.findActiveInvoiceOn(ngay);
    }
}
