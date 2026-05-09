package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.request.CreateEmployeeRequest;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.EmployeeWriteRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcEmployeeWriteRepository extends AbstractJdbcRepository implements EmployeeWriteRepository {
    private static final String EXISTS_BY_USERNAME_SQL = """
            SELECT 1
            FROM NhanVienDto
            WHERE TaiKhoan = ?
            LIMIT 1
            """;
    private static final String INSERT_EMPLOYEE_SQL = """
            INSERT INTO NhanVienDto (
                MaNV, TenNV, SDT, Email, NgaySinh, GioiTinh, DiaChi, VaiTro,
                TrangThai, TaiKhoan, MatKhau, NgayVaoLam, NgayKetThuc, TrangThaiXoa
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)
            """;

    public JdbcEmployeeWriteRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public boolean existsByUsername(String username) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(EXISTS_BY_USERNAME_SQL)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not validate employee username uniqueness.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insert(String maNhanVien, CreateEmployeeRequest request) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_EMPLOYEE_SQL)) {
                statement.setString(1, maNhanVien);
                statement.setString(2, request.getFullName());
                statement.setString(3, request.getPhoneNumber());
                statement.setString(4, request.getEmail());
                statement.setDate(5, Date.valueOf(request.getDateOfBirth()));
                statement.setBoolean(6, request.isGender());
                statement.setString(7, request.getAddress());
                statement.setString(8, request.getRole().toLegacyValue());
                statement.setBoolean(9, request.isActive());
                statement.setString(10, request.getUsername());
                statement.setString(11, request.getPassword());
                statement.setDate(12, Date.valueOf(request.getStartDate()));
                if (request.getEndDate() == null) {
                    statement.setDate(13, null);
                } else {
                    statement.setDate(13, Date.valueOf(request.getEndDate()));
                }
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert employee.", exception);
        } finally {
            closeConnection(connection);
        }
    }
}
