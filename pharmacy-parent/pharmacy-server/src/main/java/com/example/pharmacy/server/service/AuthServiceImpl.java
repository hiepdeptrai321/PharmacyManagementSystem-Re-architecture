package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserDTO;
import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.enums.AuditAction;
import com.example.pharmacy.common.enums.UserRole;
import com.example.pharmacy.common.request.LoginRequest;
import com.example.pharmacy.common.response.LoginResponse;
import com.example.pharmacy.server.entity.NhanVienEntity;
import com.example.pharmacy.server.repository.NhanVienRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class AuthServiceImpl implements AuthService {
    private final NhanVienRepository nhanVienRepository;
    private final AuditService auditService;

    public AuthServiceImpl(NhanVienRepository nhanVienRepository) {
        this(nhanVienRepository, null);
    }

    public AuthServiceImpl(NhanVienRepository nhanVienRepository, AuditService auditService) {
        this.nhanVienRepository = Objects.requireNonNull(nhanVienRepository, "nhanVienRepository must not be null");
        this.auditService = auditService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if (request == null) {
            return LoginResponse.failure("Yeu cau dang nhap khong hop le.");
        }
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return LoginResponse.failure("Vui long nhap tai khoan.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return LoginResponse.failure("Vui long nhap mat khau.");
        }

        Optional<NhanVienEntity> entityOptional = nhanVienRepository.findByUsername(request.getUsername().trim());
        if (entityOptional.isEmpty()) {
            return LoginResponse.failure("Tai khoan hoac mat khau khong dung.");
        }

        NhanVienEntity entity = entityOptional.get();
        if (!entity.isTrangThai() || entity.isTrangThaiXoa()) {
            return LoginResponse.failure("Tai khoan da bi khoa hoac ngung hoat dong.");
        }

        if (!Objects.equals(entity.getMatKhau(), request.getPassword())) {
            return LoginResponse.failure("Tai khoan hoac mat khau khong dung.");
        }

        UserDTO user = new UserDTO(
                entity.getMaNV(),
                entity.getTaiKhoan(),
                entity.getTenNV(),
                UserRole.fromLegacyValue(entity.getVaiTro()),
                entity.isTrangThai()
        );
        if (auditService != null) {
            try {
                auditService.logAction(
                        new UserContext(
                                entity.getMaNV(),
                                entity.getTaiKhoan(),
                                entity.getMaNV(),
                                entity.getTenNV(),
                                UserRole.fromLegacyValue(entity.getVaiTro())
                        ),
                        AuditAction.LOGIN,
                        "NhanVien",
                        entity.getMaNV(),
                        "Dang nhap thanh cong."
                );
            } catch (RuntimeException exception) {
                System.err.println("Audit logging failed during login: " + exception.getMessage());
            }
        }
        return LoginResponse.success(UUID.randomUUID().toString(), user);
    }
}
