package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.enums.AuditAction;
import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.common.exception.BusinessException;
import com.example.pharmacy.common.request.PhieuNhapItemRequest;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacy.server.repository.PurchaseOrderRepository;
import com.example.pharmacy.server.transaction.TransactionManager;
import com.example.pharmacy.common.model.ChiTietPhieuNhapDto;
import com.example.pharmacy.common.model.PhieuNhapDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class PhieuNhapServiceImpl implements PhieuNhapService {
    private final TransactionManager transactionManager;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CodeGenerationService codeGenerationService;
    private final AuditService auditService;
    private final AtomicBoolean lotSequenceSynced = new AtomicBoolean(false);

    public PhieuNhapServiceImpl(
            TransactionManager transactionManager,
            PurchaseOrderRepository purchaseOrderRepository,
            CodeGenerationService codeGenerationService,
            AuditService auditService
    ) {
        this.transactionManager = Objects.requireNonNull(transactionManager, "transactionManager must not be null");
        this.purchaseOrderRepository = Objects.requireNonNull(purchaseOrderRepository, "purchaseOrderRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
        this.auditService = Objects.requireNonNull(auditService, "auditService must not be null");
    }

    @Override
    public String generateNewMaPhieuNhap() {
        return codeGenerationService.nextCode(BusinessCodeType.PHIEU_NHAP);
    }

    @Override
    public String createPurchaseOrder(PhieuNhapRequest request, UserContext actor) {
        validatePurchaseOrderRequest(request, actor);
        syncLotSequenceIfNeeded();
        return transactionManager.execute(() -> {
            String maPhieuNhap = isBlank(request.getMaPhieuNhap())
                    ? codeGenerationService.nextCode(BusinessCodeType.PHIEU_NHAP)
                    : request.getMaPhieuNhap().trim();

            purchaseOrderRepository.insertHeader(maPhieuNhap, actor.getEmployeeId(), request);

            for (PhieuNhapItemRequest item : request.getItems()) {
                String maLo = isBlank(item.getMaLo())
                        ? codeGenerationService.nextCode(BusinessCodeType.LO_THUOC)
                        : item.getMaLo().trim();
                purchaseOrderRepository.insertDetail(maPhieuNhap, maLo, item);
                purchaseOrderRepository.insertLot(maPhieuNhap, maLo, item);
                purchaseOrderRepository.updatePricing(item.getMaThuoc(), item.getMaDvt(), item);
            }

            auditService.logAction(actor, AuditAction.IMPORT, "PhieuNhapDto", maPhieuNhap,
                    "Tao phieu nhap voi " + request.getItems().size() + " dong chi tiet.");
            return maPhieuNhap;
        });
    }

    @Override
    public List<PhieuNhapDto> findAll() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public PhieuNhapDto findById(String maPhieuNhap) {
        if (isBlank(maPhieuNhap)) {
            return null;
        }
        return purchaseOrderRepository.findById(maPhieuNhap.trim());
    }

    @Override
    public List<ChiTietPhieuNhapDto> findDetailsByMaPhieuNhap(String maPhieuNhap) {
        if (isBlank(maPhieuNhap)) {
            return List.of();
        }
        return purchaseOrderRepository.findDetailsByMaPhieuNhap(maPhieuNhap.trim());
    }

    private void validatePurchaseOrderRequest(PhieuNhapRequest request, UserContext actor) {
        if (actor == null || isBlank(actor.getEmployeeId())) {
            throw new BusinessException("UserContext khong hop le de tao phieu nhap.");
        }
        if (request == null) {
            throw new BusinessException("PhieuNhapRequest khong duoc null.");
        }
        if (request.getNgayNhap() == null || isBlank(request.getMaNhaCungCap())) {
            throw new BusinessException("Ngay nhap va nha cung cap bat buoc phai co.");
        }
        List<PhieuNhapItemRequest> items = request.getItems();
        if (items == null || items.isEmpty()) {
            throw new BusinessException("Phieu nhap phai co it nhat mot dong chi tiet.");
        }
        for (PhieuNhapItemRequest item : items) {
            if (isBlank(item.getMaThuoc()) || isBlank(item.getMaDvt())) {
                throw new BusinessException("Chi tiet phieu nhap thieu ma thuoc hoac ma don vi tinh.");
            }
            if (item.getSoLuong() <= 0) {
                throw new BusinessException("So luong nhap phai lon hon 0.");
            }
            if (item.getGiaNhap() == null || item.getGiaNhap().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Gia nhap khong hop le.");
            }
            if (item.getChietKhau() != null && item.getChietKhau().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Chiet khau khong duoc am.");
            }
            if (item.getThue() != null && item.getThue().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Thue khong duoc am.");
            }
            if (item.getNsx() != null && item.getHsd() != null && item.getHsd().isBefore(item.getNsx())) {
                throw new BusinessException("Han su dung khong duoc som hon ngay san xuat.");
            }
        }
    }

    private void syncLotSequenceIfNeeded() {
        if (lotSequenceSynced.compareAndSet(false, true)) {
            try {
                long maxLot = purchaseOrderRepository.findMaxLotNumber();
                if (maxLot > 0) {
                    codeGenerationService.syncIfBehind(BusinessCodeType.LO_THUOC, maxLot);
                }
            } catch (Exception e) {
                lotSequenceSynced.set(false);
                throw e;
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
