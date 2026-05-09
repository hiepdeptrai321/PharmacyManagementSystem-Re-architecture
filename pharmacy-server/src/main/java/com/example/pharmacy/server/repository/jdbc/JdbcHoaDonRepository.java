package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.HoaDonRepository;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.DonViTinhDto;
import com.example.pharmacy.common.model.HoaDonDto;
import com.example.pharmacy.common.model.KhachHangDto;
import com.example.pharmacy.common.model.NhanVienDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcHoaDonRepository extends AbstractJdbcRepository implements HoaDonRepository {
    private static final String INSERT_HOA_DON_SQL = """
            INSERT INTO HoaDon (MaHD, MaNV, MaKH, NgayLap, TrangThai, LoaiHoaDon, MaDonThuoc)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String INSERT_CHI_TIET_HOA_DON_SQL = """
            INSERT INTO ChiTietHoaDon (MaHD, MaLH, MaDVT, SoLuong, DonGia, GiamGia)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String SELECT_HE_SO_QUY_DOI_SQL = """
            SELECT HeSoQuyDoi
            FROM ChiTietDonViTinh
            WHERE MaThuoc = ? AND MaDVT = ?
            """;
    private static final String SELECT_LOT_SQL = """
            SELECT MaLH, SoLuongTon
            FROM Thuoc_SP_TheoLo
            WHERE MaThuoc = ?
            ORDER BY COALESCE(HSD, DATE('9999-12-31')) ASC, MaLH ASC
            FOR UPDATE
            """;
    private static final String UPDATE_LOT_STOCK_SQL = """
            UPDATE Thuoc_SP_TheoLo
            SET SoLuongTon = SoLuongTon - ?
            WHERE MaLH = ? AND SoLuongTon >= ?
            """;
    private static final String UPDATE_PHIEU_DAT_HANG_STATUS_SQL = """
            UPDATE PhieuDatHang
            SET TrangThai = ?
            WHERE MaPDat = ?
            """;
    private static final String SELECT_HOA_DON_BY_ID_SQL = """
            SELECT hd.MaHD,
                   hd.NgayLap,
                   hd.TrangThai,
                   hd.LoaiHoaDon,
                   hd.MaDonThuoc,
                   nv.MaNV,
                   nv.TenNV,
                   kh.MaKH,
                   kh.TenKH,
                   kh.SDT
            FROM HoaDon hd
            JOIN NhanVien nv ON nv.MaNV = hd.MaNV
            LEFT JOIN KhachHang kh ON kh.MaKH = hd.MaKH
            WHERE hd.MaHD = ?
            """;
    private static final String SELECT_ALL_HOA_DON_SQL = """
            SELECT hd.MaHD,
                   hd.NgayLap,
                   hd.TrangThai,
                   hd.LoaiHoaDon,
                   hd.MaDonThuoc,
                   nv.MaNV,
                   nv.TenNV,
                   kh.MaKH,
                   kh.TenKH,
                   kh.SDT
            FROM HoaDon hd
            JOIN NhanVien nv ON nv.MaNV = hd.MaNV
            LEFT JOIN KhachHang kh ON kh.MaKH = hd.MaKH
            ORDER BY hd.NgayLap DESC, hd.MaHD DESC
            """;
    private static final String SELECT_CHI_TIET_HOA_DON_SQL = """
            SELECT ct.MaHD,
                   ct.MaLH,
                   ct.MaDVT,
                   ct.SoLuong,
                   ct.DonGia,
                   ct.GiamGia,
                   lo.MaThuoc,
                   ts.TenThuoc,
                   dvt.TenDonViTinh
            FROM ChiTietHoaDon ct
            JOIN Thuoc_SP_TheoLo lo ON lo.MaLH = ct.MaLH
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = lo.MaThuoc
            LEFT JOIN DonViTinh dvt ON dvt.MaDVT = ct.MaDVT
            WHERE ct.MaHD = ?
            ORDER BY ts.TenThuoc ASC, ct.MaLH ASC
            """;

    public JdbcHoaDonRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public void insertHeader(String maHoaDon, String employeeId, CreateHoaDonRequest request) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_HOA_DON_SQL)) {
                statement.setString(1, maHoaDon);
                statement.setString(2, employeeId);
                statement.setString(3, isBlank(request.getMaKhachHang()) ? null : request.getMaKhachHang().trim());
                LocalDateTime ngayLap = request.getNgayLap() == null ? LocalDateTime.now() : request.getNgayLap();
                statement.setTimestamp(4, Timestamp.valueOf(ngayLap));
                statement.setString(5, request.getLoaiHoaDon());
                statement.setString(6, isBlank(request.getMaDonThuoc()) ? null : request.getMaDonThuoc().trim());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert invoice header.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertDetail(String maHoaDon, String maLo, String maDvt, int soLuong, double donGia, double giamGia) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_CHI_TIET_HOA_DON_SQL)) {
                statement.setString(1, maHoaDon);
                statement.setString(2, maLo);
                statement.setString(3, maDvt);
                statement.setInt(4, soLuong);
                statement.setDouble(5, donGia);
                statement.setDouble(6, giamGia);
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert invoice detail.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public UnitConversion findUnitConversion(String maThuoc, String maDvt) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_HE_SO_QUY_DOI_SQL)) {
                statement.setString(1, maThuoc);
                statement.setString(2, maDvt);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return new UnitConversion(
                                resultSet.getString("MaDVT"),
                                resultSet.getDouble("HeSoQuyDoi"),
                                resultSet.getBoolean("DonViCoBan")
                        );
                    }
                    return null;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load unit conversion for " + maThuoc + " / " + maDvt, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<LotStock> findLotsForUpdate(String maThuoc) {
        Connection connection = null;
        List<LotStock> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_LOT_SQL)) {
                statement.setString(1, maThuoc);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(new LotStock(
                                resultSet.getString("MaLH"),
                                resultSet.getInt("SoLuongTon"),
                                resultSet.getInt("SoLuongDat"),
                                resultSet.getInt("SoLuongGiu"),
                                resultSet.getDate("HSD")
                        ));
                    }
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not lock lots for medicine " + maThuoc, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean updateLotAfterSale(String maLo, int soLuongTonGiam, int reservedGiam) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_LOT_STOCK_SQL)) {
                statement.setInt(1, soLuongTonGiam);
                statement.setInt(2, reservedGiam);
                statement.setInt(3, reservedGiam);
                statement.setString(4, maLo);
                statement.setInt(5, soLuongTonGiam);
                return statement.executeUpdate() > 0;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not update stock lot after sale: " + maLo, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void updatePreorderStatus(String maPhieuDat, int status) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_PHIEU_DAT_HANG_STATUS_SQL)) {
                statement.setInt(1, status);
                statement.setString(2, maPhieuDat);
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not update preorder status for " + maPhieuDat, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<HoaDonDto> findAll() {
        Connection connection = null;
        List<HoaDonDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_HOA_DON_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapHeader(resultSet));
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load invoice list.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public HoaDonDto findById(String maHoaDon) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_HOA_DON_BY_ID_SQL)) {
                statement.setString(1, maHoaDon);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapHeader(resultSet);
                    }
                    return null;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load invoice " + maHoaDon, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<ChiTietHoaDonDto> findDetailsByMaHD(String maHoaDon) {
        Connection connection = null;
        List<ChiTietHoaDonDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_CHI_TIET_HOA_DON_SQL)) {
                statement.setString(1, maHoaDon);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(mapDetail(resultSet));
                    }
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load invoice details for " + maHoaDon, exception);
        } finally {
            closeConnection(connection);
        }
    }

    private HoaDonDto mapHeader(ResultSet resultSet) throws Exception {
        HoaDonDto hoaDon = new HoaDonDto();
        hoaDon.setMaHD(resultSet.getString("MaHD"));
        hoaDon.setNgayLap(resultSet.getTimestamp("NgayLap"));
        hoaDon.setTrangThai(resultSet.getBoolean("TrangThai"));
        hoaDon.setLoaiHoaDon(resultSet.getString("LoaiHoaDon"));
        hoaDon.setMaDonThuoc(resultSet.getString("MaDonThuoc"));

        NhanVienDto nhanVien = new NhanVienDto();
        nhanVien.setMaNV(resultSet.getString("MaNV"));
        nhanVien.setTenNV(resultSet.getString("TenNV"));
        hoaDon.setMaNV(nhanVien);

        String maKh = resultSet.getString("MaKH");
        if (maKh != null) {
            KhachHangDto khachHang = new KhachHangDto();
            khachHang.setMaKH(maKh);
            khachHang.setTenKH(resultSet.getString("TenKH"));
            khachHang.setSdt(resultSet.getString("SDT"));
            hoaDon.setMaKH(khachHang);
        } else {
            hoaDon.setMaKH(null);
        }
        return hoaDon;
    }

    private ChiTietHoaDonDto mapDetail(ResultSet resultSet) throws Exception {
        ChiTietHoaDonDto detail = new ChiTietHoaDonDto();
        HoaDonDto hoaDon = new HoaDonDto();
        hoaDon.setMaHD(resultSet.getString("MaHD"));
        detail.setHoaDon(hoaDon);

        Thuoc_SanPhamDto thuoc = new Thuoc_SanPhamDto();
        thuoc.setMaThuoc(resultSet.getString("MaThuoc"));
        thuoc.setTenThuoc(resultSet.getString("TenThuoc"));

        Thuoc_SP_TheoLoDto loHang = new Thuoc_SP_TheoLoDto();
        loHang.setMaLH(resultSet.getString("MaLH"));
        loHang.setThuoc(thuoc);
        detail.setLoHang(loHang);

        DonViTinhDto donViTinh = new DonViTinhDto();
        donViTinh.setMaDVT(resultSet.getString("MaDVT"));
        donViTinh.setTenDonViTinh(resultSet.getString("TenDonViTinh"));
        detail.setDvt(donViTinh);

        detail.setSoLuong(resultSet.getInt("SoLuong"));
        detail.setDonGia(resultSet.getDouble("DonGia"));
        detail.setGiamGia(resultSet.getDouble("GiamGia"));
        return detail;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
