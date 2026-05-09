package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.repository.PromotionRepository;
import com.example.pharmacy.server.transaction.TransactionManager;
import com.example.pharmacy.common.model.ChiTietKhuyenMaiDto;
import com.example.pharmacy.common.model.KhuyenMaiDto;
import com.example.pharmacy.common.model.LoaiKhuyenMaiDto;
import com.example.pharmacy.common.model.Thuoc_SP_TangKemDto;

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
    public List<KhuyenMaiDto> findAll() {
        return promotionRepository.findAll();
    }

    @Override
    public KhuyenMaiDto findById(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return null;
        }
        return promotionRepository.findById(maKhuyenMai);
    }

    @Override
    public List<KhuyenMaiDto> searchByKeyword(String keyword) {
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
    public List<LoaiKhuyenMaiDto> findAllLoaiKhuyenMai() {
        return promotionRepository.findAllLoaiKhuyenMai();
    }

    @Override
    public LoaiKhuyenMaiDto findLoaiKhuyenMaiById(String maLoaiKhuyenMai) {
        if (maLoaiKhuyenMai == null || maLoaiKhuyenMai.isBlank()) {
            return null;
        }
        return promotionRepository.findLoaiKhuyenMaiById(maLoaiKhuyenMai);
    }

    @Override
    public LoaiKhuyenMaiDto findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai) {
        if (tenLoaiKhuyenMai == null || tenLoaiKhuyenMai.isBlank()) {
            return null;
        }
        return promotionRepository.findLoaiKhuyenMaiByTen(tenLoaiKhuyenMai);
    }

    @Override
    public List<ChiTietKhuyenMaiDto> findChiTietByMaKM(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return List.of();
        }
        return promotionRepository.findChiTietByMaKM(maKhuyenMai);
    }

    @Override
    public List<Thuoc_SP_TangKemDto> findQuaTangByMaKM(String maKhuyenMai) {
        if (maKhuyenMai == null || maKhuyenMai.isBlank()) {
            return List.of();
        }
        return promotionRepository.findQuaTangByMaKM(maKhuyenMai);
    }

    @Override
    public boolean create(KhuyenMaiDto khuyenMai, List<ChiTietKhuyenMaiDto> chiTietKhuyenMais, List<Thuoc_SP_TangKemDto> quaTangKhuyenMais) {
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
    public boolean update(KhuyenMaiDto khuyenMai, List<ChiTietKhuyenMaiDto> chiTietKhuyenMais, List<Thuoc_SP_TangKemDto> quaTangKhuyenMais) {
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
    public List<KhuyenMaiDto> findActiveOn(Date ngay) {
        if (ngay == null) {
            return List.of();
        }
        return promotionRepository.findActiveOn(ngay);
    }

    @Override
    public List<KhuyenMaiDto> findActiveInvoiceOn(Date ngay) {
        if (ngay == null) {
            return List.of();
        }
        return promotionRepository.findActiveInvoiceOn(ngay);
    }

    private void saveChiTiet(KhuyenMaiDto khuyenMai, List<ChiTietKhuyenMaiDto> chiTietKhuyenMais) {
        if (chiTietKhuyenMais == null) {
            return;
        }
        for (ChiTietKhuyenMaiDto chiTietKhuyenMai : chiTietKhuyenMais) {
            if (chiTietKhuyenMai == null || chiTietKhuyenMai.getThuoc() == null) {
                continue;
            }
            chiTietKhuyenMai.setKhuyenMai(khuyenMai);
            promotionRepository.insertPromotionDetail(chiTietKhuyenMai);
        }
    }

    private void saveQuaTang(KhuyenMaiDto khuyenMai, List<Thuoc_SP_TangKemDto> quaTangKhuyenMais) {
        if (quaTangKhuyenMais == null) {
            return;
        }
        for (Thuoc_SP_TangKemDto quaTangKhuyenMai : quaTangKhuyenMais) {
            if (quaTangKhuyenMai == null || quaTangKhuyenMai.getThuocTangKem() == null) {
                continue;
            }
            quaTangKhuyenMai.setKhuyenmai(khuyenMai);
            promotionRepository.insertPromotionGift(quaTangKhuyenMai);
        }
    }
}
