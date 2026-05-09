package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.entity.NhanVienEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Optional;

public class JdbcNhanVienRepository implements NhanVienRepository {
    private static final String FIND_BY_USERNAME_SQL = """
            SELECT MaNV, TaiKhoan, MatKhau, TenNV, VaiTro, TrangThai, TrangThaiXoa
            FROM NhanVien
            WHERE TaiKhoan = ?
            LIMIT 1
            """;

    private final JdbcConnectionProvider connectionProvider;

    public JdbcNhanVienRepository(JdbcConnectionProvider connectionProvider) {
        this.connectionProvider = Objects.requireNonNull(connectionProvider, "connectionProvider must not be null");
    }

    @Override
    public Optional<NhanVienEntity> findByUsername(String username) {
        Connection connection = null;
        try {
            connection = connectionProvider.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_SQL)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return Optional.empty();
                    }
                    NhanVienEntity entity = new NhanVienEntity();
                    entity.setMaNV(resultSet.getString("MaNV"));
                    entity.setTaiKhoan(resultSet.getString("TaiKhoan"));
                    entity.setMatKhau(resultSet.getString("MatKhau"));
                    entity.setTenNV(resultSet.getString("TenNV"));
                    entity.setVaiTro(resultSet.getString("VaiTro"));
                    entity.setTrangThai(resultSet.getBoolean("TrangThai"));
                    entity.setTrangThaiXoa(resultSet.getBoolean("TrangThaiXoa"));
                    return Optional.of(entity);
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query NhanVien by username.", exception);
        } finally {
            connectionProvider.close(connection);
        }
    }
}
