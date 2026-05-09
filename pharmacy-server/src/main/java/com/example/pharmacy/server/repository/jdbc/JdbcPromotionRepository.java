package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.PromotionRepository;
import com.example.pharmacy.common.model.ChiTietDonViTinh;
import com.example.pharmacy.common.model.ChiTietKhuyenMai;
import com.example.pharmacy.common.model.DonViTinh;
import com.example.pharmacy.common.model.KeHang;
import com.example.pharmacy.common.model.KhuyenMai;
import com.example.pharmacy.common.model.LoaiHang;
import com.example.pharmacy.common.model.LoaiKhuyenMai;
import com.example.pharmacy.common.model.NhomDuocLy;
import com.example.pharmacy.common.model.Thuoc_SP_TangKem;
import com.example.pharmacy.common.model.Thuoc_SanPham;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class JdbcPromotionRepository extends AbstractJdbcRepository implements PromotionRepository {
    private static final String PROMOTION_SELECT_FRAGMENT = """
            SELECT km.MaKM,
                   km.TenKM,
                   km.GiaTriKM,
                   km.GiaTriApDung,
                   km.NgayBatDau,
                   km.NgayKetThuc,
                   km.MoTa,
                   km.NgayTao,
                   lkm.MaLoai AS LKM_MaLoai,
                   lkm.TenLoai AS LKM_TenLoai,
                   lkm.MoTa AS LKM_MoTa
            FROM KhuyenMai km
            JOIN LoaiKhuyenMai lkm ON lkm.MaLoai = km.MaLoai
            """;
    private static final String SELECT_ALL_PROMOTIONS_SQL = PROMOTION_SELECT_FRAGMENT + """
            ORDER BY km.NgayBatDau DESC, km.MaKM DESC
            """;
    private static final String SELECT_PROMOTION_BY_ID_SQL = PROMOTION_SELECT_FRAGMENT + """
            WHERE km.MaKM = ?
            """;
    private static final String SEARCH_PROMOTIONS_SQL = PROMOTION_SELECT_FRAGMENT + """
            WHERE LOWER(km.TenKM) LIKE ? OR LOWER(km.MaKM) LIKE ?
            ORDER BY km.NgayBatDau DESC, km.MaKM DESC
            """;
    private static final String SELECT_ALL_PROMOTION_TYPES_SQL = """
            SELECT MaLoai, TenLoai, MoTa
            FROM LoaiKhuyenMai
            ORDER BY TenLoai ASC, MaLoai ASC
            """;
    private static final String SELECT_PROMOTION_TYPE_BY_ID_SQL = """
            SELECT MaLoai, TenLoai, MoTa
            FROM LoaiKhuyenMai
            WHERE MaLoai = ?
            """;
    private static final String SELECT_PROMOTION_TYPE_BY_NAME_SQL = """
            SELECT MaLoai, TenLoai, MoTa
            FROM LoaiKhuyenMai
            WHERE LOWER(TenLoai) = LOWER(?)
            """;
    private static final String MEDICINE_SELECT_FRAGMENT = """
            ts.MaThuoc,
            ts.TenThuoc,
            ts.HamLuong,
            ts.DonViHL,
            ts.DuongDung,
            ts.QuyCachDongGoi,
            ts.SDK_GPNK,
            ts.HangSX,
            ts.NuocSX,
            ts.HinhAnh,
            ts.ETC,
            ts.TrangThaiXoa,
            ndl.MaNDL AS NDL_MaNDL,
            ndl.TenNDL AS NDL_TenNDL,
            ndl.MoTa AS NDL_MoTa,
            lh.MaLoaiHang AS LH_MaLoaiHang,
            lh.TenLH AS LH_TenLH,
            lh.MoTa AS LH_MoTa,
            kh.MaKe AS KE_MaKe,
            kh.TenKe AS KE_TenKe,
            kh.MoTa AS KE_MoTa
            """;
    private static final String SELECT_PROMOTION_DETAILS_SQL = """
            SELECT ct.MaKM,
                   ct.MaThuoc,
                   ct.SLApDung,
                   ct.SLToiDa,
            """ + MEDICINE_SELECT_FRAGMENT + """
            FROM ChiTietKhuyenMai ct
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = ct.MaThuoc
            LEFT JOIN NhomDuocLy ndl ON ndl.MaNDL = ts.MaNDL
            LEFT JOIN LoaiHang lh ON lh.MaLoaiHang = ts.MaLoaiHang
            LEFT JOIN KeHang kh ON kh.MaKe = ts.ViTri
            WHERE ct.MaKM = ?
            ORDER BY ts.TenThuoc ASC, ct.MaThuoc ASC
            """;
    private static final String SELECT_PROMOTION_GIFTS_SQL = """
            SELECT tg.MaKM,
                   tg.MaThuocTangKem,
                   tg.SoLuong,
            """ + MEDICINE_SELECT_FRAGMENT.replace("ts.MaThuoc,", "ts.MaThuoc AS MaThuocTangKem,") + """
            FROM Thuoc_SP_TangKem tg
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = tg.MaThuocTangKem
            LEFT JOIN NhomDuocLy ndl ON ndl.MaNDL = ts.MaNDL
            LEFT JOIN LoaiHang lh ON lh.MaLoaiHang = ts.MaLoaiHang
            LEFT JOIN KeHang kh ON kh.MaKe = ts.ViTri
            WHERE tg.MaKM = ?
            ORDER BY ts.TenThuoc ASC, tg.MaThuocTangKem ASC
            """;
    private static final String INSERT_PROMOTION_SQL = """
            INSERT INTO KhuyenMai (
                MaKM, MaLoai, TenKM, GiaTriKM, NgayBatDau, NgayKetThuc, MoTa, GiaTriApDung
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_PROMOTION_SQL = """
            UPDATE KhuyenMai
            SET MaLoai = ?,
                TenKM = ?,
                GiaTriKM = ?,
                NgayBatDau = ?,
                NgayKetThuc = ?,
                MoTa = ?,
                GiaTriApDung = ?
            WHERE MaKM = ?
            """;
    private static final String DELETE_PROMOTION_SQL = """
            DELETE FROM KhuyenMai
            WHERE MaKM = ?
            """;
    private static final String INSERT_PROMOTION_DETAIL_SQL = """
            INSERT INTO ChiTietKhuyenMai (MaThuoc, MaKM, SLApDung, SLToiDa)
            VALUES (?, ?, ?, ?)
            """;
    private static final String DELETE_PROMOTION_DETAILS_SQL = """
            DELETE FROM ChiTietKhuyenMai
            WHERE MaKM = ?
            """;
    private static final String INSERT_PROMOTION_GIFT_SQL = """
            INSERT INTO Thuoc_SP_TangKem (MaThuocTangKem, MaKM, SoLuong)
            VALUES (?, ?, ?)
            """;
    private static final String DELETE_PROMOTION_GIFTS_SQL = """
            DELETE FROM Thuoc_SP_TangKem
            WHERE MaKM = ?
            """;
    private static final String SELECT_ACTIVE_PROMOTIONS_SQL = PROMOTION_SELECT_FRAGMENT + """
            WHERE km.NgayBatDau <= ? AND km.NgayKetThuc >= ?
            ORDER BY km.NgayBatDau DESC, km.MaKM DESC
            """;
    private static final String SELECT_ACTIVE_INVOICE_PROMOTIONS_SQL = PROMOTION_SELECT_FRAGMENT + """
            WHERE km.NgayBatDau <= ? AND km.NgayKetThuc >= ? AND km.MaLoai IN ('LKM004', 'LKM005')
            ORDER BY km.NgayBatDau DESC, km.MaKM DESC
            """;

    public JdbcPromotionRepository(JdbcConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public List<KhuyenMai> findAll() {
        Connection connection = null;
        List<KhuyenMai> promotions = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PROMOTIONS_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    promotions.add(mapPromotion(resultSet));
                }
            }
            return promotions;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load promotions.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public KhuyenMai findById(String maKhuyenMai) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_PROMOTION_BY_ID_SQL)) {
                statement.setString(1, maKhuyenMai);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapPromotion(resultSet);
                    }
                    return null;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load promotion " + maKhuyenMai, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<KhuyenMai> searchByKeyword(String keyword) {
        Connection connection = null;
        List<KhuyenMai> promotions = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SEARCH_PROMOTIONS_SQL)) {
                String normalizedKeyword = "%" + (keyword == null ? "" : keyword.trim().toLowerCase()) + "%";
                statement.setString(1, normalizedKeyword);
                statement.setString(2, normalizedKeyword);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        promotions.add(mapPromotion(resultSet));
                    }
                }
            }
            return promotions;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not search promotions.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<LoaiKhuyenMai> findAllLoaiKhuyenMai() {
        return loadPromotionTypes(SELECT_ALL_PROMOTION_TYPES_SQL, null);
    }

    @Override
    public LoaiKhuyenMai findLoaiKhuyenMaiById(String maLoaiKhuyenMai) {
        List<LoaiKhuyenMai> types = loadPromotionTypes(SELECT_PROMOTION_TYPE_BY_ID_SQL, maLoaiKhuyenMai);
        return types.isEmpty() ? null : types.get(0);
    }

    @Override
    public LoaiKhuyenMai findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai) {
        List<LoaiKhuyenMai> types = loadPromotionTypes(SELECT_PROMOTION_TYPE_BY_NAME_SQL, tenLoaiKhuyenMai);
        return types.isEmpty() ? null : types.get(0);
    }

    @Override
    public List<ChiTietKhuyenMai> findChiTietByMaKM(String maKhuyenMai) {
        Connection connection = null;
        List<ChiTietKhuyenMai> details = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_PROMOTION_DETAILS_SQL)) {
                statement.setString(1, maKhuyenMai);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        ChiTietKhuyenMai detail = new ChiTietKhuyenMai();
                        KhuyenMai promotion = new KhuyenMai();
                        promotion.setMaKM(resultSet.getString("MaKM"));
                        detail.setKhuyenMai(promotion);
                        detail.setThuoc(mapMedicine(resultSet, "MaThuoc"));
                        detail.setSlApDung(resultSet.getInt("SLApDung"));
                        detail.setSoHDToiDa(resultSet.getInt("SLToiDa"));
                        details.add(detail);
                    }
                }
            }
            attachUnitDetailsToPromotionMedicines(details.stream().map(ChiTietKhuyenMai::getThuoc).toList());
            return details;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load promotion details for " + maKhuyenMai, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Thuoc_SP_TangKem> findQuaTangByMaKM(String maKhuyenMai) {
        Connection connection = null;
        List<Thuoc_SP_TangKem> gifts = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_PROMOTION_GIFTS_SQL)) {
                statement.setString(1, maKhuyenMai);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Thuoc_SP_TangKem gift = new Thuoc_SP_TangKem();
                        KhuyenMai promotion = new KhuyenMai();
                        promotion.setMaKM(resultSet.getString("MaKM"));
                        gift.setKhuyenmai(promotion);
                        gift.setThuocTangKem(mapMedicine(resultSet, "MaThuocTangKem"));
                        gift.setSoLuong(resultSet.getInt("SoLuong"));
                        gifts.add(gift);
                    }
                }
            }
            attachUnitDetailsToPromotionMedicines(gifts.stream().map(Thuoc_SP_TangKem::getThuocTangKem).toList());
            return gifts;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load promotion gifts for " + maKhuyenMai, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean insertPromotion(KhuyenMai khuyenMai) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_PROMOTION_SQL)) {
                bindPromotion(statement, khuyenMai);
                return statement.executeUpdate() > 0;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert promotion " + khuyenMai.getMaKM(), exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean updatePromotion(KhuyenMai khuyenMai) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_PROMOTION_SQL)) {
                statement.setString(1, khuyenMai.getLoaiKM() == null ? null : khuyenMai.getLoaiKM().getMaLoai());
                statement.setString(2, khuyenMai.getTenKM());
                statement.setFloat(3, khuyenMai.getGiaTriKM());
                statement.setDate(4, khuyenMai.getNgayBatDau());
                statement.setDate(5, khuyenMai.getNgayKetThuc());
                statement.setString(6, khuyenMai.getMoTa());
                statement.setDouble(7, khuyenMai.getGiaTriApDung());
                statement.setString(8, khuyenMai.getMaKM());
                return statement.executeUpdate() > 0;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not update promotion " + khuyenMai.getMaKM(), exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean deletePromotionById(String maKhuyenMai) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(DELETE_PROMOTION_SQL)) {
                statement.setString(1, maKhuyenMai);
                return statement.executeUpdate() > 0;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not delete promotion " + maKhuyenMai, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertPromotionDetail(ChiTietKhuyenMai chiTietKhuyenMai) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_PROMOTION_DETAIL_SQL)) {
                statement.setString(1, chiTietKhuyenMai.getThuoc().getMaThuoc());
                statement.setString(2, chiTietKhuyenMai.getKhuyenMai().getMaKM());
                statement.setInt(3, chiTietKhuyenMai.getSlApDung());
                statement.setInt(4, chiTietKhuyenMai.getSoHDToiDa());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert promotion detail for " + chiTietKhuyenMai.getKhuyenMai().getMaKM(), exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void deletePromotionDetailsByMaKM(String maKhuyenMai) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(DELETE_PROMOTION_DETAILS_SQL)) {
                statement.setString(1, maKhuyenMai);
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not delete promotion details for " + maKhuyenMai, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertPromotionGift(Thuoc_SP_TangKem quaTangKhuyenMai) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_PROMOTION_GIFT_SQL)) {
                statement.setString(1, quaTangKhuyenMai.getThuocTangKem().getMaThuoc());
                statement.setString(2, quaTangKhuyenMai.getKhuyenmai().getMaKM());
                statement.setInt(3, quaTangKhuyenMai.getSoLuong());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert promotion gift for " + quaTangKhuyenMai.getKhuyenmai().getMaKM(), exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void deletePromotionGiftsByMaKM(String maKhuyenMai) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(DELETE_PROMOTION_GIFTS_SQL)) {
                statement.setString(1, maKhuyenMai);
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not delete promotion gifts for " + maKhuyenMai, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<KhuyenMai> findActiveOn(Date ngay) {
        return loadActivePromotions(SELECT_ACTIVE_PROMOTIONS_SQL, ngay);
    }

    @Override
    public List<KhuyenMai> findActiveInvoiceOn(Date ngay) {
        return loadActivePromotions(SELECT_ACTIVE_INVOICE_PROMOTIONS_SQL, ngay);
    }

    private List<LoaiKhuyenMai> loadPromotionTypes(String sql, String parameter) {
        Connection connection = null;
        List<LoaiKhuyenMai> types = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (parameter != null) {
                    statement.setString(1, parameter);
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        types.add(mapPromotionType(resultSet, ""));
                    }
                }
            }
            return types;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load promotion types.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private List<KhuyenMai> loadActivePromotions(String sql, Date ngay) {
        Connection connection = null;
        List<KhuyenMai> promotions = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDate(1, ngay);
                statement.setDate(2, ngay);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        promotions.add(mapPromotion(resultSet));
                    }
                }
            }
            return promotions;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load active promotions.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private void bindPromotion(PreparedStatement statement, KhuyenMai khuyenMai) throws SQLException {
        statement.setString(1, khuyenMai.getMaKM());
        statement.setString(2, khuyenMai.getLoaiKM() == null ? null : khuyenMai.getLoaiKM().getMaLoai());
        statement.setString(3, khuyenMai.getTenKM());
        statement.setFloat(4, khuyenMai.getGiaTriKM());
        statement.setDate(5, khuyenMai.getNgayBatDau());
        statement.setDate(6, khuyenMai.getNgayKetThuc());
        statement.setString(7, khuyenMai.getMoTa());
        statement.setDouble(8, khuyenMai.getGiaTriApDung());
    }

    private KhuyenMai mapPromotion(ResultSet resultSet) throws SQLException {
        KhuyenMai promotion = new KhuyenMai();
        promotion.setMaKM(resultSet.getString("MaKM"));
        promotion.setLoaiKM(mapPromotionType(resultSet, "LKM_"));
        promotion.setTenKM(resultSet.getString("TenKM"));
        promotion.setGiaTriKM(resultSet.getFloat("GiaTriKM"));
        promotion.setNgayBatDau(resultSet.getDate("NgayBatDau"));
        promotion.setNgayKetThuc(resultSet.getDate("NgayKetThuc"));
        promotion.setMoTa(resultSet.getString("MoTa"));
        promotion.setNgayTao(resultSet.getTimestamp("NgayTao"));
        promotion.setGiaTriApDung(resultSet.getDouble("GiaTriApDung"));
        return promotion;
    }

    private LoaiKhuyenMai mapPromotionType(ResultSet resultSet, String prefix) throws SQLException {
        String maLoai = resultSet.getString(prefix + "MaLoai");
        if (maLoai == null) {
            return null;
        }
        LoaiKhuyenMai type = new LoaiKhuyenMai();
        type.setMaLoai(maLoai);
        type.setTenLoai(resultSet.getString(prefix + "TenLoai"));
        type.setMoTa(resultSet.getString(prefix + "MoTa"));
        return type;
    }

    private Thuoc_SanPham mapMedicine(ResultSet resultSet, String maThuocColumn) throws SQLException {
        Thuoc_SanPham medicine = new Thuoc_SanPham();
        medicine.setMaThuoc(resultSet.getString(maThuocColumn));
        medicine.setTenThuoc(resultSet.getString("TenThuoc"));
        medicine.setHamLuong(resultSet.getFloat("HamLuong"));
        medicine.setDonViHamLuong(resultSet.getString("DonViHL"));
        medicine.setDuongDung(resultSet.getString("DuongDung"));
        medicine.setQuyCachDongGoi(resultSet.getString("QuyCachDongGoi"));
        medicine.setSDK_GPNK(resultSet.getString("SDK_GPNK"));
        medicine.setHangSX(resultSet.getString("HangSX"));
        medicine.setNuocSX(resultSet.getString("NuocSX"));
        medicine.setHinhAnh(resultSet.getBytes("HinhAnh"));
        medicine.setETC(resultSet.getBoolean("ETC"));
        medicine.setNhomDuocLy(mapNhomDuocLy(resultSet));
        medicine.setLoaiHang(mapLoaiHang(resultSet));
        medicine.setVitri(mapKeHang(resultSet));
        return medicine;
    }

    private NhomDuocLy mapNhomDuocLy(ResultSet resultSet) throws SQLException {
        String maNdl = resultSet.getString("NDL_MaNDL");
        if (maNdl == null) {
            return null;
        }
        NhomDuocLy nhomDuocLy = new NhomDuocLy();
        nhomDuocLy.setMaNDL(maNdl);
        nhomDuocLy.setTenNDL(resultSet.getString("NDL_TenNDL"));
        nhomDuocLy.setMoTa(resultSet.getString("NDL_MoTa"));
        return nhomDuocLy;
    }

    private LoaiHang mapLoaiHang(ResultSet resultSet) throws SQLException {
        String maLoaiHang = resultSet.getString("LH_MaLoaiHang");
        if (maLoaiHang == null) {
            return null;
        }
        LoaiHang loaiHang = new LoaiHang();
        loaiHang.setMaLoaiHang(maLoaiHang);
        loaiHang.setTenLoaiHang(resultSet.getString("LH_TenLH"));
        loaiHang.setMoTa(resultSet.getString("LH_MoTa"));
        return loaiHang;
    }

    private KeHang mapKeHang(ResultSet resultSet) throws SQLException {
        String maKe = resultSet.getString("KE_MaKe");
        if (maKe == null) {
            return null;
        }
        KeHang keHang = new KeHang();
        keHang.setMaKe(maKe);
        keHang.setTenKe(resultSet.getString("KE_TenKe"));
        keHang.setMoTa(resultSet.getString("KE_MoTa"));
        return keHang;
    }

    private void attachUnitDetailsToPromotionMedicines(Collection<Thuoc_SanPham> medicines) {
        if (medicines == null || medicines.isEmpty()) {
            return;
        }

        Map<String, Thuoc_SanPham> medicinesById = new LinkedHashMap<>();
        for (Thuoc_SanPham medicine : medicines) {
            if (medicine != null && medicine.getMaThuoc() != null && !medicine.getMaThuoc().isBlank()) {
                medicinesById.put(medicine.getMaThuoc(), medicine);
            }
        }
        if (medicinesById.isEmpty()) {
            return;
        }

        String sql = """
                SELECT ctdvt.MaThuoc,
                       ctdvt.MaDVT,
                       ctdvt.HeSoQuyDoi,
                       ctdvt.GiaNhap,
                       ctdvt.GiaBan,
                       ctdvt.DonViCoBan,
                       dvt.TenDonViTinh,
                       dvt.KiHieu
                FROM ChiTietDonViTinh ctdvt
                JOIN DonViTinh dvt ON dvt.MaDVT = ctdvt.MaDVT
                WHERE ctdvt.MaThuoc IN (%s)
                ORDER BY ctdvt.MaThuoc ASC, ctdvt.DonViCoBan DESC, ctdvt.HeSoQuyDoi DESC, ctdvt.MaDVT ASC
                """.formatted(placeholders(medicinesById.size()));

        Connection connection = null;
        Map<String, List<ChiTietDonViTinh>> detailsByMedicineId = new LinkedHashMap<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int index = 1;
                for (String maThuoc : medicinesById.keySet()) {
                    statement.setString(index++, maThuoc);
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String maThuoc = resultSet.getString("MaThuoc");
                        ChiTietDonViTinh detail = new ChiTietDonViTinh();
                        DonViTinh donViTinh = new DonViTinh();
                        donViTinh.setMaDVT(resultSet.getString("MaDVT"));
                        donViTinh.setTenDonViTinh(resultSet.getString("TenDonViTinh"));
                        donViTinh.setKiHieu(resultSet.getString("KiHieu"));
                        detail.setDvt(donViTinh);
                        detail.setHeSoQuyDoi(resultSet.getDouble("HeSoQuyDoi"));
                        detail.setGiaNhap(resultSet.getDouble("GiaNhap"));
                        detail.setGiaBan(resultSet.getDouble("GiaBan"));
                        detail.setDonViCoBan(resultSet.getBoolean("DonViCoBan"));
                        detailsByMedicineId.computeIfAbsent(maThuoc, ignored -> new ArrayList<>()).add(detail);
                    }
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load promotion medicine units.", exception);
        } finally {
            closeConnection(connection);
        }

        for (Map.Entry<String, Thuoc_SanPham> entry : medicinesById.entrySet()) {
            Thuoc_SanPham medicine = entry.getValue();
            List<ChiTietDonViTinh> details = detailsByMedicineId.getOrDefault(entry.getKey(), new ArrayList<>());
            for (ChiTietDonViTinh detail : details) {
                detail.setThuoc(medicine);
            }
            medicine.setDsCTDVT(details);
            medicine.setDvcb(details.stream().filter(ChiTietDonViTinh::isDonViCoBan).findFirst().orElse(null));
        }
    }

    private String placeholders(int count) {
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < count; i++) {
            joiner.add("?");
        }
        return joiner.toString();
    }
}
