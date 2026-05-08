package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KhachHang_Dao implements DaoInterface<KhachHang> {

    private final String INSERT_SQL = "INSERT INTO KhachHang (MaKH, TenKH, SDT, Email, NgaySinh, GioiTinh, DiaChi, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE KhachHang SET TenKH=?, SDT=?, Email=?, NgaySinh=?, GioiTinh=?, DiaChi=? WHERE MaKH=?";
    private final String DELETE_BY_ID = "UPDATE KhachHang SET TrangThai = 0 WHERE MaKH = ?";
    private final String SELECT_BY_ID = "SELECT * FROM KhachHang WHERE MaKH=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM KhachHang WHERE TrangThai = 1" ;

    @Override
    public boolean insert(KhachHang e) {
        if (e.getMaKH() == null || e.getMaKH().trim().isEmpty()) {
            e.setMaKH(generateNewMaKH());
        }
        Boolean trangThai = e.getTrangThai() == null ? true : e.getTrangThai();

        Boolean gioiTinh = e.getGioiTinh() == null ? true : e.getGioiTinh();

        final int MAX_RETRY = 5;
        int attempt = 0;
        while (attempt < MAX_RETRY) {
            try {
                int rows = ConnectDB.update(INSERT_SQL,
                        e.getMaKH(),
                        e.getTenKH(),
                        e.getSdt(),
                        e.getEmail(),
                        e.getNgaySinh(),
                        gioiTinh,
                        e.getDiaChi(),
                        trangThai
                );
                return rows > 0;
            } catch (RuntimeException ex) {
                attempt++;
                if (attempt >= MAX_RETRY) throw ex;
                e.setMaKH(generateNewMaKH());
            }
        }
        return false;
    }

    public KhachHang findOrCreateByPhone(Connection con, String sdt, String ten) throws SQLException {
        final String SELECT = "select * from KhachHang where SDT = ?";
        final String INSERT = "insert into KhachHang (MaKH, TenKH, SDT) values (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(SELECT)) {
            ps.setString(1, sdt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    KhachHang kh = new KhachHang();
                    kh.setMaKH(rs.getString("MaKH"));
                    kh.setTenKH(rs.getString("TenKH"));
                    kh.setSdt(rs.getString("SDT"));
                    return kh;
                }
            }
        }
        String newMa = "KH" + System.currentTimeMillis(); // hoặc tự sinh theo format
        try (PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, newMa);
            ps.setString(2, ten);
            ps.setString(3, sdt);
            ps.executeUpdate();
        }
        KhachHang kh = new KhachHang();
        kh.setMaKH(newMa);
        kh.setTenKH(ten);
        kh.setSdt(sdt);
        return kh;
    }
    public KhachHang findOrCreateByPhone(String sdt, String ten) throws SQLException {
        Connection con = ConnectDB.getInstance();
        return findOrCreateByPhone(con, sdt, ten);
    }


    @Override
    public boolean update(KhachHang e) {
        return ConnectDB.update(UPDATE_SQL, e.getTenKH(), e.getSdt(), e.getEmail(), e.getNgaySinh(), e.getGioiTinh(), e.getDiaChi(), e.getMaKH())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID, keys)>0;
    }

    @Override
    public KhachHang selectById(Object... keys) {
        List<KhachHang> list = selectBySql(SELECT_BY_ID, keys);
        if (list.isEmpty()) {
            return null;
    }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setSdt(rs.getString("SDT"));
                kh.setEmail(rs.getString("Email"));
                kh.setNgaySinh(rs.getObject("NgaySinh", LocalDate.class));
                kh.setGioiTinh(rs.getBoolean("GioiTinh"));
                kh.setDiaChi(rs.getString("DiaChi"));
                // map TrangThai from DB
                kh.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(kh);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void refreshTrangThai() {
        String sql = "UPDATE KhachHang SET TrangThai = CASE " +
                "WHEN EXISTS (SELECT 1 FROM HoaDon WHERE HoaDon.MaKH = KhachHang.MaKH AND NgayLap >= DATEADD(year, -1, GETDATE())) THEN 'Hoạt động' " +
                "WHEN EXISTS (SELECT 1 FROM HoaDon WHERE HoaDon.MaKH = KhachHang.MaKH) AND NOT EXISTS (SELECT 1 FROM HoaDon WHERE HoaDon.MaKH = KhachHang.MaKH AND NgayLap >= DATEADD(year, -1, GETDATE())) THEN 'Không hoạt động' " +
                "ELSE TrangThai END";
        ConnectDB.update(sql);
    }

    @Override
    public List<KhachHang> selectAll() {
        return  this.selectBySql(SELECT_ALL_SQL);
    }

    public String generateNewMaKH() {
        // Preferred: use SQL Server SEQUENCE if available (atomic and safe under concurrency)
        try {
            String seqSql = "SELECT NEXT VALUE FOR seq_MaKH";
            java.sql.ResultSet rsSeq = ConnectDB.query(seqSql);
            if (rsSeq.next()) {
                long seqVal = rsSeq.getLong(1);
                rsSeq.getStatement().getConnection().close();
                return String.format("KH%03d", seqVal);
            }
            rsSeq.getStatement().getConnection().close();
        } catch (Exception ignore) {
        }

        String newMa = "KH001";
        String sql = "SELECT TOP 1 MaKH FROM KhachHang ORDER BY TRY_CONVERT(INT, SUBSTRING(MaKH, 3, 100)) DESC";
        try {
            java.sql.ResultSet rs = ConnectDB.query(sql);
            if (rs.next()) {
                String last = rs.getString("MaKH");
                String digits = last == null ? "" : last.replaceAll("\\D", "");
                if (digits.isEmpty()) {
                    newMa = "KH001";
                } else {
                    int num = Integer.parseInt(digits);
                    num++;
                    newMa = String.format("KH%03d", num);
                }
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return newMa;
    }
}