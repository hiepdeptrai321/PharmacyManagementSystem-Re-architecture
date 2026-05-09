package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.NhanVienEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryNhanVienRepository implements NhanVienRepository {
    private final Map<String, NhanVienEntity> usersByUsername = new HashMap<>();

    public InMemoryNhanVienRepository() {
        NhanVienEntity manager = new NhanVienEntity();
        manager.setMaNV("NV001");
        manager.setTaiKhoan("admin");
        manager.setMatKhau("admin");
        manager.setTenNV("System Manager");
        manager.setVaiTro("Quan ly");
        manager.setTrangThai(true);
        manager.setTrangThaiXoa(false);
        usersByUsername.put(manager.getTaiKhoan(), manager);
    }

    @Override
    public Optional<NhanVienEntity> findByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }
}
