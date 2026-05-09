package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.PhieuDatHangRepository;
import com.example.pharmacy.common.model.ChiTietPhieuDatHangDto;
import com.example.pharmacy.common.model.KhachHangDto;
import com.example.pharmacy.common.model.NhanVienDto;
import com.example.pharmacy.common.model.PhieuDatHangDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JdbcPhieuDatHangRepository extends AbstractJdbcRepository implements PhieuDatHangRepository {
    private static final String INSERT_PHIEU_DAT_SQL = """
            INSERT INTO PhieuDatHang (MaPDat, NgayLap, SoTienCoc, GhiChu, MaKH, MaNV, TrangThai)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String INSERT_CHI_TIET_PHIEU_DAT_SQL = """
            INSERT INTO ChiTietPhieuDatHang (MaPDat, MaThuoc, SoLuong, MaDVT, DonGia, GiamGia, TrangThai)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String SELECT_ALL_PHIEU_DAT_SQL = """
            SELECT pd.MaPDat, pd.NgayLap, pd.SoTienCoc, pd.GhiChu, pd.TrangThai,
                   kh.MaKH, kh.TenKH, kh.SDT,
                   nv.MaNV, nv.TenNV
            FROM PhieuDatHang pd
            JOIN KhachHang kh ON kh.MaKH = pd.MaKH
            JOIN NhanVien nv ON nv.MaNV = pd.MaNV
            ORDER BY pd.NgayLap DESC, pd.MaPDat DESC
            """;
    private static final String SELECT_PHIEU_DAT_BY_ID_SQL = """
            SELECT pd.MaPDat, pd.NgayLap, pd.SoTienCoc, pd.GhiChu, pd.TrangThai,
                   kh.MaKH, kh.TenKH, kh.SDT,
                   nv.MaNV, nv.TenNV
            FROM PhieuDatHang pd
            JOIN KhachHang kh ON kh.MaKH = pd.MaKH
            JOIN NhanVien nv ON nv.MaNV = pd.MaNV
            WHERE pd.MaPDat = ?
            """;
    private static final String SELECT_CHI_TIET_PHIEU_DAT_SQL = """
            SELECT ct.MaPDat, ct.MaThuoc, ct.SoLuong, ct.MaDVT, ct.DonGia, ct.GiamGia, ct.TrangThai,
                   ts.TenThuoc
            FROM ChiTietPhieuDatHang ct
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = ct.MaThuoc
            WHERE ct.MaPDat = ?
            ORDER BY ts.TenThuoc ASC
            """;
    private static final String SELECT_PHIEU_DAT_BY_KH_SQL = """
            SELECT MaPDat
            FROM PhieuDatHang
            WHERE MaKH = ?
            """;
    private static final String SELECT_LOT_STOCK_SQL = """
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
    private static final String UPDATE_CHI_TIET_STATUS_SQL = """
            UPDATE ChiTietPhieuDatHang
            SET TrangThai = ?
            WHERE MaPDat = ? AND MaThuoc = ? AND MaDVT = ?
            """;
    private static final String UPDATE_PHIEU_DAT_STATUS_SQL = """
            UPDATE PhieuDatHang
            SET TrangThai = ?
            WHERE MaPDat = ?
            """;

    public JdbcPhieuDatHangRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public void insertHeader(PhieuDatHangDto phieuDatHang, String maPhieuDat, String employeeId) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_PHIEU_DAT_SQL)) {
                statement.setString(1, maPhieuDat);
                statement.setDate(2, toSqlDate(phieuDatHang.getNgayLap()));
                statement.setDouble(3, phieuDatHang.getSoTienCoc());
                statement.setString(4, phieuDatHang.getGhiChu());
                statement.setString(5, phieuDatHang.getKhachHang().getMaKH());
                statement.setString(6, employeeId);
                statement.setInt(7, 0);
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert preorder header.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertDetail(String maPhieuDat, ChiTietPhieuDatHangDto detail) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_CHI_TIET_PHIEU_DAT_SQL)) {
                statement.setString(1, maPhieuDat);
                statement.setString(2, detail.getThuoc().getMaThuoc());
                statement.setInt(3, detail.getSoLuong());
                statement.setString(4, detail.getDvt());
                statement.setDouble(5, detail.getDonGia());
                statement.setDouble(6, detail.getGiamGia());
                statement.setInt(7, 0);
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert preorder detail.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<PhieuDatHangDto> findAll() {
        Connection connection = null;
        List<PhieuDatHangDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PHIEU_DAT_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapHeader(resultSet));
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load preorder list.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public PhieuDatHangDto findById(String maPhieuDat) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_PHIEU_DAT_BY_ID_SQL)) {
                statement.setString(1, maPhieuDat);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapHeader(resultSet);
                    }
                    return null;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load preorder " + maPhieuDat, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<ChiTietPhieuDatHangDto> findDetailsByMaPhieuDat(String maPhieuDat) {
        Connection connection = null;
        List<ChiTietPhieuDatHangDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_CHI_TIET_PHIEU_DAT_SQL)) {
                statement.setString(1, maPhieuDat);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(mapDetail(resultSet));
                    }
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load preorder details for " + maPhieuDat, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean approve(String maPhieuDat) {
        Connection connection = null;
        try {
            connection = getConnection();
            Integer currentStatus = findHeaderStatus(connection, maPhieuDat);
            if (currentStatus == null) {
                return false;
            }
            if (currentStatus >= 1) {
                return true;
            }

            List<ChiTietPhieuDatHangDto> details = findDetailsByMaPhieuDat(maPhieuDat);
            boolean allReady = true;
            for (ChiTietPhieuDatHangDto detail : details) {
                boolean detailReady = reserveLotsForDetail(connection, detail);
                updateDetailStatus(connection, maPhieuDat, detail, detailReady);
                if (!detailReady) {
                    allReady = false;
                }
            }

            updateHeaderStatus(connection, maPhieuDat, allReady ? 1 : 0);
            return allReady;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not approve preorder " + maPhieuDat, exception);
        } finally {
            closeConnection(connection);
        }
    }

    private PhieuDatHangDto mapHeader(ResultSet resultSet) throws Exception {
        PhieuDatHangDto phieuDatHang = new PhieuDatHangDto();
        phieuDatHang.setMaPDat(resultSet.getString("MaPDat"));
        Date ngayLap = resultSet.getDate("NgayLap");
        phieuDatHang.setNgayLap(ngayLap == null ? null : Timestamp.valueOf(ngayLap.toLocalDate().atStartOfDay()));
        phieuDatHang.setSoTienCoc(resultSet.getDouble("SoTienCoc"));
        phieuDatHang.setGhiChu(resultSet.getString("GhiChu"));
        phieuDatHang.setTrangthai(resultSet.getInt("TrangThai"));

        KhachHangDto khachHang = new KhachHangDto();
        khachHang.setMaKH(resultSet.getString("MaKH"));
        khachHang.setTenKH(resultSet.getString("TenKH"));
        khachHang.setSdt(resultSet.getString("SDT"));
        phieuDatHang.setKhachHang(khachHang);

        NhanVienDto nhanVien = new NhanVienDto();
        nhanVien.setMaNV(resultSet.getString("MaNV"));
        nhanVien.setTenNV(resultSet.getString("TenNV"));
        phieuDatHang.setNhanVien(nhanVien);
        return phieuDatHang;
    }

    private ChiTietPhieuDatHangDto mapDetail(ResultSet resultSet) throws Exception {
        ChiTietPhieuDatHangDto detail = new ChiTietPhieuDatHangDto();
        PhieuDatHangDto phieuDatHang = new PhieuDatHangDto();
        phieuDatHang.setMaPDat(resultSet.getString("MaPDat"));
        detail.setPhieuDatHang(phieuDatHang);

        Thuoc_SanPhamDto thuoc = new Thuoc_SanPhamDto();
        thuoc.setMaThuoc(resultSet.getString("MaThuoc"));
        thuoc.setTenThuoc(resultSet.getString("TenThuoc"));
        detail.setThuoc(thuoc);

        detail.setSoLuong(resultSet.getInt("SoLuong"));
        detail.setDvt(resultSet.getString("MaDVT"));
        detail.setDonGia(resultSet.getDouble("DonGia"));
        detail.setGiamGia(resultSet.getDouble("GiamGia"));
        detail.setTrangThai(resultSet.getBoolean("TrangThai"));
        return detail;
    }

    private Integer findHeaderStatus(Connection connection, String maPhieuDat) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PHIEU_DAT_BY_ID_SQL)) {
            statement.setString(1, maPhieuDat);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("TrangThai");
                }
                return null;
            }
        }
    }

    private boolean reserveLotsForDetail(Connection connection, ChiTietPhieuDatHangDto detail) throws Exception {
        List<LotAvailability> availabilities = loadLots(connection, detail.getThuoc().getMaThuoc());
        int remaining = detail.getSoLuong();
        int totalAvailable = availabilities.stream()
                .mapToInt(LotAvailability::available)
                .sum();

        if (totalAvailable < remaining) {
            return false;
        }

        for (LotAvailability lot : availabilities) {
            if (remaining <= 0) {
                break;
            }
            int allocate = Math.min(lot.available(), remaining);
            if (allocate <= 0) {
                continue;
            }
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_LOT_STOCK_SQL)) {
                statement.setInt(1, allocate);
                statement.setString(2, lot.maLo());
                statement.setInt(3, allocate);
                statement.executeUpdate();
            }
            remaining -= allocate;
        }
        return true;
    }

    private List<LotAvailability> loadLots(Connection connection, String maThuoc) throws Exception {
        List<LotAvailability> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_LOT_STOCK_SQL)) {
            statement.setString(1, maThuoc);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(new LotAvailability(
                            resultSet.getString("MaLH"),
                            resultSet.getInt("SoLuongTon"),
                            resultSet.getInt("SoLuongDat"),
                            resultSet.getInt("SoLuongGiu")
                    ));
                }
            }
        }
        return list;
    }

    private void updateDetailStatus(Connection connection, String maPhieuDat, ChiTietPhieuDatHangDto detail, boolean ready) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CHI_TIET_STATUS_SQL)) {
            statement.setBoolean(1, ready);
            statement.setString(2, maPhieuDat);
            statement.setString(3, detail.getThuoc().getMaThuoc());
            statement.setString(4, detail.getDvt());
            statement.executeUpdate();
        }
    }

    private void updateHeaderStatus(Connection connection, String maPhieuDat, int status) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PHIEU_DAT_STATUS_SQL)) {
            statement.setInt(1, status);
            statement.setString(2, maPhieuDat);
            statement.executeUpdate();
        }
    }

    private Date toSqlDate(Timestamp timestamp) {
        if (timestamp == null) {
            return Date.valueOf(java.time.LocalDate.now());
        }
        return Date.valueOf(timestamp.toLocalDateTime().toLocalDate());
    }

    private record LotAvailability(String maLo, int soLuongTon, int soLuongDat, int soLuongGiu) {
        int available() {
            return Math.max(0, soLuongTon - soLuongDat - soLuongGiu);
        }
    }
}
