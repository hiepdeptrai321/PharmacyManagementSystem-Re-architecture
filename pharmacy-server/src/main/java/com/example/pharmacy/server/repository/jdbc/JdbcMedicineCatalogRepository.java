package com.example.pharmacy.server.repository.jdbc;

import com.example.pharmacy.common.request.CreateThuocRequest;
import com.example.pharmacy.server.config.JdbcConnectionProvider;
import com.example.pharmacy.server.repository.MedicineCatalogRepository;
import com.example.pharmacy.common.model.ChiTietDonViTinhDto;
import com.example.pharmacy.common.model.ChiTietHoatChatDto;
import com.example.pharmacy.common.model.DonViTinhDto;
import com.example.pharmacy.common.model.HoatChatDto;
import com.example.pharmacy.common.model.KeHangDto;
import com.example.pharmacy.common.model.LoaiHangDto;
import com.example.pharmacy.common.model.NhomDuocLyDto;
import com.example.pharmacy.common.model.ThuocTonKhoDto;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;
import com.example.pharmacy.common.model.Thuoc_SanPhamDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class JdbcMedicineCatalogRepository extends AbstractJdbcRepository implements MedicineCatalogRepository {
    private static final String SELECT_MEDICINE_SQL = """
            SELECT ts.MaThuoc,
                   ts.TenThuoc,
                   ts.HamLuong,
                   ts.DonViHL,
                   ts.DuongDung,
                   ts.QuyCachDongGoi,
                   ts.SDK_GPNK,
                   ts.HangSX,
                   ts.NuocSX,
                   ts.HinhAnh,
                   ts.MaLoaiHang,
                   ts.MaNDL,
                   ts.ViTri,
                   ts.TrangThaiXoa,
                   ts.ETC,
                   ndl.TenNDL,
                   lh.TenLH,
                   kh.TenKe
            FROM Thuoc_SanPham ts
            LEFT JOIN NhomDuocLy ndl ON ndl.MaNDL = ts.MaNDL
            LEFT JOIN LoaiHang lh ON lh.MaLoaiHang = ts.MaLoaiHang
            LEFT JOIN KeHang kh ON kh.MaKe = ts.ViTri
            ORDER BY ts.TenThuoc
            """;
    private static final String SELECT_ALL_MEDICINES_SQL = SELECT_MEDICINE_SQL + """
            WHERE ts.TrangThaiXoa = 0
            ORDER BY ts.TenThuoc ASC, ts.MaThuoc ASC
            """;
    private static final String SELECT_ALL_LOAI_HANG_SQL = """
            SELECT MaLoaiHang, TenLH, MoTa
            FROM LoaiHang
            ORDER BY TenLH ASC, MaLoaiHang ASC
            """;
    private static final String SELECT_ALL_LOAI_HANG_NAMES_SQL = """
            SELECT TenLH
            FROM LoaiHang
            ORDER BY TenLH ASC, MaLoaiHang ASC
            """;
    private static final String SELECT_ALL_HOAT_CHAT_SQL = """
            SELECT MaHoatChat, TenHoatChat
            FROM HoatChat
            ORDER BY TenHoatChat ASC, MaHoatChat ASC
            """;
    private static final String SELECT_CHI_TIET_HOAT_CHAT_BY_MA_THUOC_SQL = """
            SELECT ct.MaThuoc,
                   ct.MaHoatChat,
                   ct.HamLuong,
                   hc.TenHoatChat,
                   ts.TenThuoc
            FROM ChiTietHoatChat ct
            JOIN HoatChat hc ON hc.MaHoatChat = ct.MaHoatChat
            LEFT JOIN Thuoc_SanPham ts ON ts.MaThuoc = ct.MaThuoc
            WHERE ct.MaThuoc = ?
            ORDER BY hc.TenHoatChat ASC, hc.MaHoatChat ASC
            """;
    private static final String INSERT_MEDICINE_SQL = """
            INSERT INTO Thuoc_SanPham (
                MaThuoc, TenThuoc, HamLuong, DonViHL, DuongDung, QuyCachDongGoi,
                SDK_GPNK, HangSX, NuocSX, HinhAnh, MaLoaiHang, MaNDL, ViTri, TrangThaiXoa, ETC
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?)
            """;
    private static final String UPDATE_MEDICINE_SQL = """
            UPDATE Thuoc_SanPham
            SET TenThuoc = ?,
                HamLuong = ?,
                DonViHL = ?,
                DuongDung = ?,
                QuyCachDongGoi = ?,
                SDK_GPNK = ?,
                HangSX = ?,
                NuocSX = ?,
                HinhAnh = ?,
                MaLoaiHang = ?,
                MaNDL = ?,
                ViTri = ?,
                ETC = ?
            WHERE MaThuoc = ?
            """;
    private static final String SOFT_DELETE_MEDICINE_SQL = """
            UPDATE Thuoc_SanPham
            SET TrangThaiXoa = 1
            WHERE MaThuoc = ?
            """;
    private static final String INSERT_BASE_UNIT_SQL = """
            INSERT INTO ChiTietDonViTinh (
                MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan
            ) VALUES (?, ?, ?, ?, ?, 1)
            """;
    private static final String INSERT_DEFAULT_BASE_UNIT_SQL = """
            INSERT INTO ChiTietDonViTinh (
                MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan
            )
            SELECT ?, MaDVT, 1.0, 0.0, 0.0, 1
            FROM DonViTinh
            WHERE MaDVT = ?
            """;
    private static final String INSERT_CHI_TIET_HOAT_CHAT_SQL = """
            INSERT INTO ChiTietHoatChat (MaThuoc, MaHoatChat, HamLuong)
            VALUES (?, ?, ?)
            """;
    private static final String UPDATE_CHI_TIET_HOAT_CHAT_SQL = """
            UPDATE ChiTietHoatChat
            SET HamLuong = ?
            WHERE MaThuoc = ? AND MaHoatChat = ?
            """;
    private static final String DELETE_CHI_TIET_HOAT_CHAT_SQL = """
            DELETE FROM ChiTietHoatChat
            WHERE MaThuoc = ? AND MaHoatChat = ?
            """;
    private static final String SELECT_TONG_SO_LUONG_TON_SQL = """
            SELECT COALESCE(SUM(SoLuongTon), 0) AS TongSoLuongTon
            FROM Thuoc_SP_TheoLo
            WHERE MaThuoc = ?
            """;
    private static final String SELECT_TEN_DON_VI_CO_BAN_SQL = """
            SELECT dvt.TenDonViTinh
            FROM ChiTietDonViTinh ctdvt
            JOIN DonViTinh dvt ON dvt.MaDVT = ctdvt.MaDVT
            WHERE ctdvt.MaThuoc = ? AND ctdvt.DonViCoBan = 1
            LIMIT 1
            """;
    private static final String SELECT_THUOC_TON_KHO_SQL = """
            SELECT ttl.MaThuoc,
                   tsp.TenThuoc,
                   dvt.TenDonViTinh,
                   COUNT(ttl.MaLH) AS SoLoTon,
                   COALESCE(SUM(ttl.SoLuongTon), 0) AS TongSoLuongTon
            FROM Thuoc_SP_TheoLo ttl
            JOIN Thuoc_SanPham tsp ON tsp.MaThuoc = ttl.MaThuoc
            JOIN ChiTietDonViTinh ctdvt ON ctdvt.MaThuoc = tsp.MaThuoc AND ctdvt.DonViCoBan = 1
            JOIN DonViTinh dvt ON dvt.MaDVT = ctdvt.MaDVT
            GROUP BY ttl.MaThuoc, tsp.TenThuoc, dvt.TenDonViTinh
            ORDER BY tsp.TenThuoc ASC, ttl.MaThuoc ASC
            """;
    private static final String SELECT_ALL_LOTS_SQL = """
            SELECT ttl.MaLH,
                   ttl.SoLuongTon,
                   ttl.NSX,
                   ttl.HSD,
                   ttl.MaThuoc,
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
            FROM Thuoc_SP_TheoLo ttl
            JOIN Thuoc_SanPham ts ON ts.MaThuoc = ttl.MaThuoc
            LEFT JOIN NhomDuocLy ndl ON ndl.MaNDL = ts.MaNDL
            LEFT JOIN LoaiHang lh ON lh.MaLoaiHang = ts.MaLoaiHang
            LEFT JOIN KeHang kh ON kh.MaKe = ts.ViTri
            ORDER BY ts.TenThuoc ASC, ttl.HSD ASC, ttl.NSX ASC, ttl.MaLH ASC
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

    @Override
    public List<Thuoc_SanPhamDto> findAllMedicines() {
        Connection connection = null;
        List<Thuoc_SanPhamDto> medicines = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_MEDICINES_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    medicines.add(mapMedicine(resultSet));
                }
            }
            attachUnitDetails(medicines);
            return medicines;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load medicines.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<LoaiHangDto> findAllLoaiHang() {
        Connection connection = null;
        List<LoaiHangDto> loaiHangs = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_LOAI_HANG_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    loaiHangs.add(mapLoaiHang(resultSet, ""));
                }
            }
            return loaiHangs;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load medicine categories.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<String> findAllLoaiHangNames() {
        Connection connection = null;
        List<String> names = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_LOAI_HANG_NAMES_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    names.add(resultSet.getString("TenLH"));
                }
            }
            return names;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load medicine category names.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<HoatChatDto> findAllHoatChat() {
        Connection connection = null;
        List<HoatChatDto> hoatChats = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_HOAT_CHAT_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    HoatChatDto hoatChat = new HoatChatDto();
                    hoatChat.setMaHoatChat(resultSet.getString("MaHoatChat"));
                    hoatChat.setTenHoatChat(resultSet.getString("TenHoatChat"));
                    hoatChats.add(hoatChat);
                }
            }
            return hoatChats;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load ingredients.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<ChiTietHoatChatDto> findChiTietHoatChatByMaThuoc(String maThuoc) {
        Connection connection = null;
        List<ChiTietHoatChatDto> details = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_CHI_TIET_HOAT_CHAT_BY_MA_THUOC_SQL)) {
                statement.setString(1, maThuoc);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        ChiTietHoatChatDto detail = new ChiTietHoatChatDto();

                        Thuoc_SanPhamDto thuoc = new Thuoc_SanPhamDto();
                        thuoc.setMaThuoc(resultSet.getString("MaThuoc"));
                        thuoc.setTenThuoc(resultSet.getString("TenThuoc"));
                        detail.setThuoc(thuoc);

                        HoatChatDto hoatChat = new HoatChatDto();
                        hoatChat.setMaHoatChat(resultSet.getString("MaHoatChat"));
                        hoatChat.setTenHoatChat(resultSet.getString("TenHoatChat"));
                        detail.setHoatChat(hoatChat);

                        detail.setHamLuong(resultSet.getFloat("HamLuong"));
                        details.add(detail);
                    }
                }
            }
            return details;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load ingredient details for medicine " + maThuoc, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean insertMedicine(Thuoc_SanPhamDto thuoc) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_MEDICINE_SQL)) {
                statement.setString(1, thuoc.getMaThuoc());
                statement.setString(2, thuoc.getTenThuoc());
                statement.setFloat(3, thuoc.getHamLuong());
                statement.setString(4, thuoc.getDonViHamLuong());
                statement.setString(5, thuoc.getDuongDung());
                statement.setString(6, thuoc.getQuyCachDongGoi());
                statement.setString(7, thuoc.getSDK_GPNK());
                statement.setString(8, thuoc.getHangSX());
                statement.setString(9, thuoc.getNuocSX());
                statement.setBytes(10, thuoc.getHinhAnh());
                statement.setString(11, thuoc.getLoaiHang() == null ? null : thuoc.getLoaiHang().getMaLoaiHang());
                statement.setString(12, thuoc.getNhomDuocLy() == null ? null : thuoc.getNhomDuocLy().getMaNDL());
                statement.setString(13, thuoc.getVitri() == null ? null : thuoc.getVitri().getMaKe());
                statement.setBoolean(14, thuoc.isETC());
                return statement.executeUpdate() > 0;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert medicine " + thuoc.getMaThuoc(), exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertBaseUnit(String maThuoc, String maDonViTinhCoBan) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_DEFAULT_BASE_UNIT_SQL)) {
                statement.setString(1, maThuoc);
                statement.setString(2, maDonViTinhCoBan);
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert default base unit for medicine " + maThuoc, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertChiTietHoatChat(String maThuoc, ChiTietHoatChatDto chiTietHoatChat) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_CHI_TIET_HOAT_CHAT_SQL)) {
                statement.setString(1, maThuoc);
                statement.setString(2, chiTietHoatChat.getHoatChat().getMaHoatChat());
                statement.setFloat(3, chiTietHoatChat.getHamLuong());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not insert ingredient detail for medicine " + maThuoc, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean updateMedicine(Thuoc_SanPhamDto thuoc) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_MEDICINE_SQL)) {
                statement.setString(1, thuoc.getTenThuoc());
                statement.setFloat(2, thuoc.getHamLuong());
                statement.setString(3, thuoc.getDonViHamLuong());
                statement.setString(4, thuoc.getDuongDung());
                statement.setString(5, thuoc.getQuyCachDongGoi());
                statement.setString(6, thuoc.getSDK_GPNK());
                statement.setString(7, thuoc.getHangSX());
                statement.setString(8, thuoc.getNuocSX());
                statement.setBytes(9, thuoc.getHinhAnh());
                statement.setString(10, thuoc.getLoaiHang() == null ? null : thuoc.getLoaiHang().getMaLoaiHang());
                statement.setString(11, thuoc.getNhomDuocLy() == null ? null : thuoc.getNhomDuocLy().getMaNDL());
                statement.setString(12, thuoc.getVitri() == null ? null : thuoc.getVitri().getMaKe());
                statement.setBoolean(13, thuoc.isETC());
                statement.setString(14, thuoc.getMaThuoc());
                return statement.executeUpdate() > 0;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not update medicine " + thuoc.getMaThuoc(), exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void updateChiTietHoatChat(String maThuoc, ChiTietHoatChatDto chiTietHoatChat) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_CHI_TIET_HOAT_CHAT_SQL)) {
                statement.setFloat(1, chiTietHoatChat.getHamLuong());
                statement.setString(2, maThuoc);
                statement.setString(3, chiTietHoatChat.getHoatChat().getMaHoatChat());
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not update ingredient detail for medicine " + maThuoc, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void deleteChiTietHoatChat(String maThuoc, String maHoatChat) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(DELETE_CHI_TIET_HOAT_CHAT_SQL)) {
                statement.setString(1, maThuoc);
                statement.setString(2, maHoatChat);
                statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not delete ingredient detail " + maHoatChat + " for medicine " + maThuoc, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean softDeleteMedicine(String maThuoc) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SOFT_DELETE_MEDICINE_SQL)) {
                statement.setString(1, maThuoc);
                return statement.executeUpdate() > 0;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not soft-delete medicine " + maThuoc, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public int getTongSoLuongTonByMaThuoc(String maThuoc) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_TONG_SO_LUONG_TON_SQL)) {
                statement.setString(1, maThuoc);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("TongSoLuongTon");
                    }
                    return 0;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load total stock for medicine " + maThuoc, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public String getTenDonViTinhCoBan(String maThuoc) {
        Connection connection = null;
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_TEN_DON_VI_CO_BAN_SQL)) {
                statement.setString(1, maThuoc);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("TenDonViTinh");
                    }
                    return null;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load base unit name for medicine " + maThuoc, exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<ThuocTonKhoDto> getThuocTonKho() {
        Connection connection = null;
        List<ThuocTonKhoDto> list = new ArrayList<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_THUOC_TON_KHO_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ThuocTonKhoDto tonKho = new ThuocTonKhoDto();
                    tonKho.setMaThuoc(resultSet.getString("MaThuoc"));
                    tonKho.setTenThuoc(resultSet.getString("TenThuoc"));
                    tonKho.setDonViTinh(resultSet.getString("TenDonViTinh"));
                    tonKho.setSoLoTon(resultSet.getInt("SoLoTon"));
                    tonKho.setTongSoLuongTon(resultSet.getInt("TongSoLuongTon"));
                    list.add(tonKho);
                }
            }
            return list;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load medicine stock summary.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Thuoc_SP_TheoLoDto> findAllLots() {
        Connection connection = null;
        List<Thuoc_SP_TheoLoDto> lots = new ArrayList<>();
        Map<String, Thuoc_SanPhamDto> medicinesById = new LinkedHashMap<>();
        try {
            connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_LOTS_SQL);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String maThuoc = resultSet.getString("MaThuoc");
                    Thuoc_SanPhamDto thuoc = medicinesById.get(maThuoc);
                    if (thuoc == null) {
                        thuoc = mapMedicine(resultSet);
                        medicinesById.put(maThuoc, thuoc);
                    }

                    Thuoc_SP_TheoLoDto lot = new Thuoc_SP_TheoLoDto();
                    lot.setMaLH(resultSet.getString("MaLH"));
                    lot.setSoLuongTon(resultSet.getInt("SoLuongTon"));
                    lot.setNsx(resultSet.getDate("NSX"));
                    lot.setHsd(resultSet.getDate("HSD"));
                    lot.setPhieuNhap(null);
                    lot.setThuoc(thuoc);
                    lots.add(lot);
                }
            }
            attachUnitDetails(medicinesById.values());
            return lots;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not load medicine lots.", exception);
        } finally {
            closeConnection(connection);
        }
    }

    private Thuoc_SanPhamDto mapMedicine(ResultSet resultSet) throws SQLException {
        Thuoc_SanPhamDto thuoc = new Thuoc_SanPhamDto();
        thuoc.setMaThuoc(resultSet.getString("MaThuoc"));
        thuoc.setTenThuoc(resultSet.getString("TenThuoc"));
        thuoc.setHamLuong(resultSet.getFloat("HamLuong"));
        thuoc.setDonViHamLuong(resultSet.getString("DonViHL"));
        thuoc.setDuongDung(resultSet.getString("DuongDung"));
        thuoc.setQuyCachDongGoi(resultSet.getString("QuyCachDongGoi"));
        thuoc.setSDK_GPNK(resultSet.getString("SDK_GPNK"));
        thuoc.setHangSX(resultSet.getString("HangSX"));
        thuoc.setNuocSX(resultSet.getString("NuocSX"));
        thuoc.setHinhAnh(resultSet.getBytes("HinhAnh"));
        thuoc.setETC(resultSet.getBoolean("ETC"));
        thuoc.setNhomDuocLy(mapNhomDuocLy(resultSet));
        thuoc.setLoaiHang(mapLoaiHang(resultSet, "LH_"));
        thuoc.setVitri(mapKeHang(resultSet));
        return thuoc;
    }

    private NhomDuocLyDto mapNhomDuocLy(ResultSet resultSet) throws SQLException {
        String maNdl = resultSet.getString("NDL_MaNDL");
        if (maNdl == null) {
            return null;
        }
        NhomDuocLyDto nhomDuocLy = new NhomDuocLyDto();
        nhomDuocLy.setMaNDL(maNdl);
        nhomDuocLy.setTenNDL(resultSet.getString("NDL_TenNDL"));
        nhomDuocLy.setMoTa(resultSet.getString("NDL_MoTa"));
        return nhomDuocLy;
    }

    private LoaiHangDto mapLoaiHang(ResultSet resultSet, String prefix) throws SQLException {
        String maLoaiHang = resultSet.getString(prefix + "MaLoaiHang");
        if (maLoaiHang == null) {
            return null;
        }
        LoaiHangDto loaiHang = new LoaiHangDto();
        loaiHang.setMaLoaiHang(maLoaiHang);
        loaiHang.setTenLoaiHang(resultSet.getString(prefix + "TenLH"));
        loaiHang.setMoTa(resultSet.getString(prefix + "MoTa"));
        return loaiHang;
    }

    private KeHangDto mapKeHang(ResultSet resultSet) throws SQLException {
        String maKe = resultSet.getString("KE_MaKe");
        if (maKe == null) {
            return null;
        }
        KeHangDto keHang = new KeHangDto();
        keHang.setMaKe(maKe);
        keHang.setTenKe(resultSet.getString("KE_TenKe"));
        keHang.setMoTa(resultSet.getString("KE_MoTa"));
        return keHang;
    }

    private void attachUnitDetails(Collection<Thuoc_SanPhamDto> medicines) throws SQLException {
        if (medicines == null || medicines.isEmpty()) {
            return;
        }

        Map<String, Thuoc_SanPhamDto> medicinesById = new LinkedHashMap<>();
        for (Thuoc_SanPhamDto medicine : medicines) {
            if (medicine != null && medicine.getMaThuoc() != null && !medicine.getMaThuoc().isBlank()) {
                medicinesById.put(medicine.getMaThuoc(), medicine);
            }
        }
        if (medicinesById.isEmpty()) {
            return;
        }

        String placeholders = placeholders(medicinesById.keySet().size());
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
                """.formatted(placeholders);

        Connection connection = null;
        Map<String, List<ChiTietDonViTinhDto>> detailsByMedicineId = new LinkedHashMap<>();
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
                        ChiTietDonViTinhDto detail = new ChiTietDonViTinhDto();
                        DonViTinhDto donViTinh = new DonViTinhDto();
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
            throw new IllegalStateException("Could not load medicine unit details.", exception);
        } finally {
            closeConnection(connection);
        }

        for (Map.Entry<String, Thuoc_SanPhamDto> entry : medicinesById.entrySet()) {
            Thuoc_SanPhamDto medicine = entry.getValue();
            List<ChiTietDonViTinhDto> details = detailsByMedicineId.getOrDefault(entry.getKey(), new ArrayList<>());
            for (ChiTietDonViTinhDto detail : details) {
                detail.setThuoc(medicine);
            }
            medicine.setDsCTDVT(details);
            medicine.setDvcb(details.stream().filter(ChiTietDonViTinhDto::isDonViCoBan).findFirst().orElse(null));
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
