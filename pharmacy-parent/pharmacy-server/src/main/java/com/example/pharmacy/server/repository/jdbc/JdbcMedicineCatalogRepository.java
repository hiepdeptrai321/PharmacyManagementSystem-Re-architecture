package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.request.CreateThuocRequest;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.MedicineCatalogRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class JdbcMedicineCatalogRepository extends AbstractJdbcRepository implements MedicineCatalogRepository {
    private static final String INSERT_MEDICINE_SQL = """
            INSERT INTO Thuoc_SanPham (
                MaThuoc, TenThuoc, HamLuong, DonViHL, DuongDung, QuyCachDongGoi,
                SDK_GPNK, HangSX, NuocSX, HinhAnh, MaLoaiHang, MaNDL, ViTri, TrangThaiXoa, ETC
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?)
            """;
    private static final String INSERT_BASE_UNIT_SQL = """
            INSERT INTO ChiTietDonViTinh (
                MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan
            ) VALUES (?, ?, ?, ?, ?, 1)
            """;

    public JdbcMedicineCatalogRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public void insertMedicine(String maThuoc, CreateThuocRequest request) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_MEDICINE_SQL)) {
                statement.setString(1, maThuoc);
                statement.setString(2, request.getTenThuoc());
                if (request.getHamLuong() == null) {
                    statement.setObject(3, null);
                } else {
                    statement.setInt(3, request.getHamLuong());
                }
                statement.setString(4, request.getDonViHamLuong());
                statement.setString(5, request.getDuongDung());
                statement.setString(6, request.getQuyCachDongGoi());
                statement.setString(7, request.getSdkGpnk());
                statement.setString(8, request.getHangSanXuat());
                statement.setString(9, request.getNuocSanXuat());
                statement.setBytes(10, request.getHinhAnh());
                statement.setString(11, request.getMaLoaiHang());
                statement.setString(12, request.getMaNhomDuocLy());
                statement.setString(13, request.getViTri());
                statement.setBoolean(14, request.isEtc());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert medicine.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertBaseUnit(String maThuoc, CreateThuocRequest request) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_BASE_UNIT_SQL)) {
                statement.setString(1, maThuoc);
                statement.setString(2, request.getMaDonViCoBan());
                statement.setBigDecimal(3, request.getHeSoQuyDoiCoBan());
                statement.setBigDecimal(4, request.getGiaNhapCoBan());
                statement.setBigDecimal(5, request.getGiaBanCoBan());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert medicine base unit.", exception);
        } finally {
            closeConnection(connection);
        }
    }
}
