package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.model.ChiTietKhuyenMai;
import com.example.pharmacy.common.model.KhuyenMai;
import com.example.pharmacy.common.model.LoaiKhuyenMai;
import com.example.pharmacy.common.model.Thuoc_SP_TangKem;

import java.sql.Date;
import java.util.List;

public interface PromotionRepository {
    List<KhuyenMai> findAll();

    KhuyenMai findById(String maKhuyenMai);

    List<KhuyenMai> searchByKeyword(String keyword);

    List<LoaiKhuyenMai> findAllLoaiKhuyenMai();

    LoaiKhuyenMai findLoaiKhuyenMaiById(String maLoaiKhuyenMai);

    LoaiKhuyenMai findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai);

    List<ChiTietKhuyenMai> findChiTietByMaKM(String maKhuyenMai);

    List<Thuoc_SP_TangKem> findQuaTangByMaKM(String maKhuyenMai);

    boolean insertPromotion(KhuyenMai khuyenMai);

    boolean updatePromotion(KhuyenMai khuyenMai);

    boolean deletePromotionById(String maKhuyenMai);

    void insertPromotionDetail(ChiTietKhuyenMai chiTietKhuyenMai);

    void deletePromotionDetailsByMaKM(String maKhuyenMai);

    void insertPromotionGift(Thuoc_SP_TangKem quaTangKhuyenMai);

    void deletePromotionGiftsByMaKM(String maKhuyenMai);

    List<KhuyenMai> findActiveOn(Date ngay);

    List<KhuyenMai> findActiveInvoiceOn(Date ngay);
}
