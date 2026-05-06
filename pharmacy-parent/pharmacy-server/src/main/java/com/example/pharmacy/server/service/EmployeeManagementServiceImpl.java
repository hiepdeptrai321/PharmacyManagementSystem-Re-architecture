package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.enums.AuditAction;
import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.common.exception.BusinessException;
import com.example.pharmacy.common.request.CreateEmployeeRequest;
import com.example.pharmacy.server.repository.EmployeeWriteRepository;
import com.example.pharmacy.server.transaction.TransactionManager;

import java.util.Objects;

public class EmployeeManagementServiceImpl implements EmployeeManagementService {
    private final TransactionManager transactionManager;
    private final EmployeeWriteRepository employeeWriteRepository;
    private final CodeGenerationService codeGenerationService;
    private final AuditService auditService;

    public EmployeeManagementServiceImpl(
            TransactionManager transactionManager,
            EmployeeWriteRepository employeeWriteRepository,
            CodeGenerationService codeGenerationService,
            AuditService auditService
    ) {
        this.transactionManager = Objects.requireNonNull(transactionManager, "transactionManager must not be null");
        this.employeeWriteRepository = Objects.requireNonNull(employeeWriteRepository, "employeeWriteRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
        this.auditService = Objects.requireNonNull(auditService, "auditService must not be null");
    }

    @Override
    public String createEmployee(CreateEmployeeRequest request, UserContext actor) {
        validateCreateEmployeeRequest(request);
        return transactionManager.execute(() -> {
            if (employeeWriteRepository.existsByUsername(request.getUsername().trim())) {
                throw new BusinessException("Tai khoan nhan vien da ton tai.");
            }
            String maNhanVien = codeGenerationService.nextCode(BusinessCodeType.NHAN_VIEN);
            employeeWriteRepository.insert(maNhanVien, request);
            auditService.logAction(actor, AuditAction.CREATE, "NhanVien", maNhanVien,
                    "Tao nhan vien moi voi tai khoan " + request.getUsername().trim());
            return maNhanVien;
        });
    }

    private void validateCreateEmployeeRequest(CreateEmployeeRequest request) {
        if (request == null) {
            throw new BusinessException("CreateEmployeeRequest khong duoc null.");
        }
        if (isBlank(request.getFullName()) || isBlank(request.getUsername()) || isBlank(request.getPassword())) {
            throw new BusinessException("Thong tin nhan vien bat buoc con thieu.");
        }
        if (request.getDateOfBirth() == null || request.getStartDate() == null) {
            throw new BusinessException("Ngay sinh va ngay vao lam bat buoc phai co.");
        }
        if (request.getRole() == null) {
            throw new BusinessException("Vai tro nhan vien khong hop le.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
