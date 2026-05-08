package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.repository.PromotionRepository;
import com.example.pharmacy.server.transaction.TransactionManager;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.KhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.LoaiKhuyenMai;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TangKem;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class KhuyenMaiServiceImpl implements KhuyenMaiService {
    private final TransactionManager transactionManager;
    private final PromotionRepository promotionRepository;
    private final CodeGenerationService codeGenerationService;

    public KhuyenMaiServiceImpl(
            TransactionManager transactionManager,
            PromotionRepository promotionRepository,
            CodeGenerationService codeGenerationService
    ) {
        this.transactionManager = Objects.requireNonNull(transactionManager, "transactionManager must not be null");
        this.promotionRepository = Objects.requireNonNull(promotionRepository, "promotionRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public List<KhuyenMai> findAll() {
        return promotionRepository.findAll();
    }

    @Override
    public KhuyenMai findById(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return null;
        }
        return promotionRepository.findById(maKhuyenMai);
    }

    @Override
    public List<KhuyenMai> searchByKeyword(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim().toLowerCase();
        if (tuKhoa.isEmpty()) {
            return findAll();
        }
        return promotionRepository.searchByKeyword(tuKhoa);
    }

    @Override
    public String generateNewMaKM() {
        return codeGenerationService.nextCode(BusinessCodeType.KHUYEN_MAI);
    }

    @Override
    public List<LoaiKhuyenMai> findAllLoaiKhuyenMai() {
        return promotionRepository.findAllLoaiKhuyenMai();
    }

    @Override
    public LoaiKhuyenMai findLoaiKhuyenMaiById(String maLoaiKhuyenMai) {
        if (maLoaiKhuyenMai == null || maLoaiKhuyenMai.isBlank()) {
            return null;
        }
        return promotionRepository.findLoaiKhuyenMaiById(maLoaiKhuyenMai);
    }

    @Override
    public LoaiKhuyenMai findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai) {
        if (tenLoaiKhuyenMai == null || tenLoaiKhuyenMai.isBlank()) {
            return null;
        }
        return promotionRepository.findLoaiKhuyenMaiByTen(tenLoaiKhuyenMai);
    }

    @Override
    public List<ChiTietKhuyenMai> findChiTietByMaKM(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return List.of();
        }
        return promotionRepository.findChiTietByMaKM(maKhuyenMai);
    }

    @Override
    public List<Thuoc_SP_TangKem> findQuaTangByMaKM(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return List.of();
        }
        return promotionRepository.findQuaTangByMaKM(maKhuyenMai);
    }

    @Override
    public boolean create(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais) {
        if (khuyenMai == null) {
            return false;
        }
        if (khuyenMai.getMaKM() == null || khuyenMai.getMaKM().isBlank()) {
            khuyenMai.setMaKM(generateNewMaKM());
        }
        try {
            return transactionManager.execute(() -> {
                if (!promotionRepository.insertPromotion(khuyenMai)) {
                    return false;
                }
                saveChiTiet(khuyenMai, chiTietKhuyenMais);
                saveQuaTang(khuyenMai, quaTangKhuyenMais);
                return true;
            });
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean update(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais, List<Thuoc_SP_TangKem> quaTangKhuyenMais) {
        if (khuyenMai == null || khuyenMai.getMaKM() == null || khuyenMai.getMaKM().isBlank()) {
            return false;
        }
        try {
            return transactionManager.execute(() -> {
                if (!promotionRepository.updatePromotion(khuyenMai)) {
                    return false;
                }
                promotionRepository.deletePromotionDetailsByMaKM(khuyenMai.getMaKM());
                promotionRepository.deletePromotionGiftsByMaKM(khuyenMai.getMaKM());
                saveChiTiet(khuyenMai, chiTietKhuyenMais);
                saveQuaTang(khuyenMai, quaTangKhuyenMais);
                return true;
            });
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean deleteByMaKM(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return false;
        }
        try {
            return transactionManager.execute(() -> {
                promotionRepository.deletePromotionGiftsByMaKM(maKhuyenMai);
                promotionRepository.deletePromotionDetailsByMaKM(maKhuyenMai);
                return promotionRepository.deletePromotionById(maKhuyenMai);
            });
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public List<KhuyenMai> findActiveOn(Date ngay) {
        if (ngay == null) {
            return List.of();
        }
        return promotionRepository.findActiveOn(ngay);
    }

    @Override
    public List<KhuyenMai> findActiveInvoiceOn(Date ngay) {
        if (ngay == null) {
            return List.of();
        }
        return promotionRepository.findActiveInvoiceOn(ngay);
    }

    private void saveChiTiet(KhuyenMai khuyenMai, List<ChiTietKhuyenMai> chiTietKhuyenMais) {
        if (chiTietKhuyenMais == null) {
            return;
        }
        for (ChiTietKhuyenMai chiTietKhuyenMai : chiTietKhuyenMais) {
            if (chiTietKhuyenMai == null || chiTietKhuyenMai.getThuoc() == null) {
                continue;
            }
            chiTietKhuyenMai.setKhuyenMai(khuyenMai);
            promotionRepository.insertPromotionDetail(chiTietKhuyenMai);
        }
    }

    private void saveQuaTang(KhuyenMai khuyenMai, List<Thuoc_SP_TangKem> quaTangKhuyenMais) {
        if (quaTangKhuyenMais == null) {
            return;
        }
        for (Thuoc_SP_TangKem quaTangKhuyenMai : quaTangKhuyenMais) {
            if (quaTangKhuyenMai == null || quaTangKhuyenMai.getThuocTangKem() == null) {
                continue;
            }
            quaTangKhuyenMai.setKhuyenmai(khuyenMai);
            promotionRepository.insertPromotionGift(quaTangKhuyenMai);
        }
    }
}
