package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.request.CreatePhieuDoiItemRequest;
import com.example.pharmacy.common.request.CreatePhieuDoiRequest;
import com.example.pharmacy.common.request.CreatePhieuTraItemRequest;
import com.example.pharmacy.common.request.CreatePhieuTraRequest;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.DoiTraRepository;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.ChiTietPhieuDoiHangDto;
import com.example.pharmacy.common.model.ChiTietPhieuTraHangDto;
import com.example.pharmacy.common.model.DonViTinhDto;
import com.example.pharmacy.common.model.HoaDonDto;
import com.example.pharmacy.common.model.KhachHangDto;
import com.example.pharmacy.common.model.NhanVienDto;
import com.example.pharmacy.common.model.PhieuDoiHangDto;
import com.example.pharmacy.common.model.PhieuTraHangDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcDoiTraRepository extends AbstractJdbcRepository implements DoiTraRepository {
    private static final String SELECT_HOA_DON_HEADER_SQL = """
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
            FROM HoaDonDto hd
            JOIN NhanVienDto nv ON nv.MaNV = hd.MaNV
            LEFT JOIN KhachHangDto kh ON kh.MaKH = hd.MaKH
            WHERE hd.MaHD = ?
            """;
    private static final String SELECT_HOA_DON_DETAILS_SQL = """
            SELECT ct.MaHD,
                   ct.MaLH,
                   ct.MaDVT,
                   ct.SoLuong,
                   ct.DonGia,
                   ct.GiamGia,
                   lo.MaThuoc,
                   ts.TenThuoc,
                   dvt.TenDonViTinh
            FROM ChiTietHoaDonDto ct
            JOIN Thuoc_SP_TheoLoDto lo ON lo.MaLH = ct.MaLH
            JOIN Thuoc_SanPhamDto ts ON ts.MaThuoc = lo.MaThuoc
            LEFT JOIN DonViTinhDto dvt ON dvt.MaDVT = ct.MaDVT
            WHERE ct.MaHD = ?
            ORDER BY ts.TenThuoc ASC, ct.MaLH ASC
            """;
    private static final String UPDATE_HOA_DON_CUSTOMER_SQL = """
            UPDATE HoaDonDto
            SET MaKH = ?
            WHERE MaHD = ?
            """;
    private static final String SELECT_SO_LUONG_DA_DOI_SQL = """
            SELECT COALESCE(SUM(ct.SoLuong), 0)
            FROM ChiTietPhieuDoiHangDto ct
            JOIN PhieuDoiHangDto pd ON pd.MaPD = ct.MaPD
            WHERE pd.MaHD = ? AND ct.MaLH = ? AND ct.MaDVT = ?
            """;
    private static final String SELECT_SO_LUONG_DA_TRA_SQL = """
            SELECT COALESCE(SUM(ct.SoLuong), 0)
            FROM ChiTietPhieuTraHangDto ct
            JOIN PhieuTraHangDto pt ON pt.MaPT = ct.MaPT
            WHERE pt.MaHD = ? AND ct.MaLH = ? AND ct.MaDVT = ?
            """;
    private static final String SELECT_UNIT_CONVERSION_SQL = """
            SELECT MaDVT, HeSoQuyDoi, DonViCoBan
            FROM ChiTietDonViTinhDto
            WHERE MaThuoc = ? AND MaDVT = ?
            """;
    private static final String SELECT_LOTS_FOR_UPDATE_SQL = """
            SELECT MaLH, SoLuongTon, HSD
            FROM Thuoc_SP_TheoLoDto
            WHERE MaThuoc = ?
            ORDER BY COALESCE(HSD, DATE('9999-12-31')) ASC, MaLH ASC
            FOR UPDATE
            """;
    private static final String UPDATE_ADD_STOCK_SQL = """
            UPDATE Thuoc_SP_TheoLoDto
            SET SoLuongTon = SoLuongTon + ?
            WHERE MaLH = ?
            """;
    private static final String UPDATE_DEDUCT_STOCK_SQL = """
            UPDATE Thuoc_SP_TheoLoDto
            SET SoLuongTon = SoLuongTon - ?
            WHERE MaLH = ? AND SoLuongTon >= ?
            """;
    private static final String INSERT_PHIEU_DOI_HEADER_SQL = """
            INSERT INTO PhieuDoiHangDto (MaPD, NgayLap, GhiChu, MaNV, MaKH, MaHD)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String INSERT_PHIEU_DOI_DETAIL_SQL = """
            INSERT INTO ChiTietPhieuDoiHangDto (MaLH, MaPD, MaThuoc, SoLuong, MaDVT, LyDoDoi)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String INSERT_PHIEU_TRA_HEADER_SQL = """
            INSERT INTO PhieuTraHangDto (MaPT, NgayLap, GhiChu, MaNV, MaHD, MaKH)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String INSERT_PHIEU_TRA_DETAIL_SQL = """
            INSERT INTO ChiTietPhieuTraHangDto (MaLH, MaPT, MaThuoc, SoLuong, MaDVT, DonGia, GiamGia, LyDoTra)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String SELECT_ALL_PHIEU_DOI_SQL = """
            SELECT pd.MaPD, pd.NgayLap, pd.GhiChu, pd.MaHD,
                   nv.MaNV, nv.TenNV,
                   kh.MaKH, kh.TenKH, kh.SDT
            FROM PhieuDoiHangDto pd
            JOIN NhanVienDto nv ON nv.MaNV = pd.MaNV
            JOIN KhachHangDto kh ON kh.MaKH = pd.MaKH
            ORDER BY pd.NgayLap DESC, pd.MaPD DESC
            """;
    private static final String SELECT_PHIEU_DOI_BY_ID_SQL = """
            SELECT pd.MaPD, pd.NgayLap, pd.GhiChu, pd.MaHD,
                   nv.MaNV, nv.TenNV,
                   kh.MaKH, kh.TenKH, kh.SDT
            FROM PhieuDoiHangDto pd
            JOIN NhanVienDto nv ON nv.MaNV = pd.MaNV
            JOIN KhachHangDto kh ON kh.MaKH = pd.MaKH
            WHERE pd.MaPD = ?
            """;
    private static final String SELECT_CT_PHIEU_DOI_SQL = """
            SELECT ct.MaLH, ct.MaPD, ct.MaThuoc, ct.SoLuong, ct.MaDVT, ct.LyDoDoi,
                   ts.TenThuoc, dvt.TenDonViTinh
            FROM ChiTietPhieuDoiHangDto ct
            JOIN Thuoc_SanPhamDto ts ON ts.MaThuoc = ct.MaThuoc
            LEFT JOIN DonViTinhDto dvt ON dvt.MaDVT = ct.MaDVT
            WHERE ct.MaPD = ?
            ORDER BY ts.TenThuoc ASC, ct.MaLH ASC
            """;
    private static final String SELECT_ALL_PHIEU_TRA_SQL = """
            SELECT pt.MaPT, pt.NgayLap, pt.GhiChu, pt.MaHD,
                   nv.MaNV, nv.TenNV,
                   kh.MaKH, kh.TenKH, kh.SDT
            FROM PhieuTraHangDto pt
            JOIN NhanVienDto nv ON nv.MaNV = pt.MaNV
            JOIN KhachHangDto kh ON kh.MaKH = pt.MaKH
            ORDER BY pt.NgayLap DESC, pt.MaPT DESC
            """;
    private static final String SELECT_PHIEU_TRA_BY_ID_SQL = """
            SELECT pt.MaPT, pt.NgayLap, pt.GhiChu, pt.MaHD,
                   nv.MaNV, nv.TenNV,
                   kh.MaKH, kh.TenKH, kh.SDT
            FROM PhieuTraHangDto pt
            JOIN NhanVienDto nv ON nv.MaNV = pt.MaNV
            JOIN KhachHangDto kh ON kh.MaKH = pt.MaKH
            WHERE pt.MaPT = ?
            """;
    private static final String SELECT_CT_PHIEU_TRA_SQL = """
            SELECT ct.MaLH, ct.MaPT, ct.MaThuoc, ct.SoLuong, ct.MaDVT, ct.DonGia, ct.GiamGia, ct.LyDoTra,
                   ts.TenThuoc, dvt.TenDonViTinh
            FROM ChiTietPhieuTraHangDto ct
            JOIN Thuoc_SanPhamDto ts ON ts.MaThuoc = ct.MaThuoc
            LEFT JOIN DonViTinhDto dvt ON dvt.MaDVT = ct.MaDVT
            WHERE ct.MaPT = ?
            ORDER BY ts.TenThuoc ASC, ct.MaLH ASC
            """;

    public JdbcDoiTraRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public HoaDonDto findHoaDonGoc(String maHoaDon) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_HOA_DON_HEADER_SQL)) {
                statement.setString(1, maHoaDon);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapHoaDonHeader(resultSet);
                    }
                    return null;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load invoice for doi/tra: " + maHoaDon, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<ChiTietHoaDonDto> findHoaDonDetails(String maHoaDon) {
        Connection connection = null;
        List<ChiTietHoaDonDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_HOA_DON_DETAILS_SQL)) {
                statement.setString(1, maHoaDon);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(mapHoaDonDetail(resultSet));
                    }
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load invoice details for doi/tra: " + maHoaDon, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void attachKhachHangToHoaDon(String maHoaDon, String maKhachHang) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_HOA_DON_CUSTOMER_SQL)) {
                statement.setString(1, maKhachHang);
                statement.setString(2, maHoaDon);
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not attach customer to invoice " + maHoaDon, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh) {
        return queryScalarInt(SELECT_SO_LUONG_DA_DOI_SQL, maHoaDon, maLoHang, maDonViTinh);
    }

    @Override
    public int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh) {
        return queryScalarInt(SELECT_SO_LUONG_DA_TRA_SQL, maHoaDon, maLoHang, maDonViTinh);
    }

    @Override
    public UnitConversion findUnitConversion(String maThuoc, String maDvt) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_UNIT_CONVERSION_SQL)) {
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
            throw new IllegalStateException("Could not load unit conversion for doi/tra.", exception);
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
            try (PreparedStatement statement = connection.prepareStatement(SELECT_LOTS_FOR_UPDATE_SQL)) {
                statement.setString(1, maThuoc);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(new LotStock(
                                resultSet.getString("MaLH"),
                                resultSet.getInt("SoLuongTon"),
                                resultSet.getDate("HSD")
                        ));
                    }
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not lock lots for doi/tra.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean addStock(String maLoHang, int soLuongTonTang) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_ADD_STOCK_SQL)) {
                statement.setInt(1, soLuongTonTang);
                statement.setString(2, maLoHang);
                return statement.executeUpdate() > 0;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not add stock for lot " + maLoHang, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean deductStock(String maLoHang, int soLuongTonGiam) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_DEDUCT_STOCK_SQL)) {
                statement.setInt(1, soLuongTonGiam);
                statement.setString(2, maLoHang);
                statement.setInt(3, soLuongTonGiam);
                return statement.executeUpdate() > 0;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not deduct stock for lot " + maLoHang, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertPhieuDoiHeader(String maPhieuDoi, CreatePhieuDoiRequest request, String maNhanVien) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_PHIEU_DOI_HEADER_SQL)) {
                statement.setString(1, maPhieuDoi);
                statement.setDate(2, Date.valueOf(request.getNgayLap() == null ? LocalDate.now() : request.getNgayLap()));
                statement.setString(3, isBlank(request.getGhiChu()) ? null : request.getGhiChu().trim());
                statement.setString(4, maNhanVien);
                statement.setString(5, request.getMaKhachHang());
                statement.setString(6, request.getMaHoaDonGoc());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert phieu doi header.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertPhieuDoiDetail(String maPhieuDoi, String maLoHangMoi, CreatePhieuDoiItemRequest item, int soLuongDisplay) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_PHIEU_DOI_DETAIL_SQL)) {
                statement.setString(1, maLoHangMoi);
                statement.setString(2, maPhieuDoi);
                statement.setString(3, item.getMaThuoc());
                statement.setInt(4, soLuongDisplay);
                statement.setString(5, item.getMaDonViTinh());
                statement.setString(6, item.getLyDoDoi());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert phieu doi detail.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertPhieuTraHeader(String maPhieuTra, CreatePhieuTraRequest request, String maNhanVien) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_PHIEU_TRA_HEADER_SQL)) {
                statement.setString(1, maPhieuTra);
                statement.setDate(2, Date.valueOf(request.getNgayLap() == null ? LocalDate.now() : request.getNgayLap()));
                statement.setString(3, isBlank(request.getGhiChu()) ? null : request.getGhiChu().trim());
                statement.setString(4, maNhanVien);
                statement.setString(5, request.getMaHoaDonGoc());
                statement.setString(6, request.getMaKhachHang());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert phieu tra header.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertPhieuTraDetail(String maPhieuTra, CreatePhieuTraItemRequest item, double donGia, double giamGia) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_PHIEU_TRA_DETAIL_SQL)) {
                statement.setString(1, item.getMaLoHang());
                statement.setString(2, maPhieuTra);
                statement.setString(3, item.getMaThuoc());
                statement.setInt(4, item.getSoLuong());
                statement.setString(5, item.getMaDonViTinh());
                statement.setDouble(6, donGia);
                statement.setDouble(7, giamGia);
                statement.setString(8, item.getLyDoTra());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert phieu tra detail.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<PhieuDoiHangDto> findAllPhieuDoi() {
        return queryPhieuDoiList(SELECT_ALL_PHIEU_DOI_SQL, null);
    }

    @Override
    public PhieuDoiHangDto findPhieuDoiById(String maPhieuDoi) {
        List<PhieuDoiHangDto> list = queryPhieuDoiList(SELECT_PHIEU_DOI_BY_ID_SQL, maPhieuDoi);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ChiTietPhieuDoiHangDto> findChiTietPhieuDoiByMaPD(String maPhieuDoi) {
        Connection connection = null;
        List<ChiTietPhieuDoiHangDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_CT_PHIEU_DOI_SQL)) {
                statement.setString(1, maPhieuDoi);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(mapChiTietPhieuDoi(resultSet));
                    }
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load chi tiet phieu doi.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<PhieuTraHangDto> findAllPhieuTra() {
        return queryPhieuTraList(SELECT_ALL_PHIEU_TRA_SQL, null);
    }

    @Override
    public PhieuTraHangDto findPhieuTraById(String maPhieuTra) {
        List<PhieuTraHangDto> list = queryPhieuTraList(SELECT_PHIEU_TRA_BY_ID_SQL, maPhieuTra);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ChiTietPhieuTraHangDto> findChiTietPhieuTraByMaPT(String maPhieuTra) {
        Connection connection = null;
        List<ChiTietPhieuTraHangDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_CT_PHIEU_TRA_SQL)) {
                statement.setString(1, maPhieuTra);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(mapChiTietPhieuTra(resultSet));
                    }
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load chi tiet phieu tra.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private int queryScalarInt(String sql, String maHoaDon, String maLoHang, String maDonViTinh) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, maHoaDon);
                statement.setString(2, maLoHang);
                statement.setString(3, maDonViTinh);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                    return 0;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query scalar integer for doi/tra.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private List<PhieuDoiHangDto> queryPhieuDoiList(String sql, String maPhieuDoi) {
        Connection connection = null;
        List<PhieuDoiHangDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (maPhieuDoi != null) {
                    statement.setString(1, maPhieuDoi);
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(mapPhieuDoiHeader(resultSet));
                    }
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load phieu doi list.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private List<PhieuTraHangDto> queryPhieuTraList(String sql, String maPhieuTra) {
        Connection connection = null;
        List<PhieuTraHangDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (maPhieuTra != null) {
                    statement.setString(1, maPhieuTra);
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(mapPhieuTraHeader(resultSet));
                    }
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load phieu tra list.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private HoaDonDto mapHoaDonHeader(ResultSet resultSet) throws Exception {
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
        }
        return hoaDon;
    }

    private ChiTietHoaDonDto mapHoaDonDetail(ResultSet resultSet) throws Exception {
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

    private PhieuDoiHangDto mapPhieuDoiHeader(ResultSet resultSet) throws Exception {
        PhieuDoiHangDto phieuDoiHang = new PhieuDoiHangDto();
        phieuDoiHang.setMaPD(resultSet.getString("MaPD"));
        phieuDoiHang.setNgayLap(resultSet.getTimestamp("NgayLap"));
        phieuDoiHang.setGhiChu(resultSet.getString("GhiChu"));

        NhanVienDto nhanVien = new NhanVienDto();
        nhanVien.setMaNV(resultSet.getString("MaNV"));
        nhanVien.setTenNV(resultSet.getString("TenNV"));
        phieuDoiHang.setNhanVien(nhanVien);

        KhachHangDto khachHang = new KhachHangDto();
        khachHang.setMaKH(resultSet.getString("MaKH"));
        khachHang.setTenKH(resultSet.getString("TenKH"));
        khachHang.setSdt(resultSet.getString("SDT"));
        phieuDoiHang.setKhachHang(khachHang);

        HoaDonDto hoaDon = new HoaDonDto();
        hoaDon.setMaHD(resultSet.getString("MaHD"));
        phieuDoiHang.setHoaDon(hoaDon);
        return phieuDoiHang;
    }

    private ChiTietPhieuDoiHangDto mapChiTietPhieuDoi(ResultSet resultSet) throws Exception {
        ChiTietPhieuDoiHangDto detail = new ChiTietPhieuDoiHangDto();

        PhieuDoiHangDto phieuDoiHang = new PhieuDoiHangDto();
        phieuDoiHang.setMaPD(resultSet.getString("MaPD"));
        detail.setPhieuDoiHang(phieuDoiHang);

        Thuoc_SanPhamDto thuoc = new Thuoc_SanPhamDto();
        thuoc.setMaThuoc(resultSet.getString("MaThuoc"));
        thuoc.setTenThuoc(resultSet.getString("TenThuoc"));
        detail.setThuoc(thuoc);

        Thuoc_SP_TheoLoDto loHang = new Thuoc_SP_TheoLoDto();
        loHang.setMaLH(resultSet.getString("MaLH"));
        loHang.setThuoc(thuoc);
        detail.setLoHang(loHang);

        DonViTinhDto donViTinh = new DonViTinhDto();
        donViTinh.setMaDVT(resultSet.getString("MaDVT"));
        donViTinh.setTenDonViTinh(resultSet.getString("TenDonViTinh"));
        detail.setDvt(donViTinh);

        detail.setSoLuong(resultSet.getInt("SoLuong"));
        detail.setLyDoDoi(resultSet.getString("LyDoDoi"));
        return detail;
    }

    private PhieuTraHangDto mapPhieuTraHeader(ResultSet resultSet) throws Exception {
        PhieuTraHangDto phieuTraHang = new PhieuTraHangDto();
        phieuTraHang.setMaPT(resultSet.getString("MaPT"));
        phieuTraHang.setNgayLap(resultSet.getTimestamp("NgayLap"));
        phieuTraHang.setGhiChu(resultSet.getString("GhiChu"));

        NhanVienDto nhanVien = new NhanVienDto();
        nhanVien.setMaNV(resultSet.getString("MaNV"));
        nhanVien.setTenNV(resultSet.getString("TenNV"));
        phieuTraHang.setNhanVien(nhanVien);

        KhachHangDto khachHang = new KhachHangDto();
        khachHang.setMaKH(resultSet.getString("MaKH"));
        khachHang.setTenKH(resultSet.getString("TenKH"));
        khachHang.setSdt(resultSet.getString("SDT"));
        phieuTraHang.setKhachHang(khachHang);

        HoaDonDto hoaDon = new HoaDonDto();
        hoaDon.setMaHD(resultSet.getString("MaHD"));
        phieuTraHang.setHoaDon(hoaDon);
        return phieuTraHang;
    }

    private ChiTietPhieuTraHangDto mapChiTietPhieuTra(ResultSet resultSet) throws Exception {
        ChiTietPhieuTraHangDto detail = new ChiTietPhieuTraHangDto();

        PhieuTraHangDto phieuTraHang = new PhieuTraHangDto();
        phieuTraHang.setMaPT(resultSet.getString("MaPT"));
        detail.setPhieuTraHang(phieuTraHang);

        Thuoc_SanPhamDto thuoc = new Thuoc_SanPhamDto();
        thuoc.setMaThuoc(resultSet.getString("MaThuoc"));
        thuoc.setTenThuoc(resultSet.getString("TenThuoc"));
        detail.setThuoc(thuoc);

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
        detail.setLyDoTra(resultSet.getString("LyDoTra"));
        return detail;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
