package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.enums.AuditAction;
import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.common.exception.BusinessException;
import com.example.pharmacy.server.repository.PhieuDatHangRepository;
import com.example.pharmacy.server.transaction.TransactionManager;
import com.example.pharmacy.common.model.ChiTietPhieuDatHang;
import com.example.pharmacy.common.model.PhieuDatHang;

import java.util.List;
import java.util.Objects;

public class PhieuDatHangServiceImpl implements PhieuDatHangService {
    private final TransactionManager transactionManager;
    private final PhieuDatHangRepository phieuDatHangRepository;
    private final CodeGenerationService codeGenerationService;
    private final AuditService auditService;

    public PhieuDatHangServiceImpl(
            TransactionManager transactionManager,
            PhieuDatHangRepository phieuDatHangRepository,
            CodeGenerationService codeGenerationService,
            AuditService auditService
    ) {
        this.transactionManager = Objects.requireNonNull(transactionManager, "transactionManager must not be null");
        this.phieuDatHangRepository = Objects.requireNonNull(phieuDatHangRepository, "phieuDatHangRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
        this.auditService = Objects.requireNonNull(auditService, "auditService must not be null");
    }

    @Override
    public String generateNewMaPhieuDatHang() {
        return codeGenerationService.nextCode(BusinessCodeType.PHIEU_DAT_HANG);
    }

    @Override
    public String create(PhieuDatHang phieuDatHang, List<ChiTietPhieuDatHang> details, UserContext actor) {
        validate(phieuDatHang, details, actor);
        return transactionManager.execute(() -> {
            String maPhieuDat = isBlank(phieuDatHang.getMaPDat())
                    ? codeGenerationService.nextCode(BusinessCodeType.PHIEU_DAT_HANG)
                    : phieuDatHang.getMaPDat().trim();

            phieuDatHangRepository.insertHeader(phieuDatHang, maPhieuDat, actor.getEmployeeId());
            for (ChiTietPhieuDatHang detail : details) {
                phieuDatHangRepository.insertDetail(maPhieuDat, detail);
            }

            auditService.logAction(actor, AuditAction.CREATE, "PhieuDatHang", maPhieuDat,
                    "Tao phieu dat hang voi " + details.size() + " dong chi tiet.");
            return maPhieuDat;
        });
    }

    @Override
    public List<PhieuDatHang> findAll() {
        return phieuDatHangRepository.findAll();
    }

    @Override
    public PhieuDatHang findById(String maPhieuDat) {
        if (isBlank(maPhieuDat)) {
            return null;
        }
        return phieuDatHangRepository.findById(maPhieuDat.trim());
    }

    @Override
    public List<ChiTietPhieuDatHang> findDetailsByMaPhieuDat(String maPhieuDat) {
        if (isBlank(maPhieuDat)) {
            return List.of();
        }
        return phieuDatHangRepository.findDetailsByMaPhieuDat(maPhieuDat.trim());
    }

    @Override
    public boolean approve(String maPhieuDat, UserContext actor) {
        if (isBlank(maPhieuDat)) {
            throw new BusinessException("Ma phieu dat hang khong hop le.");
        }
        if (actor == null || isBlank(actor.getEmployeeId())) {
            throw new BusinessException("UserContext khong hop le de duyet phieu dat hang.");
        }

        return transactionManager.execute(() -> {
            boolean approved = phieuDatHangRepository.approve(maPhieuDat.trim());
            auditService.logAction(actor, AuditAction.APPROVE, "PhieuDatHang", maPhieuDat.trim(),
                    approved ? "Duyet phieu dat hang thanh cong." : "Duyet phieu dat hang nhung chua du hang.");
            return approved;
        });
    }

    private void validate(PhieuDatHang phieuDatHang, List<ChiTietPhieuDatHang> details, UserContext actor) {
        if (actor == null || isBlank(actor.getEmployeeId())) {
            throw new BusinessException("UserContext khong hop le de tao phieu dat hang.");
        }
        if (phieuDatHang == null) {
            throw new BusinessException("PhieuDatHang khong duoc null.");
        }
        if (phieuDatHang.getKhachHang() == null || isBlank(phieuDatHang.getKhachHang().getMaKH())) {
            throw new BusinessException("Khach hang bat buoc phai co.");
        }
        if (details == null || details.isEmpty()) {
            throw new BusinessException("Phieu dat hang phai co it nhat mot dong chi tiet.");
        }
        for (ChiTietPhieuDatHang detail : details) {
            if (detail == null || detail.getThuoc() == null || isBlank(detail.getThuoc().getMaThuoc()) || isBlank(detail.getDvt())) {
                throw new BusinessException("Chi tiet phieu dat hang thieu ma thuoc hoac ma don vi tinh.");
            }
            if (detail.getSoLuong() <= 0) {
                throw new BusinessException("So luong dat phai lon hon 0.");
            }
            if (detail.getDonGia() < 0 || detail.getGiamGia() < 0) {
                throw new BusinessException("Don gia va giam gia khong duoc am.");
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
