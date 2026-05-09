package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.model.ChiTietKhuyenMaiDto;
import com.example.pharmacy.common.model.KhuyenMaiDto;
import com.example.pharmacy.common.model.LoaiKhuyenMaiDto;
import com.example.pharmacy.common.model.Thuoc_SP_TangKemDto;

import java.sql.Date;
import java.util.List;

public interface PromotionRepository {
    List<KhuyenMaiDto> findAll();

    KhuyenMaiDto findById(String maKhuyenMai);

    List<KhuyenMaiDto> searchByKeyword(String keyword);

    List<LoaiKhuyenMaiDto> findAllLoaiKhuyenMai();

    LoaiKhuyenMaiDto findLoaiKhuyenMaiById(String maLoaiKhuyenMai);

    LoaiKhuyenMaiDto findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai);

    List<ChiTietKhuyenMaiDto> findChiTietByMaKM(String maKhuyenMai);

    List<Thuoc_SP_TangKemDto> findQuaTangByMaKM(String maKhuyenMai);

    boolean insertPromotion(KhuyenMaiDto khuyenMai);

    boolean updatePromotion(KhuyenMaiDto khuyenMai);

    boolean deletePromotionById(String maKhuyenMai);

    void insertPromotionDetail(ChiTietKhuyenMaiDto chiTietKhuyenMai);

    void deletePromotionDetailsByMaKM(String maKhuyenMai);

    void insertPromotionGift(Thuoc_SP_TangKemDto quaTangKhuyenMai);

    void deletePromotionGiftsByMaKM(String maKhuyenMai);

    List<KhuyenMaiDto> findActiveOn(Date ngay);

    List<KhuyenMaiDto> findActiveInvoiceOn(Date ngay);
}
