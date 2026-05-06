package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.enums.AuditAction;
import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.common.exception.BusinessException;
import com.example.pharmacy.common.request.CreateThuocRequest;
import com.example.pharmacy.server.repository.MedicineCatalogRepository;
import com.example.pharmacy.server.transaction.TransactionManager;

import java.math.BigDecimal;
import java.util.Objects;

public class ThuocCatalogServiceImpl implements ThuocCatalogService {
    private final TransactionManager transactionManager;
    private final MedicineCatalogRepository medicineCatalogRepository;
    private final CodeGenerationService codeGenerationService;
    private final AuditService auditService;

    public ThuocCatalogServiceImpl(
            TransactionManager transactionManager,
            MedicineCatalogRepository medicineCatalogRepository,
            CodeGenerationService codeGenerationService,
            AuditService auditService
    ) {
        this.transactionManager = Objects.requireNonNull(transactionManager, "transactionManager must not be null");
        this.medicineCatalogRepository = Objects.requireNonNull(medicineCatalogRepository, "medicineCatalogRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
        this.auditService = Objects.requireNonNull(auditService, "auditService must not be null");
    }

    @Override
    public String createThuocWithBaseUnit(CreateThuocRequest request, UserContext actor) {
        validateCreateThuocRequest(request);
        return transactionManager.execute(() -> {
            String maThuoc = codeGenerationService.nextCode(BusinessCodeType.THUOC);
            medicineCatalogRepository.insertMedicine(maThuoc, request);
            medicineCatalogRepository.insertBaseUnit(maThuoc, request);
            auditService.logAction(actor, AuditAction.CREATE, "Thuoc_SanPham", maThuoc,
                    "Tao thuoc moi va don vi co ban " + request.getMaDonViCoBan());
            return maThuoc;
        });
    }

    private void validateCreateThuocRequest(CreateThuocRequest request) {
        if (request == null) {
            throw new BusinessException("CreateThuocRequest khong duoc null.");
        }
        if (isBlank(request.getTenThuoc()) || isBlank(request.getMaLoaiHang()) || isBlank(request.getMaDonViCoBan())) {
            throw new BusinessException("Thong tin thuoc bat buoc con thieu.");
        }
        if (request.getGiaNhapCoBan() == null || request.getGiaBanCoBan() == null) {
            throw new BusinessException("Gia nhap va gia ban co ban bat buoc phai co.");
        }
        if (request.getGiaNhapCoBan().compareTo(BigDecimal.ZERO) < 0
                || request.getGiaBanCoBan().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Gia thuoc khong duoc am.");
        }
        if (request.getHeSoQuyDoiCoBan() == null || request.getHeSoQuyDoiCoBan().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("He so quy doi co ban phai lon hon 0.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
