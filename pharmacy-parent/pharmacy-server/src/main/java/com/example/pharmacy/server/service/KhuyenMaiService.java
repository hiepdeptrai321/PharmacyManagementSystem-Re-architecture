package com.example.pharmacy.server.service;

import com.example.pharmacymanagementsystem_qlht.model.ChiTietKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.LoaiKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TangKem;

import java.sql.Date;
import java.util.List;

public interface KhuyenMaiService {
    List<KhuyenMai> findAll();

    KhuyenMai findById(String maKhuyenMai);

    List<KhuyenMai> searchByKeyword(String keyword);

    String generateNewMaKM();

    List<LoaiKhuyenMai> findAllLoaiKhuyenMai();

    LoaiKhuyenMai findLoaiKhuyenMaiById(String maLoaiKhuyenMai);

    LoaiKhuyenMai findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai);

    List<ChiTietKhuyenMai> findChiTietByMaKM(String maKhuyenMai);

    List<Thuoc_SP_TangKem> findQuaTangByMaKM(String maKhuyenMai);

    boolean create(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais);

    boolean update(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais);

    boolean deleteByMaKM(String maKhuyenMai);

    List<KhuyenMai> findActiveOn(Date ngay);

    List<KhuyenMai> findActiveInvoiceOn(Date ngay);
}
