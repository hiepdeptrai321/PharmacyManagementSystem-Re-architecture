package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.dto.ExpiringLotDTO;
import com.example.pharmacy.common.dto.RevenuePointDTO;
import com.example.pharmacy.common.dto.TopSellingProductDTO;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.ReportRepository;
import com.example.pharmacy.common.model.HoaDonDisplay;
import com.example.pharmacy.common.model.ThongKeBanHang;
import com.example.pharmacy.common.model.ThongKeTonKho;
import com.example.pharmacy.common.model.ThongKeTopSanPham;
import com.example.pharmacy.common.model.ThuocHetHan;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcReportRepository extends AbstractJdbcRepository implements ReportRepository {
    private static final String REVENUE_BY_RANGE_SQL = """
            SELECT DATE(hd.NgayLap) AS bucket_date,
                   ROUND(SUM((cthd.SoLuong * cthd.DonGia) - cthd.GiamGia), 2) AS revenue
            FROM HoaDon hd
            JOIN ChiTietHoaDon cthd ON cthd.MaHD = hd.MaHD
            WHERE DATE(hd.NgayLap) BETWEEN ? AND ?
            GROUP BY DATE(hd.NgayLap)
            ORDER BY DATE(hd.NgayLap)
            """;
    private static final String TOP_SELLING_BY_RANGE_SQL = """
            SELECT ts.MaThuoc,
                   ts.TenThuoc,
                   SUM(cthd.SoLuong) AS quantity_sold,
                   ROUND(SUM((cthd.SoLuong * cthd.DonGia) - cthd.GiamGia), 2) AS revenue
            FROM HoaDon hd
            JOIN ChiTietHoaDon cthd ON cthd.MaHD = hd.MaHD
            JOIN Thuoc_SP_TheoLo lo ON lo.MaLH = cthd.MaLH
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = lo.MaThuoc
            WHERE DATE(hd.NgayLap) BETWEEN ? AND ?
            GROUP BY ts.MaThuoc, ts.TenThuoc
            ORDER BY quantity_sold DESC, revenue DESC
            LIMIT ?
            """;
    private static final String EXPIRED_LOTS_SQL = """
            SELECT lo.MaLH,
                   lo.MaThuoc,
                   ts.TenThuoc,
                   lo.HSD,
                   lo.SoLuongTon,
                   DATEDIFF(lo.HSD, CURRENT_DATE()) AS days_to_expiry
            FROM Thuoc_SP_TheoLo lo
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = lo.MaThuoc
            WHERE lo.SoLuongTon > 0
              AND lo.HSD IS NOT NULL
              AND lo.HSD < CURRENT_DATE()
            ORDER BY lo.HSD ASC, lo.MaLH ASC
            """;
    private static final String EXPIRING_LOTS_SQL = """
            SELECT lo.MaLH,
                   lo.MaThuoc,
                   ts.TenThuoc,
                   lo.HSD,
                   lo.SoLuongTon,
                   DATEDIFF(lo.HSD, CURRENT_DATE()) AS days_to_expiry
            FROM Thuoc_SP_TheoLo lo
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = lo.MaThuoc
            WHERE lo.SoLuongTon > 0
              AND lo.HSD IS NOT NULL
              AND lo.HSD BETWEEN CURRENT_DATE() AND DATE_ADD(CURRENT_DATE(), INTERVAL ? DAY)
            ORDER BY lo.HSD ASC, lo.MaLH ASC
            """;
    private static final String HOA_DON_BY_RANGE_SQL = """
            SELECT
                h.MaHD,
                DATE(h.NgayLap) AS ngayLap,
                h.MaKH,
                h.MaNV,
                COALESCE(SUM(ct.SoLuong * (ct.DonGia - ct.GiamGia)), 0) * 1.05 AS tongTienNet
            FROM HoaDon h
            LEFT JOIN ChiTietHoaDon ct ON h.MaHD = ct.MaHD
            WHERE DATE(h.NgayLap) BETWEEN ? AND ?
            GROUP BY h.MaHD, DATE(h.NgayLap), h.MaKH, h.MaNV
            ORDER BY ngayLap DESC, h.MaHD DESC
            """;
    private static final String TOP_BAN_CHAY_SQL = """
            SELECT
                t.MaThuoc,
                t.TenThuoc,
                COALESCE(dvtChuan.KiHieu, dvtChuan.TenDonViTinh, 'N/A') AS DVT_CoBan,
                CAST(SUM(cthd.SoLuong * COALESCE(ctdvtBan.HeSoQuyDoi, 1)) AS SIGNED) AS TongSoLuong,
                ROUND(SUM(cthd.SoLuong * (cthd.DonGia - cthd.GiamGia)), 2) AS TongDoanhThu
            FROM ChiTietHoaDon cthd
            JOIN HoaDon hd ON cthd.MaHD = hd.MaHD
            JOIN Thuoc_SP_TheoLo lo ON cthd.MaLH = lo.MaLH
            JOIN Thuoc_SanPham t ON lo.MaThuoc = t.MaThuoc
            LEFT JOIN ChiTietDonViTinh ctdvtChuan
                ON t.MaThuoc = ctdvtChuan.MaThuoc
               AND ctdvtChuan.DonViCoBan = 1
            LEFT JOIN DonViTinh dvtChuan
                ON ctdvtChuan.MaDVT = dvtChuan.MaDVT
            LEFT JOIN ChiTietDonViTinh ctdvtBan
                ON t.MaThuoc = ctdvtBan.MaThuoc
               AND cthd.MaDVT = ctdvtBan.MaDVT
            WHERE DATE(hd.NgayLap) BETWEEN ? AND ?
            GROUP BY t.MaThuoc, t.TenThuoc, COALESCE(dvtChuan.KiHieu, dvtChuan.TenDonViTinh, 'N/A')
            ORDER BY TongSoLuong DESC, TongDoanhThu DESC
            LIMIT ?
            """;
    private static final String TOP_DOANH_THU_SQL = """
            SELECT
                t.MaThuoc,
                t.TenThuoc,
                COALESCE(dvtChuan.KiHieu, dvtChuan.TenDonViTinh, 'N/A') AS DVT_CoBan,
                CAST(SUM(cthd.SoLuong * COALESCE(ctdvtBan.HeSoQuyDoi, 1)) AS SIGNED) AS TongSoLuong,
                ROUND(SUM(cthd.SoLuong * (cthd.DonGia - cthd.GiamGia)), 2) AS TongDoanhThu
            FROM ChiTietHoaDon cthd
            JOIN HoaDon hd ON cthd.MaHD = hd.MaHD
            JOIN Thuoc_SP_TheoLo lo ON cthd.MaLH = lo.MaLH
            JOIN Thuoc_SanPham t ON lo.MaThuoc = t.MaThuoc
            LEFT JOIN ChiTietDonViTinh ctdvtChuan
                ON t.MaThuoc = ctdvtChuan.MaThuoc
               AND ctdvtChuan.DonViCoBan = 1
            LEFT JOIN DonViTinh dvtChuan
                ON ctdvtChuan.MaDVT = dvtChuan.MaDVT
            LEFT JOIN ChiTietDonViTinh ctdvtBan
                ON t.MaThuoc = ctdvtBan.MaThuoc
               AND cthd.MaDVT = ctdvtBan.MaDVT
            WHERE DATE(hd.NgayLap) BETWEEN ? AND ?
            GROUP BY t.MaThuoc, t.TenThuoc, COALESCE(dvtChuan.KiHieu, dvtChuan.TenDonViTinh, 'N/A')
            ORDER BY TongDoanhThu DESC, TongSoLuong DESC
            LIMIT ?
            """;
    private static final String THUOC_HET_HAN_SQL = """
            SELECT
                t.MaThuoc AS maThuocHH,
                t.TenThuoc AS tenThuocHH,
                SUM(l.SoLuongTon) AS soLuong,
                l.HSD AS ngayHetHan
            FROM Thuoc_SP_TheoLo l
            JOIN Thuoc_SanPham t ON l.MaThuoc = t.MaThuoc
            WHERE l.SoLuongTon > 0
              AND l.HSD IS NOT NULL
              AND l.HSD <= DATE_ADD(
                    CURRENT_DATE(),
                    INTERVAL COALESCE((
                        SELECT CASE
                            WHEN TRIM(GiaTri) REGEXP '^[0-9]+$' THEN CAST(TRIM(GiaTri) AS SIGNED)
                            ELSE NULL
                        END
                        FROM ThongSoUngDung
                        WHERE TenThongSo = 'NgayHetHan'
                        LIMIT 1
                    ), 30) DAY
                )
            GROUP BY t.MaThuoc, t.TenThuoc, l.HSD
            ORDER BY l.HSD ASC, t.MaThuoc ASC
            """;
    private static final String THONG_KE_XNT_SQL = """
            WITH DonViCoBan AS (
                SELECT
                    MaThuoc,
                    MaDVT,
                    COALESCE(HeSoQuyDoi, 1) AS HeSoQuyDoi
                FROM ChiTietDonViTinh
                WHERE DonViCoBan = 1
            ),
            BaseUnits AS (
                SELECT
                    ct.MaThuoc,
                    dvt.KiHieu AS DVT
                FROM ChiTietDonViTinh ct
                JOIN DonViTinh dvt ON ct.MaDVT = dvt.MaDVT
                WHERE ct.DonViCoBan = 1
            ),
            AllProducts AS (
                SELECT
                    t.MaThuoc,
                    t.TenThuoc,
                    COALESCE(bu.DVT, 'N/A') AS DVT
                FROM Thuoc_SanPham t
                LEFT JOIN BaseUnits bu ON t.MaThuoc = bu.MaThuoc
            ),
            Transactions AS (
                SELECT
                    ctpn.MaThuoc,
                    pn.NgayNhap AS NgayGiaoDich,
                    CEIL(ctpn.SoLuong * COALESCE(dvt.HeSoQuyDoi, 1) / dvtcb.HeSoQuyDoi) AS SoLuongNhap,
                    0 AS SoLuongXuat
                FROM ChiTietPhieuNhap ctpn
                JOIN PhieuNhap pn ON ctpn.MaPN = pn.MaPN
                LEFT JOIN ChiTietDonViTinh dvt
                    ON ctpn.MaThuoc = dvt.MaThuoc
                   AND ctpn.MaDVT = dvt.MaDVT
                JOIN DonViCoBan dvtcb ON ctpn.MaThuoc = dvtcb.MaThuoc

                UNION ALL

                SELECT
                    lo.MaThuoc,
                    pt.NgayLap AS NgayGiaoDich,
                    CEIL(ctpt.SoLuong * COALESCE(dvt.HeSoQuyDoi, 1) / dvtcb.HeSoQuyDoi) AS SoLuongNhap,
                    0 AS SoLuongXuat
                FROM ChiTietPhieuTraHang ctpt
                JOIN PhieuTraHang pt ON ctpt.MaPT = pt.MaPT
                JOIN Thuoc_SP_TheoLo lo ON ctpt.MaLH = lo.MaLH
                LEFT JOIN ChiTietDonViTinh dvt
                    ON lo.MaThuoc = dvt.MaThuoc
                   AND ctpt.MaDVT = dvt.MaDVT
                JOIN DonViCoBan dvtcb ON lo.MaThuoc = dvtcb.MaThuoc

                UNION ALL

                SELECT
                    ctpd.MaThuoc,
                    pd.NgayLap AS NgayGiaoDich,
                    CEIL(ABS(ctpd.SoLuong) * COALESCE(dvt.HeSoQuyDoi, 1) / dvtcb.HeSoQuyDoi) AS SoLuongNhap,
                    0 AS SoLuongXuat
                FROM ChiTietPhieuDoiHang ctpd
                JOIN PhieuDoiHang pd ON ctpd.MaPD = pd.MaPD
                LEFT JOIN ChiTietDonViTinh dvt
                    ON ctpd.MaThuoc = dvt.MaThuoc
                   AND ctpd.MaDVT = dvt.MaDVT
                JOIN DonViCoBan dvtcb ON ctpd.MaThuoc = dvtcb.MaThuoc
                WHERE ctpd.SoLuong < 0

                UNION ALL

                SELECT
                    lo.MaThuoc,
                    hd.NgayLap AS NgayGiaoDich,
                    0 AS SoLuongNhap,
                    CEIL(cthd.SoLuong * COALESCE(dvt.HeSoQuyDoi, 1) / dvtcb.HeSoQuyDoi) AS SoLuongXuat
                FROM ChiTietHoaDon cthd
                JOIN HoaDon hd ON cthd.MaHD = hd.MaHD
                JOIN Thuoc_SP_TheoLo lo ON cthd.MaLH = lo.MaLH
                LEFT JOIN ChiTietDonViTinh dvt
                    ON lo.MaThuoc = dvt.MaThuoc
                   AND cthd.MaDVT = dvt.MaDVT
                JOIN DonViCoBan dvtcb ON lo.MaThuoc = dvtcb.MaThuoc

                UNION ALL

                SELECT
                    ctpd.MaThuoc,
                    pd.NgayLap AS NgayGiaoDich,
                    0 AS SoLuongNhap,
                    CEIL(ctpd.SoLuong * COALESCE(dvt.HeSoQuyDoi, 1) / dvtcb.HeSoQuyDoi) AS SoLuongXuat
                FROM ChiTietPhieuDoiHang ctpd
                JOIN PhieuDoiHang pd ON ctpd.MaPD = pd.MaPD
                LEFT JOIN ChiTietDonViTinh dvt
                    ON ctpd.MaThuoc = dvt.MaThuoc
                   AND ctpd.MaDVT = dvt.MaDVT
                JOIN DonViCoBan dvtcb ON ctpd.MaThuoc = dvtcb.MaThuoc
                WHERE ctpd.SoLuong > 0
            ),
            DailySummary AS (
                SELECT
                    MaThuoc,
                    DATE(NgayGiaoDich) AS Ngay,
                    SUM(SoLuongNhap) AS TongNhap,
                    SUM(SoLuongXuat) AS TongXuat
                FROM Transactions
                GROUP BY MaThuoc, DATE(NgayGiaoDich)
            )
            SELECT
                p.MaThuoc,
                p.TenThuoc,
                p.DVT,
                COALESCE(SUM(CASE WHEN ds.Ngay < ? THEN ds.TongNhap - ds.TongXuat ELSE 0 END), 0) AS TonDauKy,
                COALESCE(SUM(CASE WHEN ds.Ngay BETWEEN ? AND ? THEN ds.TongNhap ELSE 0 END), 0) AS NhapTrongKy,
                COALESCE(SUM(CASE WHEN ds.Ngay BETWEEN ? AND ? THEN ds.TongXuat ELSE 0 END), 0) AS XuatTrongKy,
                COALESCE(SUM(CASE WHEN ds.Ngay <= ? THEN ds.TongNhap - ds.TongXuat ELSE 0 END), 0) AS TonCuoiKy
            FROM AllProducts p
            LEFT JOIN DailySummary ds ON p.MaThuoc = ds.MaThuoc
            GROUP BY p.MaThuoc, p.TenThuoc, p.DVT
            HAVING
                COALESCE(SUM(CASE WHEN ds.Ngay <= ? THEN ds.TongNhap - ds.TongXuat ELSE 0 END), 0) <> 0
                OR COALESCE(SUM(CASE WHEN ds.Ngay BETWEEN ? AND ? THEN ds.TongNhap ELSE 0 END), 0) <> 0
                OR COALESCE(SUM(CASE WHEN ds.Ngay BETWEEN ? AND ? THEN ds.TongXuat ELSE 0 END), 0) <> 0
            ORDER BY p.TenThuoc
            """;

    public JdbcReportRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public List<ThongKeBanHang> findThongKeBanHangByDateRange(DateRangeRequest request, ReportBucket bucket) {
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = buildThongKeBanHangSql(bucket);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                bindDateRangeTwice(statement, request);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<ThongKeBanHang> results = new ArrayList<>();
                    while (resultSet.next()) {
                        results.add(new ThongKeBanHang(
                                resultSet.getString("ThoiGian"),
                                resultSet.getInt("SoLuongHoaDon"),
                                resultSet.getDouble("TongGiaTri"),
                                resultSet.getDouble("GiamGia"),
                                resultSet.getInt("SoLuongDonTra"),
                                resultSet.getDouble("GiaTriDonTra"),
                                resultSet.getDouble("DoanhThu")
                        ));
                    }
                    return results;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query sales summary report.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<HoaDonDisplay> findHoaDonByDateRange(DateRangeRequest request) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(HOA_DON_BY_RANGE_SQL)) {
                statement.setDate(1, Date.valueOf(request.getFromDate()));
                statement.setDate(2, Date.valueOf(request.getToDate()));
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<HoaDonDisplay> results = new ArrayList<>();
                    while (resultSet.next()) {
                        results.add(new HoaDonDisplay(
                                resultSet.getString("MaHD"),
                                resultSet.getObject("ngayLap", LocalDate.class),
                                resultSet.getString("MaKH"),
                                resultSet.getString("MaNV"),
                                resultSet.getDouble("tongTienNet")
                        ));
                    }
                    return results;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query invoice list for report.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<ThongKeTopSanPham> findTopBanChayByDateRange(DateRangeRequest request, int limit) {
        return queryTopProducts(TOP_BAN_CHAY_SQL, request, limit);
    }

    @Override
    public List<ThongKeTopSanPham> findTopDoanhThuByDateRange(DateRangeRequest request, int limit) {
        return queryTopProducts(TOP_DOANH_THU_SQL, request, limit);
    }

    @Override
    public List<ThongKeTonKho> findThongKeXnt(DateRangeRequest request) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(THONG_KE_XNT_SQL)) {
                LocalDate fromDate = request.getFromDate();
                LocalDate toDate = request.getToDate();
                statement.setDate(1, Date.valueOf(fromDate));
                statement.setDate(2, Date.valueOf(fromDate));
                statement.setDate(3, Date.valueOf(toDate));
                statement.setDate(4, Date.valueOf(fromDate));
                statement.setDate(5, Date.valueOf(toDate));
                statement.setDate(6, Date.valueOf(toDate));
                statement.setDate(7, Date.valueOf(toDate));
                statement.setDate(8, Date.valueOf(fromDate));
                statement.setDate(9, Date.valueOf(toDate));
                statement.setDate(10, Date.valueOf(fromDate));
                statement.setDate(11, Date.valueOf(toDate));
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<ThongKeTonKho> results = new ArrayList<>();
                    while (resultSet.next()) {
                        results.add(new ThongKeTonKho(
                                resultSet.getString("MaThuoc"),
                                resultSet.getString("TenThuoc"),
                                resultSet.getString("DVT"),
                                resultSet.getInt("TonDauKy"),
                                resultSet.getInt("NhapTrongKy"),
                                resultSet.getInt("XuatTrongKy"),
                                resultSet.getInt("TonCuoiKy")
                        ));
                    }
                    return results;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query XNT report.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<ThuocHetHan> findThuocHetHan() {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(THUOC_HET_HAN_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                List<ThuocHetHan> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(new ThuocHetHan(
                            resultSet.getString("maThuocHH"),
                            resultSet.getString("tenThuocHH"),
                            resultSet.getInt("soLuong"),
                            resultSet.getObject("ngayHetHan", LocalDate.class)
                    ));
                }
                return results;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query expiring products report.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<RevenuePointDTO> findRevenueByDateRange(DateRangeRequest request) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(REVENUE_BY_RANGE_SQL)) {
                statement.setDate(1, Date.valueOf(request.getFromDate()));
                statement.setDate(2, Date.valueOf(request.getToDate()));
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<RevenuePointDTO> results = new ArrayList<>();
                    while (resultSet.next()) {
                        results.add(new RevenuePointDTO(
                                resultSet.getObject("bucket_date", LocalDate.class),
                                resultSet.getBigDecimal("revenue")
                        ));
                    }
                    return results;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query revenue report.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<TopSellingProductDTO> findTopSellingProducts(DateRangeRequest request, int limit) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(TOP_SELLING_BY_RANGE_SQL)) {
                statement.setDate(1, Date.valueOf(request.getFromDate()));
                statement.setDate(2, Date.valueOf(request.getToDate()));
                statement.setInt(3, limit);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<TopSellingProductDTO> results = new ArrayList<>();
                    while (resultSet.next()) {
                        results.add(new TopSellingProductDTO(
                                resultSet.getString("MaThuoc"),
                                resultSet.getString("TenThuoc"),
                                resultSet.getLong("quantity_sold"),
                                resultSet.getBigDecimal("revenue")
                        ));
                    }
                    return results;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query top selling products report.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<ExpiringLotDTO> findExpiredLots() {
        return queryExpiringLots(EXPIRED_LOTS_SQL, null);
    }

    @Override
    public List<ExpiringLotDTO> findExpiringLots(int thresholdDays) {
        return queryExpiringLots(EXPIRING_LOTS_SQL, thresholdDays);
    }

    private List<ThongKeTopSanPham> queryTopProducts(String sql, DateRangeRequest request, int limit) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDate(1, Date.valueOf(request.getFromDate()));
                statement.setDate(2, Date.valueOf(request.getToDate()));
                statement.setInt(3, limit);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<ThongKeTopSanPham> results = new ArrayList<>();
                    int stt = 1;
                    while (resultSet.next()) {
                        results.add(new ThongKeTopSanPham(
                                stt++,
                                resultSet.getString("MaThuoc"),
                                resultSet.getString("TenThuoc"),
                                resultSet.getString("DVT_CoBan"),
                                resultSet.getInt("TongSoLuong"),
                                resultSet.getDouble("TongDoanhThu")
                        ));
                    }
                    return results;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query top products report.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private void bindDateRangeTwice(PreparedStatement statement, DateRangeRequest request) throws Exception {
        statement.setDate(1, Date.valueOf(request.getFromDate()));
        statement.setDate(2, Date.valueOf(request.getToDate()));
        statement.setDate(3, Date.valueOf(request.getFromDate()));
        statement.setDate(4, Date.valueOf(request.getToDate()));
    }

    private String buildThongKeBanHangSql(ReportBucket bucket) {
        BucketExpression expression = switch (bucket) {
            case HOUR -> new BucketExpression("DATE_FORMAT(%s, '%H:00')", "HOUR(%s)");
            case MONTH -> new BucketExpression("DATE_FORMAT(%s, '%m/%Y')", "MONTH(%s)");
            default -> new BucketExpression("DATE_FORMAT(%s, '%d/%m')", "DATE(%s)");
        };

        String salesLabel = expression.labelExpression("hd.NgayLap");
        String salesSort = expression.sortExpression("hd.NgayLap");
        String returnsLabel = expression.labelExpression("pt.NgayLap");
        String returnsSort = expression.sortExpression("pt.NgayLap");

        return """
                SELECT
                    bucket_label AS ThoiGian,
                    SUM(sales_invoice_count) AS SoLuongHoaDon,
                    ROUND(SUM(gross_value), 2) AS TongGiaTri,
                    ROUND(SUM(discount_value), 2) AS GiamGia,
                    SUM(return_count) AS SoLuongDonTra,
                    ROUND(SUM(return_value), 2) AS GiaTriDonTra,
                    ROUND(((SUM(gross_value) - SUM(discount_value)) - SUM(return_value)) * 1.05, 2) AS DoanhThu
                FROM (
                    SELECT
                        %s AS bucket_label,
                        %s AS sort_key,
                        COUNT(DISTINCT hd.MaHD) AS sales_invoice_count,
                        COALESCE(SUM(cthd.SoLuong * cthd.DonGia), 0) AS gross_value,
                        COALESCE(SUM(cthd.SoLuong * cthd.GiamGia), 0) AS discount_value,
                        0 AS return_count,
                        0 AS return_value
                    FROM HoaDon hd
                    JOIN ChiTietHoaDon cthd ON hd.MaHD = cthd.MaHD
                    WHERE DATE(hd.NgayLap) BETWEEN ? AND ?
                    GROUP BY bucket_label, sort_key

                    UNION ALL

                    SELECT
                        %s AS bucket_label,
                        %s AS sort_key,
                        0 AS sales_invoice_count,
                        0 AS gross_value,
                        0 AS discount_value,
                        COUNT(DISTINCT pt.MaPT) AS return_count,
                        COALESCE(SUM(ctpt.SoLuong * (ctpt.DonGia - ctpt.GiamGia)), 0) AS return_value
                    FROM PhieuTraHang pt
                    JOIN ChiTietPhieuTraHang ctpt ON pt.MaPT = ctpt.MaPT
                    WHERE DATE(pt.NgayLap) BETWEEN ? AND ?
                    GROUP BY bucket_label, sort_key
                ) aggregated
                GROUP BY bucket_label, sort_key
                ORDER BY sort_key
                """.formatted(salesLabel, salesSort, returnsLabel, returnsSort);
    }

    private List<ExpiringLotDTO> queryExpiringLots(String sql, Integer thresholdDays) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (thresholdDays != null) {
                    statement.setInt(1, thresholdDays);
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<ExpiringLotDTO> results = new ArrayList<>();
                    while (resultSet.next()) {
                        results.add(new ExpiringLotDTO(
                                resultSet.getString("MaLH"),
                                resultSet.getString("MaThuoc"),
                                resultSet.getString("TenThuoc"),
                                resultSet.getObject("HSD", LocalDate.class),
                                resultSet.getInt("SoLuongTon"),
                                resultSet.getLong("days_to_expiry")
                        ));
                    }
                    return results;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not query expiry report.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private record BucketExpression(String labelTemplate, String sortTemplate) {
        private String labelExpression(String column) {
            return labelTemplate.formatted(column);
        }

        private String sortExpression(String column) {
            return sortTemplate.formatted(column);
        }
    }
}
