package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.request.PhieuNhapItemRequest;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.PurchaseOrderRepository;
import com.example.pharmacy.common.model.ChiTietPhieuNhapDto;
import com.example.pharmacy.common.model.NhaCungCapDto;
import com.example.pharmacy.common.model.NhanVienDto;
import com.example.pharmacy.common.model.PhieuNhapDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcPurchaseOrderRepository extends AbstractJdbcRepository implements PurchaseOrderRepository {
    private static final String INSERT_PHIEU_NHAP_SQL = """
            INSERT INTO PhieuNhap (MaPN, NgayNhap, TrangThai, GhiChu, MaNCC, MaNV)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String INSERT_CHI_TIET_PHIEU_NHAP_SQL = """
            INSERT INTO ChiTietPhieuNhap (
                MaPN, MaThuoc, MaLH, SoLuong, MaDVT, GiaNhap, ChietKhau, Thue
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String INSERT_LOT_SQL = """
            INSERT INTO Thuoc_SP_TheoLo (
                MaPN, MaThuoc, MaLH, SoLuongTon, SoLuongDat, SoLuongGiu, NSX, HSD
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_UNIT_PRICE_SQL = """
            UPDATE ChiTietDonViTinh
            SET GiaNhap = ?, GiaBan = ?
            WHERE MaThuoc = ? AND MaDVT = ?
            """;
    private static final String SELECT_ALL_PHIEU_NHAP_SQL = """
            SELECT pn.MaPN, pn.NgayNhap, pn.TrangThai, pn.GhiChu,
                   ncc.MaNCC, ncc.TenNCC,
                   nv.MaNV, nv.TenNV,
                   COUNT(ct.MaPN) AS soDong,
                   COALESCE(SUM((ct.GiaNhap * ct.SoLuong) - ((ct.GiaNhap * ct.SoLuong) * (ct.ChietKhau / 100.0))
                                + (((ct.GiaNhap * ct.SoLuong) - ((ct.GiaNhap * ct.SoLuong) * (ct.ChietKhau / 100.0))) * (ct.Thue / 100.0))), 0) AS TongTien
            FROM PhieuNhap pn
            JOIN NhaCungCap ncc ON ncc.MaNCC = pn.MaNCC
            JOIN NhanVien nv ON nv.MaNV = pn.MaNV
            LEFT JOIN ChiTietPhieuNhap ct ON ct.MaPN = pn.MaPN
            GROUP BY pn.MaPN, pn.NgayNhap, pn.TrangThai, pn.GhiChu, ncc.MaNCC, ncc.TenNCC, nv.MaNV, nv.TenNV
            ORDER BY pn.NgayNhap DESC, pn.MaPN DESC
            """;
    private static final String SELECT_PHIEU_NHAP_BY_ID_SQL = """
            SELECT pn.MaPN, pn.NgayNhap, pn.TrangThai, pn.GhiChu,
                   ncc.MaNCC, ncc.TenNCC,
                   nv.MaNV, nv.TenNV,
                   COUNT(ct.MaPN) AS soDong,
                   COALESCE(SUM((ct.GiaNhap * ct.SoLuong) - ((ct.GiaNhap * ct.SoLuong) * (ct.ChietKhau / 100.0))
                                + (((ct.GiaNhap * ct.SoLuong) - ((ct.GiaNhap * ct.SoLuong) * (ct.ChietKhau / 100.0))) * (ct.Thue / 100.0))), 0) AS TongTien
            FROM PhieuNhap pn
            JOIN NhaCungCap ncc ON ncc.MaNCC = pn.MaNCC
            JOIN NhanVien nv ON nv.MaNV = pn.MaNV
            LEFT JOIN ChiTietPhieuNhap ct ON ct.MaPN = pn.MaPN
            WHERE pn.MaPN = ?
            GROUP BY pn.MaPN, pn.NgayNhap, pn.TrangThai, pn.GhiChu, ncc.MaNCC, ncc.TenNCC, nv.MaNV, nv.TenNV
            """;
    private static final String SELECT_CHI_TIET_PHIEU_NHAP_SQL = """
            SELECT ct.MaPN, ct.MaThuoc, ct.MaLH, ct.SoLuong, ct.MaDVT, ct.GiaNhap, ct.ChietKhau, ct.Thue,
                   ts.TenThuoc
            FROM ChiTietPhieuNhap ct
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = ct.MaThuoc
            WHERE ct.MaPN = ?
            ORDER BY ts.TenThuoc ASC
            """;
    private static final String SELECT_UNIT_PRICE_SQL = """
            SELECT GiaNhap, GiaBan
            FROM ChiTietDonViTinh
            WHERE MaThuoc = ? AND MaDVT = ?
            """;
    private static final String INSERT_DEFAULT_UNIT_SQL = """
            INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
            SELECT ?, MaDVT, 1.0, 0.0, 0.0, 1
            FROM DonViTinh
            WHERE MaDVT = ?
            """;

    public JdbcPurchaseOrderRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public void insertHeader(String maPhieuNhap, String employeeId, PhieuNhapRequest request) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_PHIEU_NHAP_SQL)) {
                statement.setString(1, maPhieuNhap);
                statement.setDate(2, Date.valueOf(request.getNgayNhap()));
                statement.setBoolean(3, true);
                statement.setString(4, request.getGhiChu());
                statement.setString(5, request.getMaNhaCungCap());
                statement.setString(6, employeeId);
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
            try (PreparedStatement statement = connection.prepareStatement(INSERT_CHI_TIET_PHIEU_NHAP_SQL)) {
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
                statement.setInt(5, 0);
                statement.setInt(6, 0);
                statement.setDate(7, item.getNsx() == null ? null : Date.valueOf(item.getNsx()));
                statement.setDate(8, item.getHsd() == null ? null : Date.valueOf(item.getHsd()));
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
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_UNIT_PRICE_SQL)) {
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

    @Override
    public List<PhieuNhapDto> findAll() {
        Connection connection = null;
        List<PhieuNhapDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PHIEU_NHAP_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapHeader(resultSet));
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load purchase orders.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public PhieuNhapDto findById(String maPhieuNhap) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_PHIEU_NHAP_BY_ID_SQL)) {
                statement.setString(1, maPhieuNhap);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapHeader(resultSet);
                    }
                    return null;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load purchase order " + maPhieuNhap, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<ChiTietPhieuNhapDto> findDetailsByMaPhieuNhap(String maPhieuNhap) {
        Connection connection = null;
        List<ChiTietPhieuNhapDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_CHI_TIET_PHIEU_NHAP_SQL)) {
                statement.setString(1, maPhieuNhap);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(mapDetail(resultSet));
                    }
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load purchase order details for " + maPhieuNhap, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public long findMaxLotNumber() {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT COALESCE(MAX(CAST(SUBSTRING(MaLH, 3) AS UNSIGNED)), 0) FROM Thuoc_SP_TheoLo WHERE MaLH REGEXP '^LH[0-9]+$'")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next() ? resultSet.getLong(1) : 0;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not find max lot number.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private PhieuNhapDto mapHeader(ResultSet resultSet) throws Exception {
        PhieuNhapDto phieuNhap = new PhieuNhapDto();
        phieuNhap.setMaPN(resultSet.getString("MaPN"));
        Date ngayNhap = resultSet.getDate("NgayNhap");
        phieuNhap.setNgayNhap(ngayNhap == null ? null : ngayNhap.toLocalDate());
        phieuNhap.setTrangThai(resultSet.getBoolean("TrangThai"));
        phieuNhap.setGhiChu(resultSet.getString("GhiChu"));
        phieuNhap.setTongTien(resultSet.getDouble("TongTien"));

        NhaCungCapDto nhaCungCap = new NhaCungCapDto();
        nhaCungCap.setMaNCC(resultSet.getString("MaNCC"));
        nhaCungCap.setTenNCC(resultSet.getString("TenNCC"));
        phieuNhap.setNhaCungCap(nhaCungCap);

        NhanVienDto nhanVien = new NhanVienDto();
        nhanVien.setMaNV(resultSet.getString("MaNV"));
        nhanVien.setTenNV(resultSet.getString("TenNV"));
        phieuNhap.setNhanVien(nhanVien);
        return phieuNhap;
    }

    private ChiTietPhieuNhapDto mapDetail(ResultSet resultSet) throws Exception {
        ChiTietPhieuNhapDto detail = new ChiTietPhieuNhapDto();
        PhieuNhapDto phieuNhap = new PhieuNhapDto();
        phieuNhap.setMaPN(resultSet.getString("MaPN"));
        detail.setPhieuNhap(phieuNhap);

        Thuoc_SanPhamDto thuoc = new Thuoc_SanPhamDto();
        thuoc.setMaThuoc(resultSet.getString("MaThuoc"));
        thuoc.setTenThuoc(resultSet.getString("TenThuoc"));
        detail.setThuoc(thuoc);

        detail.setMaLH(resultSet.getString("MaLH"));
        detail.setSoLuong(resultSet.getInt("SoLuong"));
        detail.setGiaNhap(resultSet.getDouble("GiaNhap"));
        detail.setChietKhau(resultSet.getFloat("ChietKhau"));
        detail.setThue(resultSet.getFloat("Thue"));
        return detail;
    }
}
