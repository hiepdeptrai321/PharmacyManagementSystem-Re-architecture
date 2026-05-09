package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.KhuyenMaiClientService;
import com.example.pharmacy.client.service.RmiKhuyenMaiClientService;
import com.example.pharmacy.common.model.ChiTietKhuyenMai;
import com.example.pharmacy.common.model.KhuyenMai;
import com.example.pharmacy.common.model.LoaiKhuyenMai;
import com.example.pharmacy.common.model.Thuoc_SP_TangKem;

import java.sql.Date;
import java.util.List;

public class KhuyenMaiService {
    private final KhuyenMaiClientService khuyenMaiClientService =
            new RmiKhuyenMaiClientService(new RmiClientProvider());

    public List<KhuyenMai> findAll() {
        return khuyenMaiClientService.findAll();
    }

    public KhuyenMai findById(String maKhuyenMai) {
        return khuyenMaiClientService.findById(maKhuyenMai);
    }

    public List<KhuyenMai> searchByKeyword(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim().toLowerCase();
        return tuKhoa.isEmpty() ? findAll() : khuyenMaiClientService.searchByKeyword(tuKhoa);
    }

    public String generateNewMaKM() {
        return khuyenMaiClientService.generateNewMaKM();
    }

    public List<LoaiKhuyenMai> findAllLoaiKhuyenMai() {
        return khuyenMaiClientService.findAllLoaiKhuyenMai();
    }

    public LoaiKhuyenMai findLoaiKhuyenMaiById(String maLoaiKhuyenMai) {
        return khuyenMaiClientService.findLoaiKhuyenMaiById(maLoaiKhuyenMai);
    }

    public LoaiKhuyenMai findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai) {
        return khuyenMaiClientService.findLoaiKhuyenMaiByTen(tenLoaiKhuyenMai);
    }

    public List<ChiTietKhuyenMai> findChiTietByMaKM(String maKhuyenMai) {
        return khuyenMaiClientService.findChiTietByMaKM(maKhuyenMai);
    }

    public List<Thuoc_SP_TangKem> findQuaTangByMaKM(String maKhuyenMai) {
        return khuyenMaiClientService.findQuaTangByMaKM(maKhuyenMai);
    }

    public boolean create(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais) {
        return khuyenMaiClientService.create(khuyenMai, chiTietKhuyenMais, quaTangKhuyenMais);
    }

    public boolean update(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais) {
        return khuyenMaiClientService.update(khuyenMai, chiTietKhuyenMais, quaTangKhuyenMais);
    }

    public boolean deleteByMaKM(String maKhuyenMai) {
        return khuyenMaiClientService.deleteByMaKM(maKhuyenMai);
    }

    public List<KhuyenMai> findActiveOn(Date ngay) {
        return khuyenMaiClientService.findActiveOn(ngay);
    }

    public List<KhuyenMai> findActiveInvoiceOn(Date ngay) {
        return khuyenMaiClientService.findActiveInvoiceOn(ngay);
    }
}
