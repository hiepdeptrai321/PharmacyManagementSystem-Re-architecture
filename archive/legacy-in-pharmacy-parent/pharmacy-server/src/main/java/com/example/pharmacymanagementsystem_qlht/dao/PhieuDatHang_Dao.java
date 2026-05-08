package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDatHang;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhieuDatHang_Dao implements DaoInterface<PhieuDatHang> {
    private final String INSERT_SQL = "INSERT INTO PhieuDatHang (MaPDat, NgayLap, SoTienCoc, GhiChu, MaKH, MaNV) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE PhieuDatHang SET NgayLap=?, SoTienCoc=?, GhiChu=?, MaKH=?, MaNV=?, TrangThai=? WHERE MaPDat=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM PhieuDatHang WHERE MaPDat=?";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM PhieuDatHang WHERE MaPDat=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM PhieuDatHang";

    @Override
    public boolean insert(PhieuDatHang e) {
        return ConnectDB.update(INSERT_SQL, e.getMaPDat(), e.getNgayLap(), e.getSoTienCoc(), e.getGhiChu(), e.getKhachHang().getMaKH(), e.getNhanVien().getMaNV())>0;
    }

    @Override
    public boolean update(PhieuDatHang e) {
        return ConnectDB.update(UPDATE_SQL, e.getNgayLap(), e.getSoTienCoc(), e.getGhiChu(), e.getKhachHang().getMaKH(), e.getNhanVien().getMaNV(), e.getTrangthai(), e.getMaPDat())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys)>0;
    }

    @Override
    public PhieuDatHang selectById(Object... keys) {
        List<PhieuDatHang> list = selectBySql(SELECT_BY_ID_SQL, keys);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<PhieuDatHang> selectBySql(String sql, Object... args) {
        List<PhieuDatHang> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                PhieuDatHang pdh = new PhieuDatHang();
                pdh.setMaPDat(rs.getString("MaPDat"));
                pdh.setNgayLap(rs.getTimestamp("NgayLap"));
                pdh.setSoTienCoc(rs.getDouble("SoTienCoc"));
                pdh.setGhiChu(rs.getString("GhiChu"));
                pdh.setKhachHang(new KhachHang_Dao().selectById(rs.getString("MaKH")));
                pdh.setNhanVien(new NhanVien_Dao().selectById(rs.getString("MaNV")));
                pdh.setTrangthai(rs.getInt("TrangThai"));
                list.add(pdh);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<PhieuDatHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public String generateNewMaPDat() {
        String prefix = "PDH";
        String sql = "SELECT TOP 1 MaPDat FROM PhieuDatHang ORDER BY MaPDat DESC";
        try {
            ResultSet rs = ConnectDB.query(sql);
            if (rs.next()) {
                String lastMaPDat = rs.getString("MaPDat");
                int lastNumber = Integer.parseInt(lastMaPDat.substring(prefix.length()));
                String newMaPDat = String.format("%s%03d", prefix, lastNumber + 1);
                rs.getStatement().close();
                return newMaPDat;
            } else {
                rs.getStatement().close();
                return String.format("%s%03d", prefix, 1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean duyetPhieuDatHang(String maPhieuDat) {
        String sql = "{CALL sp_DuyetPhieuDatHang(?)}";

        try (Connection con = ConnectDB.getInstance();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, maPhieuDat);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi duyệt phiếu đặt hàng: " + e.getMessage());
            return false;
        }
    }
}
