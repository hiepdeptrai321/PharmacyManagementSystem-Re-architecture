package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuNhap;
import com.example.pharmacymanagementsystem_qlht.model.ThuocTonKho;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Thuoc_SP_TheoLo_Dao implements DaoInterface<Thuoc_SP_TheoLo> {

    private final String INSERT_SQL = "INSERT INTO Thuoc_SP_TheoLo(MaLH, MaPN, MaThuoc,SoLuongTon, NSX, HSD) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE Thuoc_SP_TheoLo SET SoLuongTon=?, NSX=?, HSD=?, MaPN=?, MaThuoc=? WHERE MaLH=?";
    private final String DELETE_BY_ID = "DELETE FROM Thuoc_SP_TheoLo WHERE MaLH = ?";
    private final String SELECT_BY_ID = "SELECT * FROM Thuoc_SP_TheoLo WHERE MaLH=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM Thuoc_SP_TheoLo";
    private final String SELECT_SLTON_BY_MATHUOC = "SELECT SUM(SoLuongTon) AS TongSoLuongTon FROM Thuoc_SP_TheoLo WHERE MaThuoc = ?";
    private final String SELECT_BY_TUKHOA_SQL =
            "SELECT t.* FROM Thuoc_SP_TheoLo t " +
                    "JOIN Thuoc_SanPham sp ON t.MaThuoc = sp.MaThuoc " +
                    "WHERE LOWER(t.MaThuoc) LIKE ? OR LOWER(sp.TenThuoc) LIKE ?";
    private static final String UPDATE_TON_KHO_SQL = "UPDATE Thuoc_SP_TheoLo SET SoLuongTon = SoLuongTon - ? WHERE MaThuoc   = ? AND MaLH= ? AND SoLuongTon >= ?";

    private static final String UPDATE_TON_KHO_BY_MA_LO_SQL =
            "UPDATE Thuoc_SP_TheoLo " +
                    "SET SoLuongTon = SoLuongTon - ? " +
                    "WHERE MaLH = ? AND SoLuongTon >= ?";


    private final String SELECT_THUOC_TON_KHO = "SELECT ttl.MaThuoc, tsp.TenThuoc, dvt.TenDonViTinh, " +
            "COUNT(ttl.MaLH) as SoLoTon, SUM(ttl.SoLuongTon) as TongSoLuongTon " +
            "FROM Thuoc_SP_TheoLo ttl " +
            "JOIN Thuoc_SanPham tsp ON ttl.MaThuoc = tsp.MaThuoc " +
            "JOIN ChiTietDonViTinh ctdvt ON ctdvt.MaThuoc = tsp.MaThuoc " +
            "JOIN DonViTinh dvt ON dvt.MaDVT = ctdvt.MaDVT " +
            "WHERE ctdvt.DonViCoBan = 1 " +
            "GROUP BY ttl.MaThuoc, tsp.TenThuoc, dvt.TenDonViTinh";


    @Override
    public boolean insert(Thuoc_SP_TheoLo e) {
        return ConnectDB.update(
                "INSERT INTO Thuoc_SP_TheoLo(MaLH, MaPN, MaThuoc, SoLuongTon, NSX, HSD) VALUES (?, ?, ?, ?, ?, ?)",
                e.getMaLH(),
                e.getPhieuNhap() != null ? e.getPhieuNhap().getPhieuNhap().getMaPN() : null,
                e.getThuoc() != null ? e.getThuoc().getMaThuoc() : null,
                e.getSoLuongTon(),
                e.getNsx(),
                e.getHsd()
        ) > 0;
    }

    @Override
    public boolean update(Thuoc_SP_TheoLo e) {
        return ConnectDB.update(UPDATE_SQL, e.getSoLuongTon(), e.getNsx(), e.getHsd(),
                e.getPhieuNhap() != null ? e.getPhieuNhap().getPhieuNhap().getMaPN() : null,
                e.getThuoc() != null ? e.getThuoc().getMaThuoc() : null,
                e.getMaLH()) > 0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID, keys) > 0;
    }

    @Override
    public Thuoc_SP_TheoLo selectById(Object... keys) {
        List<Thuoc_SP_TheoLo> list = selectBySql(SELECT_BY_ID, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Thuoc_SP_TheoLo> selectBySql(String sql, Object... args) {
        List<Thuoc_SP_TheoLo> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                Thuoc_SP_TheoLo t = new Thuoc_SP_TheoLo();
                t.setMaLH(rs.getString("MaLH"));
                t.setSoLuongTon(rs.getInt("SoLuongTon"));
                t.setNsx(rs.getDate("NSX"));
                t.setHsd(rs.getDate("HSD"));
                t.setPhieuNhap(new ChiTietPhieuNhap_Dao().selectById(rs.getString("MaPN"), rs.getString("MaThuoc"), rs.getString("MaLH")));
                t.setThuoc(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuoc")));
                list.add(t);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Thuoc_SP_TheoLo> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public int selectSoLuongTonByMaThuoc(String maThuoc) {
        int soLuongTon = 0;
        try {
            ResultSet rs = ConnectDB.query(SELECT_SLTON_BY_MATHUOC, maThuoc);
            if (rs.next()) {
                soLuongTon = rs.getInt("TongSoLuongTon");
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return soLuongTon;
    }
    public boolean congSoLuong(String maLH, int soLuong) {
        String sql = """
        UPDATE Thuoc_SP_TheoLo
        SET SoLuongTon = SoLuongTon + ?
        WHERE MaLH = ?
    """;
        return ConnectDB.update(sql, soLuong, maLH) > 0;
    }

    public List<Thuoc_SP_TheoLo> selectByTuKhoa(String tuKhoa) {
        return this.selectBySql(SELECT_BY_TUKHOA_SQL, "%" + tuKhoa.toLowerCase() + "%", "%" + tuKhoa.toLowerCase() + "%");
    }

    public boolean giamTonKho(Connection conn, Thuoc_SP_TheoLo loHang, int soLuongCanGiam) throws SQLException {
        if (conn == null) throw new SQLException("Connection is null");
        if (soLuongCanGiam <= 0) return false;

        try (PreparedStatement ps = conn.prepareStatement(UPDATE_TON_KHO_BY_MA_LO_SQL)) {
            ps.setInt(1, soLuongCanGiam);
            ps.setString(2, loHang.getMaLH());
            ps.setInt(3, soLuongCanGiam);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public Thuoc_SP_TheoLo selectOldestAvailableLot(Connection con, String maThuoc) throws SQLException {
        final String sql = "SELECT TOP 1 MaLH, SoLuongTon, NSX, HSD " +
                "FROM Thuoc_SP_TheoLo WHERE MaThuoc = ? AND SoLuongTon > 0 " +
                "ORDER BY HSD ASC, NSX ASC, MaLH ASC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maThuoc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Thuoc_SP_TheoLo lo = new Thuoc_SP_TheoLo();
                    lo.setMaLH(rs.getString("MaLH"));
                    lo.setSoLuongTon(rs.getInt("SoLuongTon"));
                    lo.setNsx(rs.getDate("NSX"));
                    lo.setHsd(rs.getDate("HSD"));
                    return lo;
                }
            }
        }
        return null;
    }
    public List<Thuoc_SP_TheoLo> selectAllAvailableLots(Connection con, String maThuoc) throws SQLException {
        List<Thuoc_SP_TheoLo> list = new ArrayList<>();
        String sql = "SELECT MaLH, MaPN, MaThuoc, SoLuongTon, NSX, HSD FROM Thuoc_SP_TheoLo WHERE MaThuoc = ? AND SoLuongTon > 0 ORDER BY HSD ASC, NSX ASC, MaLH ASC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maThuoc);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Thuoc_SP_TheoLo lo = new Thuoc_SP_TheoLo();
                    lo.setMaLH(rs.getString("MaLH"));
                    lo.setSoLuongTon(rs.getInt("SoLuongTon"));
                    lo.setNsx(rs.getDate("NSX"));
                    lo.setHsd(rs.getDate("HSD"));
                    lo.setPhieuNhap(new ChiTietPhieuNhap_Dao().selectById(rs.getString("MaPN"), rs.getString("MaThuoc"), rs.getString("MaLH")));
                    lo.setThuoc(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuoc")));
                    list.add(lo);
                }
            }
        }
        return list;
    }


    // Optional convenience overload if you only have lot id on hand
    public boolean giamTonKhoByMaLo(Connection conn, String maLH, int soLuongCanGiam) throws SQLException {
        if (conn == null) throw new SQLException("Connection is null");
        if (soLuongCanGiam <= 0) return false;

        try (PreparedStatement ps = conn.prepareStatement(UPDATE_TON_KHO_BY_MA_LO_SQL)) {
            ps.setInt(1, soLuongCanGiam);
            ps.setString(2, maLH);
            ps.setInt(3, soLuongCanGiam);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public List<Thuoc_SP_TheoLo> selectLoHangFEFO_Multi(String maThuoc, int soLuongCan) {
        String sql = "SELECT * FROM Thuoc_SP_TheoLo WHERE MaThuoc = ? AND SoLuongTon > 0 ORDER BY HSD ASC";
        List<Thuoc_SP_TheoLo> list = selectBySql(sql, maThuoc);
        List<Thuoc_SP_TheoLo> kq = new ArrayList<>();
        int tong = 0;
        for (Thuoc_SP_TheoLo lo : list) {
            kq.add(lo);
            tong += lo.getSoLuongTon();
            if (tong >= soLuongCan) break;
        }
        return tong >= soLuongCan ? kq : new ArrayList<>();
    }

    public List<Thuoc_SP_TheoLo> selectHangSapHetHan() {
        List<Thuoc_SP_TheoLo> list = new ArrayList<>();
        String sql = "{call sp_HangSapHetHan}";

        try (Connection con = ConnectDB.getInstance(); // 🔹 Tạo connection mới
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Thuoc_SP_TheoLo lo = new Thuoc_SP_TheoLo();
                lo.setMaLH(rs.getString("MaLH"));
                lo.setHsd(rs.getDate("HSD"));
                lo.setThuoc(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuoc")));

                list.add(lo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Thuoc_SP_TheoLo> selectHangDaHetHan() {
        List<Thuoc_SP_TheoLo> list = new ArrayList<>();
        String sql = "{call sp_HangHetHan}";

        try (Connection con = ConnectDB.getInstance();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Thuoc_SP_TheoLo lo = new Thuoc_SP_TheoLo();
                lo.setMaLH(rs.getString("MaLH"));
                lo.setHsd(rs.getDate("HSD"));
                lo.setThuoc(new Thuoc_SanPham_Dao().selectById(rs.getString("MaThuoc")));

                list.add(lo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ThuocTonKho> getThuocTonKho(){
        List<ThuocTonKho> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(SELECT_THUOC_TON_KHO);
            while (rs.next()) {
                ThuocTonKho t = new ThuocTonKho();
                t.setTenThuoc(rs.getString("TenThuoc"));
                t.setDonViTinh(rs.getString("TenDonViTinh"));
                t.setMaThuoc(rs.getString("MaThuoc"));
                t.setSoLoTon(rs.getInt("SoLoTon"));
                t.setTongSoLuongTon(rs.getInt("TongSoLuongTon"));
                list.add(t);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}