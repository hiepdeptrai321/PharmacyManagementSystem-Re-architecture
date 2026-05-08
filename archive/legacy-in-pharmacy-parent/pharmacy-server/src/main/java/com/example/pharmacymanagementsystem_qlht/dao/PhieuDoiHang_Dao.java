package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDoiHang;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhieuDoiHang_Dao implements DaoInterface<PhieuDoiHang> {
    private final String INSERT_SQL = "INSERT INTO PhieuDoiHang (MaPD, MaNV, MaKH, NgayLap, GhiChu, MaHD) VALUES ( ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE PhieuDoiHang SET MaNV=?, MaKH=?, NgayLap=?, GhiChu=?, MaHD=? WHERE MaPD=?";
    private final String DELETE_BY_ID_SQL = "DELETE FROM PhieuDoiHang WHERE MaPD=?";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM PhieuDoiHang WHERE MaPD=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM PhieuDoiHang";

    @Override
    public boolean insert(PhieuDoiHang e) {
        return ConnectDB.update(INSERT_SQL, e.getMaPD(), e.getNhanVien().getMaNV(), e.getKhachHang().getMaKH(), e.getNgayLap(), e.getGhiChu(), e.getHoaDon().getMaHD())>0;
    }

    @Override
    public boolean update(PhieuDoiHang e) {
        return ConnectDB.update(UPDATE_SQL, e.getNhanVien().getMaNV(), e.getKhachHang().getMaKH(), e.getNgayLap(), e.getGhiChu(), e.getHoaDon().getMaHD(), e.getMaPD())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID_SQL, keys)>0;
    }

    @Override
    public PhieuDoiHang selectById(Object... keys) {
        List<PhieuDoiHang> list = selectBySql(SELECT_BY_ID_SQL, keys);
        return list.isEmpty() ? null : list.get(0);
    }
    public int countByHoaDon(String maHD) {
        String sql = "SELECT COUNT(*) FROM PhieuDoiHang WHERE MaHD=?";
        try (ResultSet rs = ConnectDB.query(sql, maHD)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    @Override
    public List<PhieuDoiHang> selectBySql(String sql, Object... args) {
        List<PhieuDoiHang> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                PhieuDoiHang pdh = new PhieuDoiHang();
                pdh.setMaPD(rs.getString("MaPD"));
                pdh.setNhanVien(new NhanVien_Dao().selectById(rs.getString("MaNV")));
                pdh.setKhachHang(new KhachHang_Dao().selectById(rs.getString("MaKH")));
                pdh.setNgayLap(rs.getTimestamp("NgayLap"));
                pdh.setGhiChu(rs.getString("GhiChu"));
                pdh.setHoaDon(new HoaDon_Dao().selectById(rs.getString("MaHD")));
                list.add(pdh);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public String generateNewMaPD() {
        String newMaPD = "PD001";
        String SELECT_TOP1_SQL = "SELECT TOP 1 MaPD FROM PhieuDoiHang ORDER BY MaPD DESC";
        try {
            ResultSet rs = ConnectDB.query(SELECT_TOP1_SQL);
            if (rs.next()) {
                String lastMaPD = rs.getString("MaPD");
                int stt = Integer.parseInt(lastMaPD.substring(2));
                stt++;
                newMaPD = String.format("PD%03d", stt);
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newMaPD;
    }


    @Override
    public List<PhieuDoiHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
}
