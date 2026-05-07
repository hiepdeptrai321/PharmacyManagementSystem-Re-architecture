package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuNhap;
import com.example.pharmacymanagementsystem_qlht.model.PhieuNhap;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhap_Dao implements DaoInterface<PhieuNhap>{
    private final String INSERT_SQL = "INSERT INTO PhieuNhap (MaPN, MaNCC, NgayNhap, TrangThai, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE PhieuNhap SET MaNCC = ?, MaNV = ?, NgayNhap = ?, TrangThai = ?, GhiChu = ? WHERE MaPN = ?";
    private final String DELETE_SQL = "DELETE FROM PhieuNhap WHERE MaPN = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM PhieuNhap";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM PhieuNhap WHERE MaPN = ?";
    private final String SELECT_TOP1_MAPN = "SELECT TOP 1 MaPN FROM PhieuNhap ORDER BY MaPN DESC";
    private final String SELECT_ALL_NCC = "SELECT TenNCC FROM NhaCungCap";
    private final String SELECT_TOP1_PHIEUNHAP = "SELECT TOP 1 MaPN FROM PhieuNhap ORDER BY MaPN DESC";

    @Override
    public boolean insert(PhieuNhap e) {
        return ConnectDB.update(INSERT_SQL, e.getMaPN(), e.getNhaCungCap().getMaNCC(), e.getNhanVien().getMaNV(), e.getNgayNhap(), e.getTrangThai(), e.getGhiChu())>0;
    }

    @Override
    public boolean update(PhieuNhap e) {
        return ConnectDB.update(UPDATE_SQL, e.getNhaCungCap().getMaNCC(), e.getNhanVien().getMaNV(), e.getNgayNhap(), e.getTrangThai(), e.getGhiChu(), e.getMaPN())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_SQL, keys)>0;
    }

    @Override
    public PhieuNhap selectById(Object... keys) {
        return this.selectBySql(SELECT_BY_ID_SQL, keys).get(0);
    }

    public List<PhieuNhap> selectBySql(String sql, Object... args) {
        List<PhieuNhap> phieuNhapList = new ArrayList<>();
        try (ResultSet rs = ConnectDB.query(sql, args)) {
            while (rs.next()) {
                PhieuNhap pn = new PhieuNhap();
                pn.setMaPN(rs.getString("MaPN"));
                pn.setNgayNhap(rs.getTimestamp("NgayNhap").toLocalDateTime().toLocalDate());
                pn.setTrangThai(rs.getBoolean("TrangThai"));
                pn.setGhiChu(rs.getString("GhiChu"));
                pn.setNhaCungCap(new NhaCungCap_Dao().selectById(rs.getString("MaNCC")));
                pn.setNhanVien(new NhanVien_Dao().selectById(rs.getString("MaNV")));
                phieuNhapList.add(pn);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi truy vấn SQL: " + sql, e);
        }
        return phieuNhapList;
    }

    @Override
    public List<PhieuNhap> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public String generatekeyPhieuNhap() {
        String key = null;
        try {
            ResultSet rs = ConnectDB.query(SELECT_TOP1_MAPN);
            String lastKey = rs.getString("maPN");
            if (lastKey != null) {
                int numericPart = Integer.parseInt(lastKey.substring(2));
                numericPart++;
                key = String.format("PN%03d", numericPart);
            } else {
                key = "PN001";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    public List<String> getAllNCC() {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(SELECT_ALL_NCC);
            while (rs.next()) {
                list.add(rs.getString("TenNCC"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> getAllNhanVien() {
        String sql = "SELECT TenNV FROM NhanVien";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql);
            while (rs.next()) {
                list.add(rs.getString("TenNV"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean luuPhieuNhap(PhieuNhap phieu, ChiTietPhieuNhap ctpn, Thuoc_SP_TheoLo lo, String maDVT) {
        String sql = "{CALL sp_LuuPhieuNhap(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (PreparedStatement stmt = ConnectDB.getStmt(sql,
                phieu.getMaPN(),
                phieu.getNgayNhap(),
                phieu.getGhiChu(),
                phieu.getNhaCungCap().getMaNCC(),
                phieu.getNhanVien().getMaNV(),
                ctpn.getThuoc().getMaThuoc(),
                ctpn.getMaLH(),
                ctpn.getSoLuong(),
                ctpn.getGiaNhap(),
                ctpn.getChietKhau(),
                ctpn.getThue(),
                lo != null ? lo.getSoLuongTon() : null,
                lo != null ? lo.getNsx() : null,
                lo != null ? lo.getHsd() : null,
                maDVT)) {

            stmt.execute();
            stmt.getConnection().close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateMaPN(){
        String key = "PN001";
        try {
            String lastKey = ConnectDB.queryTaoMa(SELECT_TOP1_PHIEUNHAP);
            if (lastKey != null && lastKey.startsWith("PN")) {
                int stt = Integer.parseInt(lastKey.substring(2));
                stt++;
                key = String.format("PN%03d", stt);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    public boolean existsByMaPN(String maPN) {
        return selectBySql("SELECT 1 FROM PhieuNhap WHERE MaPN = ?").get(0).equals(maPN);
    }
}
