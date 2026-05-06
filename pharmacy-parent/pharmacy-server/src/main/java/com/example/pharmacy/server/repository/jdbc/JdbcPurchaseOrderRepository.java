package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.request.PhieuNhapItemRequest;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.PurchaseOrderRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class JdbcPurchaseOrderRepository extends AbstractJdbcRepository implements PurchaseOrderRepository {
    private static final String INSERT_HEADER_SQL = """
            INSERT INTO PhieuNhap (MaPN, NgayNhap, TrangThai, GhiChu, MaNCC, MaNV)
            VALUES (?, ?, 1, ?, ?, ?)
            """;
    private static final String INSERT_DETAIL_SQL = """
            INSERT INTO ChiTietPhieuNhap (
                MaPN, MaThuoc, MaLH, SoLuong, MaDVT, GiaNhap, ChietKhau, Thue
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String INSERT_LOT_SQL = """
            INSERT INTO Thuoc_SP_TheoLo (
                MaPN, MaThuoc, MaLH, SoLuongTon, SoLuongDat, SoLuongGiu, NSX, HSD
            ) VALUES (?, ?, ?, ?, 0, 0, ?, ?)
            """;
    private static final String UPDATE_PRICING_SQL = """
            UPDATE ChiTietDonViTinh
            SET GiaNhap = ?, GiaBan = COALESCE(?, GiaBan)
            WHERE MaThuoc = ? AND MaDVT = ?
            """;

    public JdbcPurchaseOrderRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public void insertHeader(String maPhieuNhap, String employeeId, PhieuNhapRequest request) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_HEADER_SQL)) {
                statement.setString(1, maPhieuNhap);
                statement.setDate(2, Date.valueOf(request.getNgayNhap()));
                statement.setString(3, request.getGhiChu());
                statement.setString(4, request.getMaNhaCungCap());
                statement.setString(5, employeeId);
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert purchase order header.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertDetail(String maPhieuNhap, String maLo, PhieuNhapItemRequest item) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_DETAIL_SQL)) {
                statement.setString(1, maPhieuNhap);
                statement.setString(2, item.getMaThuoc());
                statement.setString(3, maLo);
                statement.setInt(4, item.getSoLuong());
                statement.setString(5, item.getMaDvt());
                statement.setBigDecimal(6, item.getGiaNhap());
                statement.setBigDecimal(7, item.getChietKhau());
                statement.setBigDecimal(8, item.getThue());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert purchase order detail.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertLot(String maPhieuNhap, String maLo, PhieuNhapItemRequest item) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_LOT_SQL)) {
                statement.setString(1, maPhieuNhap);
                statement.setString(2, item.getMaThuoc());
                statement.setString(3, maLo);
                statement.setInt(4, item.getSoLuong());
                statement.setDate(5, item.getNsx() == null ? null : Date.valueOf(item.getNsx()));
                statement.setDate(6, item.getHsd() == null ? null : Date.valueOf(item.getHsd()));
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert purchase order lot.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void updatePricing(String maThuoc, String maDvt, PhieuNhapItemRequest item) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_PRICING_SQL)) {
                statement.setBigDecimal(1, item.getGiaNhap());
                statement.setBigDecimal(2, item.getGiaBanCapNhat());
                statement.setString(3, maThuoc);
                statement.setString(4, maDvt);
                int updated = statement.executeUpdate();
                if (updated == 0) {
                    throw new IllegalStateException("No ChiTietDonViTinh row found for " + maThuoc + " / " + maDvt);
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not update medicine pricing after import.", exception);
        } finally {
            closeConnection(connection);
        }
    }
}
