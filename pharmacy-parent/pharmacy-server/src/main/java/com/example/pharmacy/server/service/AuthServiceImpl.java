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
            return LoginResponse.failure("Login request must not be null.");
        }
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return LoginResponse.failure("Username is required.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return LoginResponse.failure("Password is required.");
        }

        Optional<NhanVienEntity> entityOptional = nhanVienRepository.findByUsername(request.getUsername().trim());
        if (entityOptional.isEmpty()) {
            return LoginResponse.failure("Invalid username or password.");
        }

        NhanVienEntity entity = entityOptional.get();
        if (!entity.isTrangThai() || entity.isTrangThaiXoa()) {
            return LoginResponse.failure("User account is inactive.");
        }

        // Placeholder only. Replace with password hashing and verification later.
        if (!Objects.equals(entity.getMatKhau(), request.getPassword())) {
            return LoginResponse.failure("Invalid username or password.");
        }

        UserDTO user = new UserDTO(
                entity.getMaNV(),
                entity.getTaiKhoan(),
                entity.getTenNV(),
                UserRole.fromLegacyValue(entity.getVaiTro()),
                entity.isTrangThai()
        );
        if (auditService != null) {
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
        }
        return LoginResponse.success(UUID.randomUUID().toString(), user);
    }
}
