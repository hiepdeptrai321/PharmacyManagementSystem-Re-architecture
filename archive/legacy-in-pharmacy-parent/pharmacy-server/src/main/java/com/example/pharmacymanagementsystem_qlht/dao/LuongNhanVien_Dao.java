package com.example.pharmacymanagementsystem_qlht.dao;

import com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB;
import com.example.pharmacymanagementsystem_qlht.model.LuongNhanVien;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LuongNhanVien_Dao implements DaoInterface<LuongNhanVien> {

    private final String INSERT_SQL = "INSERT INTO LuongNhanVien(MaLNV, TuNgay, DenNgay, LuongCoBan, PhuCap,GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE LuongNhanVien SET TuNgay=?, DenNgay=?, LuongCoBan=?, PhuCap=?, GhiChu=? WHERE MaLNV=?";
    private final String DELETE_BY_ID = "DELETE FROM LuongNhanVien WHERE MaLNV = ?";
    private final String SELECT_BY_ID = "SELECT * FROM LuongNhanVien WHERE MaLNV=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM LuongNhanVien";

    @Override
    public boolean insert(LuongNhanVien e) {
        return ConnectDB.update(INSERT_SQL, e.getMaLNV(), e.getTuNgay(), e.getDenNgay(), e.getLuongCoBan(), e.getPhuCap(), e.getGhiChu(), e.getNhanVien().getMaNV())>0;
    }

    @Override
    public boolean update(LuongNhanVien e) {
        return ConnectDB.update(UPDATE_SQL, e.getTuNgay(), e.getDenNgay(), e.getLuongCoBan(), e.getPhuCap(), e.getGhiChu(), e.getMaLNV())>0;
    }

    @Override
    public boolean deleteById(Object... keys) {
        return ConnectDB.update(DELETE_BY_ID, keys) > 0;
    }

    @Override
    public LuongNhanVien selectById(Object... keys) {
        List<LuongNhanVien> list = selectBySql(SELECT_BY_ID, keys);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LuongNhanVien> selectBySql(String sql, Object... args) {
        List<LuongNhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.query(sql, args);
            while (rs.next()) {
                LuongNhanVien lnv = new LuongNhanVien();
                lnv.setMaLNV(rs.getString("MaLNV"));
                lnv.setTuNgay(rs.getDate("TuNgay"));
                lnv.setDenNgay(rs.getDate("DenNgay"));
                lnv.setLuongCoBan(rs.getDouble("LuongCoBan"));
                lnv.setPhuCap(rs.getDouble("PhuCap"));
                lnv.setGhiChu(rs.getString("GhiChu"));
                lnv.setNhanVien(new NhanVien_Dao().selectById(rs.getString("MaNV")));
                list.add(lnv);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<LuongNhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<LuongNhanVien> selectByMaNV(String maNV) {
        String sql = "SELECT * FROM LuongNhanVien WHERE MaNV = ?";
        return this.selectBySql(sql, maNV);
    }

    public String getNewMaLNV() {
        String sql = "SELECT TOP 1 MaLNV FROM LuongNhanVien ORDER BY MaLNV DESC";
        try {
            ResultSet rs = ConnectDB.query(sql);
            if (rs.next()) {
                String lastMaLNV = rs.getString("MaLNV");
                int numericPart = Integer.parseInt(lastMaLNV.substring(3));
                String newMaLNV = String.format("LNV%03d", numericPart + 1);
                rs.getStatement().getConnection().close();
                return newMaLNV;
            } else {
                rs.getStatement().getConnection().close();
                return "LNV001";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}