package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.dto.ExpiringLotDTO;
import com.example.pharmacy.common.dto.RevenuePointDTO;
import com.example.pharmacy.common.dto.TopSellingProductDTO;
import com.example.pharmacy.common.request.DateRangeRequest;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.ReportRepository;

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

    public JdbcReportRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
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
}
