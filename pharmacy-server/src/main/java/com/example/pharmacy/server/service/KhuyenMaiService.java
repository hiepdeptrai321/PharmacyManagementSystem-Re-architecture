package com.example.pharmacy.server.service;

import com.example.pharmacy.common.model.ChiTietKhuyenMaiDto;
import com.example.pharmacy.common.model.KhuyenMaiDto;
import com.example.pharmacy.common.model.LoaiKhuyenMaiDto;
import com.example.pharmacy.common.model.Thuoc_SP_TangKemDto;

import java.sql.Date;
import java.util.List;

public interface KhuyenMaiService {
    List<KhuyenMaiDto> findAll();

    KhuyenMaiDto findById(String maKhuyenMai);

    List<KhuyenMaiDto> searchByKeyword(String keyword);

    String generateNewMaKM();

    List<LoaiKhuyenMaiDto> findAllLoaiKhuyenMai();

    LoaiKhuyenMaiDto findLoaiKhuyenMaiById(String maLoaiKhuyenMai);

    LoaiKhuyenMaiDto findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai);

    List<ChiTietKhuyenMaiDto> findChiTietByMaKM(String maKhuyenMai);

    List<Thuoc_SP_TangKemDto> findQuaTangByMaKM(String maKhuyenMai);

    boolean create(KhuyenMaiDto khuyenMai, List<ChiTietKhuyenMaiDto> chiTietKhuyenMais, List<Thuoc_SP_TangKemDto> quaTangKhuyenMais);

    boolean update(KhuyenMaiDto khuyenMai, List<ChiTietKhuyenMaiDto> chiTietKhuyenMais, List<Thuoc_SP_TangKemDto> quaTangKhuyenMais);

    boolean deleteByMaKM(String maKhuyenMai);

    List<KhuyenMaiDto> findActiveOn(Date ngay);

    List<KhuyenMaiDto> findActiveInvoiceOn(Date ngay);
}
